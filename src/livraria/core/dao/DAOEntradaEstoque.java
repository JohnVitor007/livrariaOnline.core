package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Entrada;
import livraria.dominio.Fornecedor;
import livraria.dominio.Livro;

public class DAOEntradaEstoque extends AbstractJDBCDAO {

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {

        Entrada entrada = (Entrada) entidadeDominio;

        List<EntidadeDominio> entradas = new ArrayList();

        StringBuilder sql = new StringBuilder();

        this.openConnection();

        sql.append("select it_id_item_entoque, it_data_entrada, it_quantidade, it_custo, it_preco");
        sql.append(" from item_estoque where it_id_livro = ?");

        PreparedStatement pst = connection.prepareStatement(sql.toString());

        pst.setInt(1, entrada.getItemEstoque().getProduto().getId());

        ResultSet rs = pst.executeQuery();

        Entrada e = null;

        while (rs.next()) {

            e = new Entrada();
            e.setId(rs.getInt("it_id_item_entoque"));
            e.setData(rs.getDate("it_data_entrada"));
            e.setQuantidade(rs.getInt("it_quantidade"));
            e.setCusto(rs.getDouble("it_custo"));
            e.setFornecedor(new Fornecedor());

            sql.setLength(0);
            sql.append("select itf_id_fornecedor from it_fornecedores where itf_id_fornecedor = ?");
            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, e.getId());

            ResultSet rsFor = pst.executeQuery();

            if(rsFor.next())
                e.getFornecedor().setId(rsFor.getInt("itf_id_fornecedor"));
            
            DAOFornecedor df = new DAOFornecedor();
            List<EntidadeDominio> fors = (List<EntidadeDominio>) df.consultar(e.getFornecedor());
            e.setFornecedor((Fornecedor) fors.get(0));

            entradas.add(e);

        }

        connection.close();

        return entradas;

    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {

        Entrada entrada = (Entrada) entidadeDominio;

        this.openConnection();

        PreparedStatement pst = null;

        StringBuilder sql = new StringBuilder();

        if (entrada.getFornecedor() != null && entrada.getFornecedor().getId() == 0) {

            sql.append("insert into fornecedores(for_ddd, for_telefone, for_razao_social, for_email, for_logradouro, for_tipo_logradouro, for_numero, for_bairro, for_cidade, for_estado, for_pais, for_cnpj)");
            sql.append("values(?,?,?,?,?,?,?,?,?,?,?,?) ");

            pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

            pst.setObject(1, entrada.getFornecedor().getTelefone().getDdd());
            pst.setObject(2, entrada.getFornecedor().getTelefone().getNumero());
            pst.setObject(3, entrada.getFornecedor().getRazaoSocial());
            pst.setObject(4, entrada.getFornecedor().getEmail());
            pst.setObject(5, entrada.getFornecedor().getEndereco().getLogradouro());
            pst.setObject(6, entrada.getFornecedor().getEndereco().getTipoLogradouro().getNome());
            pst.setObject(7, entrada.getFornecedor().getEndereco().getNumero());
            pst.setObject(8, entrada.getFornecedor().getEndereco().getBairro());
            pst.setObject(9, entrada.getFornecedor().getEndereco().getCidade().getNome());
            pst.setObject(10, entrada.getFornecedor().getEndereco().getEstado().getNome());
            pst.setObject(11, entrada.getFornecedor().getEndereco().getPais().getNome());
            pst.setObject(12, entrada.getFornecedor().getCnpj());

            pst.execute();

            ResultSet rs = pst.getGeneratedKeys();

            if (rs.next()) {
                entrada.getFornecedor().setId(rs.getInt(1));
            }

            sql.setLength(0);
        }

        sql.setLength(0);

        sql.append("insert into item_estoque(it_data_entrada, it_quantidade, it_custo, it_preco, it_id_livro) ");
        sql.append("values(?,?,?,?,?)");

        pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

        pst.setObject(1, new java.sql.Date(entrada.getData().getTime()));
        pst.setObject(2, entrada.getQuantidade());
        pst.setObject(3, entrada.getCusto());
        pst.setObject(4, entrada.getPreco());
        pst.setObject(5, entrada.getItemEstoque().getProduto().getId());

        pst.execute();
        
        ResultSet rs = pst.getGeneratedKeys();

        if(rs.next())
            entrada.setId(rs.getInt(1));

        if(entrada.getFornecedor() != null && entrada.getId() != 0){
            
            sql.setLength(0);
            sql.append("insert into it_fornecedores(itf_id_fornecedor, itf_id_item_estoque)values(?,?)");
            
            pst = connection.prepareStatement(sql.toString());
            
            pst.setObject(1, entrada.getFornecedor().getId());
            pst.setObject(2, entrada.getId());
            
            pst.execute();
            
        }
        
        connection.close();

        DAOLivro dl = new DAOLivro();
        dl.alterar(entrada.getItemEstoque().getProduto());

        return entrada;

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {

        Entrada e = (Entrada) entidadeDominio;

        Livro l = (Livro) e.getItemEstoque().getProduto();

        DAOLivro dl = new DAOLivro();

        dl.alterar(l);

    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
