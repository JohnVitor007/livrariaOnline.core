//Classe de validação de existência de livros;
//@author John Vitor da Silva Quispe
//@date 25/03/2018
package livraria.core.negocio;

import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;



public class ValidarExistenciaLivro implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Livro livro = (Livro) entidadeDominio;
        
        DAOLivro daoLivro = new DAOLivro();
        
        Collection<EntidadeDominio> livros = null;
        try {
            livros = daoLivro.consultar(livro);
            if(livros.size() > 0)
                return "Livro já cadastrado<br>";
        } catch (SQLException ex) {
            Logger.getLogger(ValidarExistenciaLivro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return null;

    }
    
}
