package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.CategoriaAtivacao;
import livraria.dominio.CategoriaInativacao;
import livraria.dominio.EntidadeDominio;

public class DAOCategoriaInativacao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String sql = "select cin_id_categoria_inativacao, cin_nome_categoria_inativacao "+
                "from categorias_inativacao";
        
        pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> categoriasAtivacao = new ArrayList<>();
        
        CategoriaInativacao categoriaInativacao;
        
        while(rs.next()){
                  
            categoriaInativacao = new  CategoriaInativacao();
            categoriaInativacao.setId(rs.getInt("cin_id_categoria_inativacao"));
            categoriaInativacao.setNome(rs.getString("cin_nome_categoria_inativacao"));
            categoriasAtivacao.add(categoriaInativacao);
            
        }
        
        return categoriasAtivacao;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        return null;
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    }


    
}
