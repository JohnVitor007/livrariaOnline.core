package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class CalculadoraPagamentoCartao implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        //Verificar se o cartão não está sendo usado desnecessariamente - (se a compra for menor que 0)
        Pedido p = (Pedido) entidadeDominio;

        double valor = 0.0;

        if (!p.getPagamento().getCartoes().isEmpty()) {
            valor = p.getValorTotal() / p.getPagamento().getCartoes().size();
            for (Cartao c : p.getPagamento().getCartoes()) {
                c.setValorPago(valor);
            }
        }
        return null;

    }

}
