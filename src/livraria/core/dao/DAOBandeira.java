package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Bandeira;
import livraria.dominio.EntidadeDominio;

public class DAOBandeira extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {

        Bandeira bandeira = (Bandeira) entidadeDominio;
        
        Collection<EntidadeDominio> eds = new ArrayList();
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ban_id_bandeira, ban_nome_banco_emissor, ban_img_bandeira, ban_data_cadastro from bandeiras");

        this.openConnection();
        
        if(bandeira.getId() != 0)
            sql.append(" where ban_id_bandeira = ?");
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        if(bandeira.getId() != 0)
            pst.setObject(1, bandeira.getId());
        
        ResultSet rs = pst.executeQuery();
        
        Bandeira b = null;
        
        while(rs.next()){
            
            b = new Bandeira();
            b.setId(rs.getInt("ban_id_bandeira"));
            b.setNomeBancoEmissor(rs.getString("ban_nome_banco_emissor"));
            b.setImgBandeira(rs.getString("ban_img_bandeira"));
            b.setDataCadastro(rs.getDate("ban_data_cadastro"));
            
            eds.add(b);
            
        }
                
        connection.close();
        
        return eds;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Bandeira bandeira = (Bandeira) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into bandeiras(ban_nome_banco_emissor, ban_img_bandeira, ban_data_cadastro)values(?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, bandeira.getNomeBancoEmissor());
        pst.setObject(2, bandeira.getImgBandeira());
        pst.setObject(3, new java.sql.Date(bandeira.getDataCadastro().getTime()));
        
        pst.execute();
                
        connection.close();

        return bandeira;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
