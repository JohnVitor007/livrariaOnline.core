package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Pedido;

public class CalculadoraValorSubtotal implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        
        Pedido pedido = (Pedido) entidadeDominio;
        
        for(ItemCarrinho ic : pedido.getCarrinho().getItens()){
            ic.setValorSubtotal(0);
            ic.setValorSubtotal(ic.getProduto().getPreco() * ic.getQuantidade());
        }
        return null;
        
        
    }
    
}
