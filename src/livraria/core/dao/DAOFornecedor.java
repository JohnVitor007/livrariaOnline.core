
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Cidade;
import livraria.dominio.Endereco;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Estado;
import livraria.dominio.Fornecedor;
import livraria.dominio.Pais;
import livraria.dominio.Telefone;
import livraria.dominio.TipoLogradouro;

public class DAOFornecedor extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Fornecedor f = (Fornecedor) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        Collection<EntidadeDominio> fornecedores = new ArrayList();
        
        sql.append("select for_ddd, for_telefone, for_razao_social, for_email, for_logradouro, for_tipo_logradouro, for_numero, for_bairro, for_cidade, for_estado, for_pais, for_cnpj ");
        sql.append("from fornecedores");
        
        if(f.getId() != 0)
            sql.append(" where for_id_fornecedor = ?");

        this.openConnection();
            
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        if(f.getId() != 0)
            pst.setInt(1, f.getId());
        
        ResultSet rs = pst.executeQuery();
        
        Fornecedor fornecedor = null;
        
        while(rs.next()){
        
            fornecedor = new Fornecedor();
            
            fornecedor.setTelefone(new Telefone());
            fornecedor.getTelefone().setDdd(rs.getInt("for_ddd"));
            fornecedor.getTelefone().setNumero(rs.getInt("for_telefone"));
            fornecedor.setRazaoSocial(rs.getString("for_razao_social"));
            fornecedor.setEmail(rs.getString("for_email"));
            fornecedor.setEndereco(new Endereco());
            fornecedor.getEndereco().setLogradouro(rs.getString("for_logradouro"));
            fornecedor.getEndereco().setTipoLogradouro(new TipoLogradouro());
            fornecedor.getEndereco().getTipoLogradouro().setNome(rs.getString("for_tipo_logradouro"));
            fornecedor.getEndereco().setNumero(rs.getInt("for_numero"));
            fornecedor.getEndereco().setBairro(rs.getString("for_bairro"));
            fornecedor.getEndereco().setCidade(new Cidade());
            fornecedor.getEndereco().getCidade().setNome(rs.getString("for_cidade"));
            fornecedor.getEndereco().setEstado(new Estado());
            fornecedor.getEndereco().getEstado().setNome(rs.getString("for_estado"));
            fornecedor.getEndereco().setPais(new Pais());
            fornecedor.getEndereco().getPais().setNome(rs.getString("for_pais"));
            fornecedor.setCnpj(rs.getString("for_cnpj"));
            
            fornecedores.add(fornecedor);
            
        }
        
        connection.close();
        
        return fornecedores;
        
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Fornecedor f = (Fornecedor) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into fornecedores(for_ddd, for_telefone, for_razao_social, for_email, for_logradouro, for_tipo_logradouro, for_numero, for_bairro, for_cidade, for_estado, for_pais, for_cnpj)");
        sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?) ");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, f.getTelefone().getDdd());
        pst.setObject(2, f.getTelefone().getNumero());
        pst.setObject(3, f.getRazaoSocial());
        pst.setObject(4, f.getEmail());
        pst.setObject(5, f.getEndereco().getLogradouro());
        pst.setObject(6, f.getEndereco().getTipoLogradouro().getNome());
        pst.setObject(7, f.getEndereco().getNumero());
        pst.setObject(8, f.getEndereco().getBairro());
        pst.setObject(9, f.getEndereco().getCidade().getNome());
        pst.setObject(10, f.getEndereco().getEstado().getNome());
        pst.setObject(11, f.getEndereco().getPais().getNome());
        pst.setObject(12, f.getCnpj());
        
        pst.execute();
        
        connection.close();
        
        return f;
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
