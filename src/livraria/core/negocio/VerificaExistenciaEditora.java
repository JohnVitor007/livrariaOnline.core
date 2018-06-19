package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOEditora;
import livraria.dominio.Editora;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;

public class VerificaExistenciaEditora implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Livro l = (Livro) entidadeDominio;
        
        if(l.getCapa() != null)
            return null;
        
        DAOEditora de = new DAOEditora();
        
        try {
            List<EntidadeDominio> eds = (List<EntidadeDominio>) de.consultar(l.getEditora());
        
            if(!eds.isEmpty()){
                
                l.getEditora().setId(((Editora) eds.get(0)).getId());                
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(VerificaExistenciaEditora.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
        return null;
        
    }
    
}
