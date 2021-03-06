/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.dominio.Categoria;
import livraria.dominio.Editora;
import livraria.dominio.EntidadeDominio;

/**
 *
 * @author johnv
 */
public class DAOCategoria extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Categoria categoria = (Categoria) entidadeDominio;
       
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        
        String query = "SELECT CAT_ID_CATEGORIA, CAT_NOME FROM CATEGORIAS";
        
        if(categoria.getNome() != null)
            query = "select cat_id_categoria, cat_nome from categorias where cat_nome = ?";
        
        
        pst = connection.prepareStatement(query);
        
        if(categoria.getNome() != null)
            pst.setObject(1, categoria.getNome());
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> ced = new ArrayList<>();
        
        while(rs.next()){
        
            Categoria cat = new Categoria();
            cat.setId(rs.getInt("CAT_ID_CATEGORIA"));
            cat.setNome(rs.getString("CAT_NOME"));
            
            ced.add(cat);
            
        }
                
        connection.close();
        
        return ced;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) {

        return null;
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) {
    }
    
}
