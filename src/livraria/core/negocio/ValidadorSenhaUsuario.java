package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class ValidadorSenhaUsuario implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        String m = "";
        
        Usuario u = (Usuario) entidadeDominio;
        
        if(!u.getSenha().equals(u.getSenhaRepetida()))
            m += "As senhas não conferem<br>";
        else{
            ValidadorSenhaForte vsf = new ValidadorSenhaForte();
            
            String mb = "";
            
            if((mb = vsf.processar(u)) != null)
                m += mb;
        }
            
        
        
        
        if(m.length() > 0)
            return m;
        else                     
            return null;
    }
    
}
