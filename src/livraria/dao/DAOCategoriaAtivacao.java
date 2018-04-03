package livraria.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.CategoriaAtivacao;
import livraria.dominio.EntidadeDominio;

public class DAOCategoriaAtivacao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        CategoriaAtivacao categoriaAtivacao = (CategoriaAtivacao) entidadeDominio;
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String sql = "select caa_id_categoria_ativacao, caa_nome_categoria_ativacao "+
                "from categorias_ativacao";
        
        if(categoriaAtivacao.getId() != 0)
            sql+=" where caa_id_categoria_ativacao = ?";
        
        pst = connection.prepareStatement(sql);
        
        if(categoriaAtivacao.getId() != 0)
            pst.setInt(1, categoriaAtivacao.getId());
        
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> categoriasAtivacao = new ArrayList<>();
        
        while(rs.next()){
                  
            categoriaAtivacao = new CategoriaAtivacao();
            categoriaAtivacao.setId(rs.getInt("caa_id_categoria_ativacao"));
            categoriaAtivacao.setNome(rs.getString("caa_nome_categoria_ativacao"));
            categoriasAtivacao.add(categoriaAtivacao);
            
        }
        
        
        return categoriasAtivacao;
        
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
