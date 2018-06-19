package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Pedido;

public class CalculadoraValorTotal implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido pedido = (Pedido) entidadeDominio;
        
        pedido.setValorTotal(0);
        
        for(ItemCarrinho ic : pedido.getCarrinho().getItens())   
            pedido.setValorTotal(pedido.getValorTotal() + ic.getValorSubtotal());   
        
        if(pedido.getFrete() != null)
            pedido.setValorTotal(pedido.getValorTotal() + pedido.getFrete().getValor());
        
        return null;
        
    }
    
    
    
    
}
