
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.Bandeira;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;


public class DAOCartao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Cartao c = (Cartao) entidadeDominio;
        
        Collection<EntidadeDominio> cartoes = new ArrayList<>();
    
        StringBuilder query = new StringBuilder();
        
        query.append("select b.ban_nome_banco_emissor, b.ban_id_bandeira, c.car_id_cartao, c.car_numero, c.car_nome_impresso, c.car_codigo_seguranca, c.car_id_bandeira, c.car_principal, c.car_data_cadastro");
        query.append(" from cartoes c join bandeiras b on c.car_id_bandeira = b.ban_id_bandeira ");
        
        if(c.getId() != 0)
            query.append("where c.car_id_cartao = ?");
        else
            query.append("inner join cartoes_clientes cc on c.car_id_cartao = cc.cc_id_cartao where c.car_ativo = true and cc.cc_id_cliente = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
                
        if(c.getId() != 0)
            pst.setInt(1, c.getId());
        else
            pst.setInt(1, c.getCliente().getId());
                
        ResultSet rs = pst.executeQuery();
        
        Cartao cBuffer;
        
        while(rs.next()){
            
            cBuffer = new Cartao();
            cBuffer.setId(rs.getInt("car_id_cartao"));
            cBuffer.setNumero(rs.getString("car_numero"));
            cBuffer.setNomeImpresso(rs.getString("car_nome_impresso"));
            cBuffer.setCodigoSeguranca(rs.getInt("car_codigo_seguranca"));
            cBuffer.setPreferencial(rs.getBoolean("car_principal"));
            cBuffer.setDataCadastro(rs.getDate("car_data_cadastro"));
            cBuffer.setBancoEmissor(new Bandeira());
            cBuffer.getBancoEmissor().setNomeBancoEmissor(rs.getString("ban_nome_banco_emissor"));
            cBuffer.getBancoEmissor().setId(rs.getInt("ban_id_bandeira"));
            
            DAOBandeira db = new DAOBandeira();
            cBuffer.setBancoEmissor((Bandeira) ((List<EntidadeDominio>) db.consultar(cBuffer.getBancoEmissor())).get(0));
            
            cartoes.add(cBuffer);
            
        }
       
        connection.close();
        
        return cartoes;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Cartao c = (Cartao) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        query.append("insert into cartoes(car_numero, car_nome_impresso, car_codigo_seguranca, car_id_bandeira, car_principal, car_ativo, car_data_cadastro) ");
        query.append("values(?,?,?,?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
        
        pst.setObject(1, c.getNumero());
        pst.setObject(2, c.getNomeImpresso());
        pst.setObject(3, c.getCodigoSeguranca());
        pst.setObject(4, c.getBancoEmissor().getId());
        pst.setObject(5, c.isPreferencial());
        pst.setObject(6, c.isAtivo());
        pst.setObject(7, new java.sql.Date(c.getDataCadastro().getTime()));
        
        pst.execute();
        
        ResultSet rs = pst.getGeneratedKeys();
        
        if(rs.next())
            c.setId(rs.getInt(1));
        
        query.setLength(0);
        query.append("insert into cartoes_clientes (cc_id_cartao, cc_id_cliente) values (?,?)");
        
        pst = connection.prepareStatement(query.toString());
        
        pst.setInt(1, c.getId());
        pst.setInt(2, c.getCliente().getId());
        
        pst.execute();
        
        connection.close();
        
    
        return c;
    
    }
    
    

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Cartao c = (Cartao) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("update cartoes set car_numero = ?, car_nome_impresso = ?, car_codigo_seguranca = ?, car_id_bandeira = ?, car_principal = ? where car_id_cartao = ?");

        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, c.getNumero());
        pst.setObject(2, c.getNomeImpresso());
        pst.setObject(3, c.getCodigoSeguranca());
        pst.setObject(4, c.getBancoEmissor().getId());
        pst.setObject(5, c.isPreferencial());
        pst.setObject(6, c.getId());
        
        pst.execute();
        
        connection.close();
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    
        Cartao c = (Cartao) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("update cartoes set car_ativo = false where car_id_cartao = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, c.getId());
        
        pst.execute();
        
        connection.close();
        
    }
    
    
    
}
