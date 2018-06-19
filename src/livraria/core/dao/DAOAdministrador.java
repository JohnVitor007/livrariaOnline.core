
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Administrador;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Telefone;
import livraria.dominio.Usuario;

public class DAOAdministrador extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Administrador administrador = (Administrador) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        Collection<EntidadeDominio> eds = new ArrayList<>();
        
        query.append("select adm_nome, adm_data_nascimento, adm_genero, adm_cpf, adm_ddd, adm_numero_telefone, adm_status, adm_email, adm_senha, adm_papel, adm_ativo ");
        query.append("from administradores");
        
        if(administrador.getUsuario() != null)
            query.append(" where adm_email = ? and adm_senha = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        if(administrador.getUsuario() != null){
            pst.setObject(1, administrador.getUsuario().getEmail());
            pst.setObject(2, administrador.getUsuario().getSenha());
        }
        
        ResultSet rs = pst.executeQuery();
        
        Administrador a;
        
        while(rs.next()){
            
            a = new Administrador();
            
            a.setNome(rs.getString("adm_nome"));
            a.setDataNascimento(rs.getDate("adm_data_nascimento"));
            a.setGenero(rs.getString("adm_genero"));
            a.setCpf(rs.getString("adm_cpf"));
            a.setTelefone(new Telefone());
            a.getTelefone().setDdd(rs.getInt("adm_ddd"));
            a.getTelefone().setNumero(rs.getInt("adm_numero_telefone"));
            a.setUsuario(new Usuario());
            a.getUsuario().setStatus(rs.getString("adm_status"));
            a.getUsuario().setEmail(rs.getString("adm_email"));
            a.getUsuario().setSenha(rs.getString("adm_senha"));
            a.getUsuario().setAtivo(true);
            a.setFuncao(rs.getString("adm_papel"));
            a.getUsuario().setAtivo(rs.getBoolean("adm_ativo"));
            eds.add(a);
            
        }
        
        connection.close();
        
        return eds;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Administrador a = (Administrador) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into administradores(adm_nome, adm_data_nascimento, adm_genero, adm_cpf, adm_ddd, adm_numero_telefone, adm_status, adm_email, adm_senha, adm_papel, adm_ativo, adm_tipo_telefone) values ");
        sql.append("(?,?,?,?,?,?,?,?,?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, a.getNome());
        pst.setObject(2, new java.sql.Date(a.getDataCadastro().getTime()));
        pst.setObject(3, a.getGenero());
        pst.setObject(4, a.getCpf());
        pst.setObject(5, a.getTelefone().getDdd());
        pst.setObject(6, a.getTelefone().getNumero());
        pst.setObject(7, a.getUsuario().getStatus());
        pst.setObject(8, a.getUsuario().getEmail());
        pst.setObject(9, a.getUsuario().getSenha());
        pst.setObject(10, a.getFuncao());
        pst.setObject(11, a.isAtivo());
        pst.setObject(12, a.getTelefone().getTipo());
        
        pst.execute();
        
        return a;
       
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
