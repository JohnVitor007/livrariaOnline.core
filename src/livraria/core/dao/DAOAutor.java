//Classes para consultar Grupo de Precificação
//@author John Vitor da Silva Quispe
//@date 20/03/2018

package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Autor;
import livraria.dominio.EntidadeDominio;

public class DAOAutor extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Autor autor = (Autor) entidadeDominio;
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT AUT_ID_AUTOR, AUT_NOME FROM AUTORES";
        
        if(autor.getNome() != null)
            query = "select aut_id_autor, aut_nome from autores where aut_nome = ?";
        
                
        pst = connection.prepareStatement(query);
        
        if(autor.getNome() != null)
            pst.setObject(1, autor.getNome());
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> auts = new ArrayList<>();
        
        while(rs.next()){
        
            Autor aut = new Autor();
            aut.setId(rs.getInt("AUT_ID_AUTOR"));
            aut.setNome(rs.getString("AUT_NOME"));
            
            auts.add(aut);
            
        }
         
        connection.close();
        
        return auts;
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException{
        
        return null;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException{
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException{
    }
    
    
    
}
