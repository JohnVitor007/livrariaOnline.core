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
import livraria.dominio.Editora;
import livraria.dominio.EntidadeDominio;

/**
 *
 * @author johnv
 */
public class DAOEditora extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Editora editora = (Editora) entidadeDominio;
        
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT EDI_ID_EDITORA, EDI_NOME FROM EDITORAS";
        
        if(editora.getNome() != null)
            query = "select edi_id_editora, edi_nome from editoras where edi_nome = ?";
        
        pst = connection.prepareStatement(query);
        
        if(editora.getNome() != null)
            pst.setObject(1, editora.getNome());
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> eds = new ArrayList<>();
        
        while(rs.next()){
        
            Editora ed = new Editora();
            ed.setId(rs.getInt("EDI_ID_EDITORA"));
            ed.setNome(rs.getString("EDI_NOME"));
            
            eds.add(ed);
            
        }
        
        connection.close();
                
        return eds;
        
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
