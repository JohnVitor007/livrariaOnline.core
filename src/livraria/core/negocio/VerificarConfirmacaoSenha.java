package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;

public class VerificarConfirmacaoSenha implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Cliente c = (Cliente) entidadeDominio;
        
        if(c.getUsuario().getSenha() != c.getUsuario().getSenhaRepetida())
            return "<br>Senhas não conferem";
        
        return null;
        
        
    }
    
    
    
}
