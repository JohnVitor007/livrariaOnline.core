
package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOUsuario;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;


public class ValidadorAcessoUsuario implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Usuario u = (Usuario) entidadeDominio;
            
        if(!u.isAtivo())
            return "Esta conta está inativada<br>";
        
        return null;
    }
    
}
