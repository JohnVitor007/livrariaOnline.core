package livraria.core.negocio;

import java.util.List;
import livraria.core.IStrategy;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidadorInformacoesPagamento implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        
        boolean cupomTroca = false;
        boolean cupomPromocional = false;
        boolean cartoes = false;
        
        StringBuilder msg = new StringBuilder();
        
        Pedido pedido = (Pedido) entidadeDominio;
                
        if(pedido.getPagamento().getCartoes().isEmpty())
            cartoes = true;
        
        if(pedido.getPagamento().getCupomPromocional() == null)
            cupomPromocional = true;
            
        if(pedido.getPagamento().getCuponsTroca().isEmpty())
            cupomTroca = true;
        
        if((cupomTroca && cupomPromocional && cartoes))
            msg.append("Selecione ao menos uma forma de pagamento");   
        
        if(msg.length() > 0)
            return msg.toString();
        else
            return null;        
    
    }
    
    
    
}
