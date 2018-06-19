/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.core.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import livraria.dominio.Cidade;
import livraria.dominio.Cliente;
import livraria.dominio.Endereco;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Estado;
import livraria.dominio.Pais;
import livraria.dominio.Telefone;
import livraria.dominio.TipoEndereco;
import livraria.dominio.TipoLogradouro;
import livraria.dominio.Usuario;


public class DAOCliente extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException, ClassCastException {
        
        Cliente cliente = (Cliente) entidadeDominio;
       
        List<EntidadeDominio> clientes = new ArrayList<>();
        StringBuilder query = new StringBuilder();
       
        //Query de seleção de clientes
        query.append("select cli.cli_id_cliente, cli.cli_nome, cli.cli_data_nascimento, cli.cli_genero, cli.cli_cpf, cli.cli_ddd, cli.cli_numero_telefone, cli.cli_status, cli.cli_email, cli.cli_senha, cli.cli_ranking, cli.cli_data_cadastro, ");
        query.append("cli_tipo_logradouro, cli_tipo_endereco, cli_logradouro, cli_numero, cli_complemento, cli_bairro, cli_cidade, cli_estado, cli_pais, cli_end_observacoes, cli_cep, ");
        query.append("cli.cli_status, cli_email, cli_ativo ");
        query.append("from clientes cli where 1 = 1 ");
               
        if(cliente.getId() != 0)
            query.append("and cli.cli_id_cliente =  ?");
        
        
        if(cliente.getUsuario ()!= null)
            query.append("and cli_email = ? and cli_senha = ?");
        
        query.append(" order by cli_ranking asc ");
        
        //Conexão com banco de dados

        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
              
        if(cliente.getId() != 0)
            pst.setObject(1, cliente.getId());
        
        if(cliente.getUsuario ()!= null){
            pst.setObject(1, cliente.getUsuario().getEmail());
            pst.setObject(2, cliente.getUsuario().getSenha());
        }
        
        ResultSet rs = pst.executeQuery();
        
        Cliente c = null;
        
        while(rs.next()){
            
            c = new Cliente();
                        
            //Integer
            c.setId(rs.getInt("cli_id_cliente"));
            c.setRanking(rs.getInt("cli_ranking"));        
            
            Telefone t = new Telefone();
            t.setDdd(rs.getInt("cli_ddd"));
            t.setNumero(rs.getInt("cli_numero_telefone"));
            c.setTelefone(t);
            
            
            //String
            c.setNome(rs.getString("cli_nome"));
            c.setGenero(rs.getString("cli_genero"));
            c.setCpf(rs.getString("cli_cpf"));
            
            
            //Data
            c.setDataNascimento(rs.getDate("cli_data_nascimento"));
            c.setDataCadastro(rs.getDate("cli_data_cadastro"));
          
            //DAOEnderecoEntrega dee = new DAOEnderecoEntrega();
            
            c.setEnderecoResidencial(new Endereco());
            c.getEnderecoResidencial().setTipoLogradouro(new TipoLogradouro());
            c.getEnderecoResidencial().getTipoLogradouro().setNome(rs.getString("cli_tipo_logradouro"));
            c.getEnderecoResidencial().setTipoEndereco(new TipoEndereco());
            c.getEnderecoResidencial().getTipoEndereco().setNome(rs.getString("cli_tipo_endereco"));
            c.getEnderecoResidencial().setLogradouro(rs.getString("cli_logradouro"));
            c.getEnderecoResidencial().setNumero(rs.getInt("cli_numero"));
            c.getEnderecoResidencial().setComplemento(rs.getString("cli_complemento"));
            c.getEnderecoResidencial().setBairro(rs.getString("cli_bairro"));
            c.getEnderecoResidencial().setCidade(new Cidade());
            c.getEnderecoResidencial().getCidade().setNome(rs.getString("cli_cidade"));
            c.getEnderecoResidencial().setEstado(new Estado());
            c.getEnderecoResidencial().getEstado().setNome(rs.getString("cli_estado"));
            c.getEnderecoResidencial().setPais(new Pais());
            c.getEnderecoResidencial().getPais().setNome(rs.getString("cli_pais"));
            c.getEnderecoResidencial().setObservacoes(rs.getString("cli_end_observacoes"));
            c.getEnderecoResidencial().setCep(rs.getString("cli_cep"));
            c.setUsuario(new Usuario());
            c.getUsuario().setStatus(rs.getString("cli_status"));
            c.getUsuario().setEmail(rs.getString("cli_email"));
            c.getUsuario().setAtivo(rs.getBoolean("cli_ativo"));
                        
            clientes.add(c);           
            
        }
        
        
        connection.close();
        return clientes;
    
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Cliente cliente = (Cliente) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        
        query.append("insert into clientes (cli_nome, cli_data_nascimento, cli_genero, cli_cpf, cli_ddd, cli_numero_telefone, cli_status, cli_email, cli_senha, cli_data_cadastro, cli_ativo, cli_ranking, cli_tipo_telefone,");
        query.append("cli_tipo_logradouro, cli_tipo_endereco, cli_logradouro, cli_numero, cli_complemento, cli_bairro, cli_cidade, cli_estado, cli_pais, cli_end_observacoes, cli_cep)");
        query.append("values(?,?,?,?,?,?,?,?,?,?,?,0,?,?,?,?,?,?,?,?,?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);        
        
        pst.setObject(1, cliente.getNome());
        pst.setDate(2, Date.valueOf((cliente.getDataNascimento().getYear() + 1900) + "-" + (cliente.getDataNascimento().getMonth() + 1) + "-" +(cliente.getDataNascimento().getDate())));
        pst.setObject(3, cliente.getGenero());
        pst.setObject(4, cliente.getCpf());
        pst.setObject(5, cliente.getTelefone().getDdd());
        pst.setObject(6, cliente.getTelefone().getNumero());
        pst.setObject(7, cliente.getUsuario().getStatus());
        pst.setObject(8, cliente.getUsuario().getEmail());
        pst.setObject(9, cliente.getUsuario().getSenha());
        pst.setObject(10, Date.valueOf((cliente.getDataNascimento().getYear() + 1900) + "-" + (cliente.getDataNascimento().getMonth() + 1) + "-" +(cliente.getDataNascimento().getDate())));
        pst.setObject(11, cliente.getUsuario().isAtivo());
        pst.setObject(12, cliente.getTelefone().getTipo());
        pst.setObject(13, cliente.getEnderecoResidencial().getTipoLogradouro().getNome());
        pst.setObject(14, cliente.getEnderecoResidencial().getTipoEndereco().getNome());
        pst.setObject(15, cliente.getEnderecoResidencial().getLogradouro());
        pst.setObject(16, cliente.getEnderecoResidencial().getNumero());
        pst.setObject(17, cliente.getEnderecoResidencial().getComplemento());
        pst.setObject(18, cliente.getEnderecoResidencial().getBairro());
        pst.setObject(19, cliente.getEnderecoResidencial().getCidade().getNome());
        pst.setObject(20, cliente.getEnderecoResidencial().getEstado().getNome());
        pst.setObject(21, cliente.getEnderecoResidencial().getPais().getNome());
        pst.setObject(22, cliente.getEnderecoResidencial().getObservacoes());
        pst.setObject(23, cliente.getEnderecoResidencial().getCep());
        
        pst.execute();
        
        ResultSet rs = pst.getGeneratedKeys();
        
        if(rs.next())
            cliente.setId(rs.getInt(1));
        
        connection.close();
        
        DAOEnderecoEntrega daoEnderecoEntrega = new DAOEnderecoEntrega();
        
        Collection<EnderecoEntrega> ees = cliente.getEnderecosEntrega();
        
        for(EnderecoEntrega ee : ees)
            daoEnderecoEntrega.salvar(ee);

        DAOEnderecoCobranca daoEnderecoCobranca = new DAOEnderecoCobranca();
        daoEnderecoCobranca.salvar(cliente.getEnderecoCobranca());
             
        return cliente;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Cliente cliente = (Cliente) entidadeDominio;
        
        StringBuilder query = new StringBuilder();
        query.append("update clientes set cli_nome = ?, cli_genero = ?, cli_ddd = ?, cli_numero_telefone = ?, cli_tipo_telefone = ?, cli_email = ?, ");
        query.append("cli_tipo_logradouro = ?, cli_tipo_endereco = ?, cli_logradouro = ?, cli_numero = ?, cli_complemento = ?, cli_bairro = ?, cli_cidade = ?, cli_estado = ?, cli_pais = ?, cli_end_observacoes = ? ");
        query.append("where cli_id_cliente = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());
        
        pst.setObject(1, cliente.getNome());
        pst.setObject(2, cliente.getGenero());
        pst.setObject(3, cliente.getTelefone().getDdd());
        pst.setObject(4, cliente.getTelefone().getNumero());
        pst.setObject(5, cliente.getTelefone().getTipo());
        pst.setObject(6, cliente.getUsuario().getEmail());
        pst.setObject(7, cliente.getEnderecoResidencial().getTipoLogradouro().getNome());
        pst.setObject(8, cliente.getEnderecoResidencial().getTipoEndereco().getNome());
        pst.setObject(9, cliente.getEnderecoResidencial().getLogradouro());
        pst.setObject(10, cliente.getEnderecoResidencial().getNumero());
        pst.setObject(11, cliente.getEnderecoResidencial().getComplemento());
        pst.setObject(12, cliente.getEnderecoResidencial().getBairro());
        pst.setObject(13, cliente.getEnderecoResidencial().getCidade().getNome());
        pst.setObject(14, cliente.getEnderecoResidencial().getEstado().getNome());
        pst.setObject(15, cliente.getEnderecoResidencial().getPais().getNome());
        pst.setObject(16, cliente.getEnderecoResidencial().getObservacoes());
        pst.setObject(17, cliente.getId());
                
        pst.execute();
        
        DAOEnderecoCobranca dc = new DAOEnderecoCobranca();
        
        dc.alterar(cliente.getEnderecoCobranca());
        
        connection.close();
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
