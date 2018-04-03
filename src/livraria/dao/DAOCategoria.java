/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.dao;

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
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT CAT_ID_CATEGORIA, CAT_NOME FROM CATEGORIAS";
        
        pst = connection.prepareStatement(query);
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> ced = new ArrayList<>();
        
        while(rs.next()){
        
            Categoria cat = new Categoria();
            cat.setId(rs.getInt("CAT_ID_CATEGORIA"));
            cat.setNome(rs.getString("CAT_NOME"));
            
            ced.add(cat);
            
        }
                
        return ced;
        
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) {

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) {
    }
    
}
