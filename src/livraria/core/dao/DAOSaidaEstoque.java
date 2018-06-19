package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;
import livraria.dominio.Saida;

public class DAOSaidaEstoque extends AbstractJDBCDAO {

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Saida saida = (Saida) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("select se_data_saida, se_quantidade, se_id_pedido from saida_estoque where se_id_livro = ?");
       
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, saida.getProduto().getId());
        
        List<EntidadeDominio> eds = new ArrayList();
        
        ResultSet rs = pst.executeQuery();
        
        Saida s;
        
        while(rs.next()){
            
            s = new Saida();
            s.setData(rs.getDate("se_data_saida"));
            s.setPedido(new Pedido());
            s.getPedido().setId((rs.getInt("se_id_pedido")));
            s.setQuantidade(rs.getInt("se_quantidade"));
            
            eds.add(s);
            
            
        }
        
        connection.close();
        
        return eds;
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Saida saida = (Saida) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into saida_estoque(se_data_saida, se_quantidade, se_id_pedido, se_id_livro) ");
        sql.append(" values (?,?,?,?)");
       
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, new java.sql.Date(saida.getData().getTime()));
        pst.setObject(2, saida.getQuantidade());
        pst.setObject(3, saida.getPedido().getId());
        pst.setObject(4, saida.getProduto().getId());
        
        pst.execute();
        
        DAOLivro dl = new DAOLivro();
        dl.alterar(saida.getProduto());
        
        connection.close();
        
        return saida;        
        
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
