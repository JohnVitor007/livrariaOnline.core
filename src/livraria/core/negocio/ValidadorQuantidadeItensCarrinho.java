package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Carrinho;
import livraria.dominio.EntidadeDominio;

public class ValidadorQuantidadeItensCarrinho implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Carrinho c = (Carrinho) entidadeDominio;
        
        if(c.getItens().size() == 0)
            return "Ao menos adicione um item no carrinho";
        
        return null;
        
    }
    
}
