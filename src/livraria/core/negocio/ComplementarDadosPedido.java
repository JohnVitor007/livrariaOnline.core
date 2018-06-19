package livraria.core.negocio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Pedido;
import livraria.dominio.Status;

public class ComplementarDadosPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido pedido = (Pedido) entidadeDominio;

        pedido.setData(new Date());
        
        pedido.setStatuses(new ArrayList());
        pedido.setAtivo(true);
        pedido.setData(new Date());     
        pedido.setCancelado(false);
        
        for(ItemCarrinho ic : pedido.getCarrinho().getItens()){
            
            ic.setStatus("Em processamento");
            ic.setValorSubtotal(ic.getQuantidade() * ic.getProduto().getPreco());
            
        }
        
        return null;
        
    }   
}