
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Cidade;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Estado;
import livraria.dominio.Pais;
import livraria.dominio.TipoEndereco;
import livraria.dominio.TipoLogradouro;


public class DAOEnderecoEntrega extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {

        EnderecoEntrega ee = (EnderecoEntrega) entidadeDominio;
        
        Collection<EntidadeDominio> ees = new ArrayList<>();
        
        StringBuilder query = new StringBuilder();
        
        
        if(ee.getId() != 0){
            query.append("select e.ee_id_endereco_entrega, e.ee_apelido, e.ee_tipo_endereco, e.ee_logradouro, e.ee_numero, e.ee_bairro, e.ee_cidade, e.ee_estado, e.ee_pais, e.ee_observacoes, e.ee_complemento, e.ee_tipo_logradouro, e.ee_tipo_logradouro, e.ee_cep ");
            query.append(" from enderecos_entrega e where ");
            query.append("e.ee_id_endereco_entrega = ?");
        }else{
            query.append("select e.ee_id_endereco_entrega, e.ee_apelido, e.ee_tipo_endereco, e.ee_logradouro, e.ee_numero, e.ee_bairro, e.ee_cidade, e.ee_estado, e.ee_pais, e.ee_observacoes, e.ee_complemento, e.ee_tipo_logradouro, e.ee_tipo_logradouro, e.ee_cep ");
            query.append(" from enderecos_entrega e inner join ee_clientes ee on e.ee_id_endereco_entrega = ee.eec_id_endereco_entrega where e.ee_ativo = true ");
            query.append("and ee.eec_id_cliente = ?");
        }
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        if(ee.getId() != 0)
            pst.setInt(1, ee.getId());
        else
            pst.setInt(1, ee.getPessoa().getId());
        
        ResultSet rs = pst.executeQuery();
        
        EnderecoEntrega eeBuffer;
        
        while(rs.next()){
            
            eeBuffer = new EnderecoEntrega();
            eeBuffer.setId(rs.getInt("ee_id_endereco_entrega"));
            eeBuffer.setApelido(rs.getString("ee_apelido"));
            eeBuffer.setTipoEndereco(new TipoEndereco());
            eeBuffer.getTipoEndereco().setNome(rs.getString("ee_tipo_endereco"));
            eeBuffer.setLogradouro(rs.getString("ee_logradouro"));
            eeBuffer.setNumero(rs.getInt("ee_numero"));
            eeBuffer.setBairro(rs.getString("ee_bairro"));
            eeBuffer.setCidade(new Cidade());
            eeBuffer.getCidade().setNome(rs.getString("ee_cidade"));
            eeBuffer.setEstado(new Estado());
            eeBuffer.getEstado().setNome(rs.getString("ee_estado"));
            eeBuffer.setPais(new Pais());
            eeBuffer.getPais().setNome(rs.getString("ee_pais"));
            eeBuffer.setTipoLogradouro(new TipoLogradouro());
            eeBuffer.getTipoLogradouro().setNome(rs.getString("ee_tipo_logradouro"));
            eeBuffer.setObservacoes(rs.getString("ee_observacoes"));
            eeBuffer.setComplemento(rs.getString("ee_complemento"));
            eeBuffer.setCep(rs.getString("ee_cep"));
            
            ees.add(eeBuffer);
            
        }
        
        connection.close();
        
        return ees;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        
        EnderecoEntrega ee = (EnderecoEntrega) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("insert into enderecos_entrega(ee_tipo_logradouro, ee_apelido, ee_tipo_endereco, ee_logradouro, ee_numero, ee_complemento, ee_bairro, ee_cidade, ee_estado, ee_pais, ee_observacoes, ee_ativo, ee_cep) ");
        query.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?)");

        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);

        pst.setObject(1, ee.getTipoLogradouro().getNome());
        pst.setObject(2, ee.getApelido());
        pst.setObject(3, ee.getTipoEndereco().getNome());
        pst.setObject(4, ee.getLogradouro());
        pst.setObject(5, ee.getNumero());
        pst.setObject(6, ee.getComplemento());
        pst.setObject(7, ee.getBairro());
        pst.setObject(8, ee.getCidade().getNome());
        pst.setObject(9, ee.getEstado().getNome());
        pst.setObject(10, ee.getPais().getNome());
        pst.setObject(11, ee.getObservacoes());
        pst.setObject(12, true);
        pst.setObject(13, ee.getCep());

        pst.execute();
        
        ResultSet resultSet = pst.getGeneratedKeys();
        if(resultSet.next())                    
            ee.setId(resultSet.getInt(1));

        if(ee.getPessoa() != null){
        
            query.setLength(0);

            query.append("insert into ee_clientes(eec_id_cliente, eec_id_endereco_entrega) ");
            query.append("values(?, ?)");

            pst = connection.prepareStatement(query.toString());

            pst.setInt(1, ee.getPessoa().getId());
            pst.setInt(2, ee.getId());

            pst.execute();
        }
        connection.close();
        
        return ee;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        EnderecoEntrega ee = (EnderecoEntrega) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
                
        query.append("update enderecos_entrega set ee_apelido = ?, ee_tipo_endereco = ?, ee_logradouro = ?, ee_numero = ?, ee_bairro = ?, ee_cidade = ?, ee_estado = ?, ee_pais = ?, ee_observacoes = ?, ee_complemento = ?, ee_tipo_logradouro = ? where ee_id_endereco_entrega = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, ee.getApelido());
        pst.setObject(2, ee.getTipoEndereco().getNome());
        pst.setObject(3, ee.getLogradouro());
        pst.setObject(4, ee.getNumero());
        pst.setObject(5, ee.getBairro());
        pst.setObject(6, ee.getCidade().getNome());
        pst.setObject(7, ee.getEstado().getNome());
        pst.setObject(8, ee.getPais().getNome());
        pst.setObject(9, ee.getObservacoes());
        pst.setObject(10, ee.getComplemento());
        pst.setObject(11, ee.getTipoLogradouro().getNome());
        pst.setObject(12, ee.getId());
        
        pst.execute();
        
        connection.close();
        
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        
        EnderecoEntrega ee = (EnderecoEntrega) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("update enderecos_entrega set ee_ativo = false where ee_id_endereco_entrega = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setInt(1, ee.getId());
        
        pst.execute();
        
        connection.close();
        
        
        
    }
    
    
    
}
