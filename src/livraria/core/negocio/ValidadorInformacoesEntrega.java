package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidadorInformacoesEntrega implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        StringBuilder msg = new StringBuilder();
        
        Pedido pedido = (Pedido) entidadeDominio;
               
        if(pedido.getFrete().getEnderecoEntrega().getId() == 0)
            msg.append("Selecione ou cadastre um endereço de entrega<br>");
        
        if(msg.length() > 1)
            return msg.toString();
        else{       
            if(!pedido.isAtivo())            
                return "pedido_pagamento.jsp";
            else 
                return null;
        }
    }
    
}
