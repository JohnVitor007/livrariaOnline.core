package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCategoria;
import livraria.dominio.Categoria;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;


public class VerificaExistenciaCategoria implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Livro l = (Livro) entidadeDominio;
        
        DAOCategoria dc = new DAOCategoria();
        
        if(l.getCapa() != null)
            return null;
        
        for(Categoria c : l.getCategoria()){
            
            try {
                
                List<EntidadeDominio> eds = (List<EntidadeDominio>) dc.consultar(c);
                                
                if(!eds.isEmpty()){
                    
                    Categoria categoria = (Categoria) eds.get(0);
                    c.setId(categoria.getId());
                    
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(VerificaExistenciaCategoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
     
        return null;
        
    }
    
    
    
}
