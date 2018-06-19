package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOConfiguracao;
import livraria.core.dao.DAOLivro;
import livraria.dominio.Configuracao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;

public class ValidadorQtdeMinimaVenda implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        DAOLivro dl = new DAOLivro();
        DAOConfiguracao dcon = new DAOConfiguracao();
        
        Livro l = new Livro();
        Configuracao c = new Configuracao();
        
        List<EntidadeDominio> eds = null;
        List<EntidadeDominio> confs = null;
        
        try {
            eds = (List<EntidadeDominio>) dl.consultar(l);
            confs = (List<EntidadeDominio>) dcon.consultar(c);
            
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorQtdeMinimaVenda.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        c = (Configuracao) confs.get(0);
                 
        for(EntidadeDominio e : eds){
            
            Livro livro = (Livro) e;
                        
            
        }
        
        return null;
        
    }
    
}
