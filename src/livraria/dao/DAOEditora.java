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
import livraria.dominio.Editora;
import livraria.dominio.EntidadeDominio;

/**
 *
 * @author johnv
 */
public class DAOEditora extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT EDI_ID_EDITORA, EDI_NOME FROM EDITORAS";
        
        pst = connection.prepareStatement(query);
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> eds = new ArrayList<>();
        
        while(rs.next()){
        
            Editora ed = new Editora();
            ed.setId(rs.getInt("EDI_ID_EDITORA"));
            ed.setNome(rs.getString("EDI_NOME"));
            
            eds.add(ed);
            
        }
                
        return eds;
        
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
