package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidadorPagamentoCartao implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;
        double valor;
        
        //1 - o valor minimo a ser pago por cartão em compras que foram utilizados mais de um cartão deve ser R$10 idenpendente se usou cupom ou não
        //2 - O valor mínimo a ser pago por cartão é R$10 se não tiver cupom
        //3 - O valor por ser qualquer (exceto negativo)
        
        
        if(!p.getPagamento().getCartoes().isEmpty()){
            
            if(p.getValorTotal() <= 0)
                return "Cartão sendo usado desnecessariamente";
                                    
            if(p.getPagamento().getCartoes().size() > 1 && (p.getValorTotal() / p.getPagamento().getCartoes().size()) < 10.00)
                return "O valor mínimo a ser pago por cada cartão é R$10";
        
            if(p.getPagamento().getCupomPromocional() == null && p.getPagamento().getCuponsTroca().isEmpty()){
                valor = p.getValorTotal() / p.getPagamento().getCartoes().size();
            
                if(valor < 10.00)
                    return "O valor mínimo a ser pago por cada cartão é R$10<br>";
            }
            
            
        }
        
        return null;
    }

    
    
}
