package livraria.core.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOAdministrador;
import livraria.core.dao.DAOCliente;
import livraria.dominio.Administrador;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class ValidadorExistenciaUsuario implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
            
        String msg = "";
                        
        Usuario u = (Usuario) entidadeDominio;
        
        List<EntidadeDominio> eds = new ArrayList<>();
        
        DAOCliente dc = new DAOCliente();
        DAOAdministrador da = new DAOAdministrador();
                
        try {            

            Cliente cli = new Cliente();
            cli.setUsuario(u);
            eds = (List<EntidadeDominio>) dc.consultar(cli);
            
            if(eds.size() > 0){
               
               Cliente cliente = (Cliente) eds.get(0);
               IStrategy vau = new ValidadorAcessoUsuario();
               
               if((msg = vau.processar(cliente.getUsuario())) != null)
                   return msg;
               else{
                   u.setTipoUsuario("Cliente");
                   return null;
               }               
               
            }
            

            Administrador adm = new Administrador();
            adm.setUsuario(u);
            eds = (List<EntidadeDominio>) da.consultar(adm);
            
            if(eds.size() > 0){
               u.setTipoUsuario("Administrador");
               return null;
            }
                            
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorExistenciaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eds.size() == 0)
            return "Email ou senha incorretos";
        
        return null;
        
    }
    
}
