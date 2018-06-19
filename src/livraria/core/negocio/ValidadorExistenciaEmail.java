
package livraria.core.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOUsuario;
import livraria.dominio.Administrador;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;


public class ValidadorExistenciaEmail implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        
        Cliente cliente = null;
        Administrador administrador=  null;
        
        Usuario u = null;
        
        if(entidadeDominio.getClass().getName().equals(Cliente.class.getName())){        
            cliente = (Cliente) entidadeDominio;
            u = cliente.getUsuario();
        }else{
            administrador = (Administrador) entidadeDominio;
            u = administrador.getUsuario();
        }
                       
        u.setSenhaAnterior("0");
        
        DAOUsuario du = new DAOUsuario();
        
        System.out.println(u.getEmail());
        
        Collection<EntidadeDominio> eds = new ArrayList<>();
        try {
            eds = du.consultar((u));
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorExistenciaEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eds.size() > 0)
            return "Email já cadastrado<br>";
        
        return null;
        
        
    }

    
    
    
}
