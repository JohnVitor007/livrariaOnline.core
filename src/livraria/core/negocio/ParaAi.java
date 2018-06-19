package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Pedido;

public class ParaAi implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;
        
        System.out.println("Valor Total: " + p.getValorTotal());
        System.out.println("Valor Frete: " + p.getFrete().getValor());
        System.out.println("Qtd: " + p.getCarrinho().getItens().size());
        
        for(ItemCarrinho ic : p.getCarrinho().getItens())
            System.out.println(ic.getQuantidade());
            
        return "Parei";
        
        
    }
    
}
