package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidarCupomTroca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;
        
        
        return null;
        
    }

    
    
}

