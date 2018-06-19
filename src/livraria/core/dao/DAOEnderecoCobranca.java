package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.Cidade;
import livraria.dominio.EnderecoCobranca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Estado;
import livraria.dominio.Pais;
import livraria.dominio.TipoEndereco;
import livraria.dominio.TipoLogradouro;

public class DAOEnderecoCobranca extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        EnderecoCobranca ec = (EnderecoCobranca) entidadeDominio;
        List<EntidadeDominio> eds = new ArrayList();
        
        
        StringBuilder query = new StringBuilder();
        
        query.append("select ec_tipo_logradouro, ec_logradouro, ec_numero, ec_complemento, ec_bairro, ec_cidade, ec_estado, ec_pais, ec_observacoes, ec_id_cliente, ec_tipo_endereco, ec_cep ");
        query.append(" from enderecos_cobranca where ec_id_cliente = ?");
        
        this.openConnection();
        
        EnderecoCobranca ecb;
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, ec.getPessoa().getId());
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            
            ecb = new EnderecoCobranca();
            ecb.setLogradouro(rs.getString("ec_logradouro"));
            ecb.setTipoLogradouro(new TipoLogradouro());
            ecb.getTipoLogradouro().setNome(rs.getString("ec_tipo_logradouro"));
            ecb.setNumero(rs.getInt("ec_numero"));
            ecb.setComplemento(rs.getString("ec_complemento"));
            ecb.setBairro(rs.getString("ec_bairro"));
            ecb.setCidade(new Cidade());
            ecb.getCidade().setNome(rs.getString("ec_cidade"));
            ecb.setEstado(new Estado());
            ecb.getEstado().setNome(rs.getString("ec_estado"));
            ecb.setPais(new Pais());
            ecb.getPais().setNome(rs.getString("ec_pais"));
            ecb.setObservacoes(rs.getString("ec_observacoes"));
            ecb.setTipoEndereco(new TipoEndereco());
            ecb.getTipoEndereco().setNome(rs.getString("ec_tipo_endereco"));
            ecb.setCep(rs.getString("ec_cep"));
            
            eds.add(ecb);
            
        }
        
        return eds;
                
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        EnderecoCobranca ec = (EnderecoCobranca) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("insert into enderecos_cobranca (ec_tipo_logradouro, ec_logradouro, ec_numero, ec_complemento, ec_bairro, ec_cidade, ec_estado, ec_pais, ec_observacoes, ec_id_cliente, ec_tipo_endereco, ec_cep)");
        query.append("values(?,?,?,?,?,?,?,?,?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, ec.getTipoLogradouro().getNome());
        pst.setObject(2, ec.getLogradouro());
        pst.setObject(3, ec.getNumero());
        pst.setObject(4, ec.getComplemento());
        pst.setObject(5, ec.getBairro());
        pst.setObject(6, ec.getCidade().getNome());
        pst.setObject(7, ec.getEstado().getNome());
        pst.setObject(8, ec.getPais().getNome());
        pst.setObject(9, ec.getObservacoes());
        pst.setObject(10, ec.getPessoa().getId());
        pst.setObject(11, ec.getCep());
        pst.setObject(12, ec.getTipoEndereco().getNome());
        
        pst.execute();
                
        connection.close();
        
        return ec;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        EnderecoCobranca ec = (EnderecoCobranca) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("update enderecos_cobranca set ec_tipo_logradouro = ?, ec_logradouro = ?, ec_numero = ?, ec_bairro = ?, ec_cidade = ?, ec_estado = ?, ec_pais = ?, ec_observacoes = ?, ec_complemento = ?, ec_tipo_endereco = ?, ec_cep = ? where ec_id_cliente = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, ec.getTipoLogradouro().getNome());
        pst.setObject(2, ec.getLogradouro());
        pst.setObject(3, ec.getNumero());
        pst.setObject(4, ec.getBairro());
        pst.setObject(5, ec.getCidade().getNome());
        pst.setObject(6, ec.getEstado().getNome());
        pst.setObject(7, ec.getPais().getNome());
        pst.setObject(8, ec.getObservacoes());
        pst.setObject(9, ec.getComplemento());
        pst.setObject(10, ec.getTipoEndereco().getNome());
        pst.setObject(11, ec.getCep());
        pst.setObject(12, ec.getPessoa().getId());
        
        pst.execute();
        
        connection.close();        
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
