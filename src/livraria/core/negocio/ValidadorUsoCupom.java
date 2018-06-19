package livraria.core.negocio;

import java.util.Collections;
import java.util.Comparator;
import livraria.core.IStrategy;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidadorUsoCupom implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Pedido pedido = (Pedido) entidadeDominio;

        //1 - Abater o valor do cupom promocional
        //2 - Abater o valor do cupom troca e analisar se o usuário está usando cupom desnecessáriamente
        //3 - Ver se o valor abate na compra        
        if (pedido.getPagamento().getCupomPromocional() != null) {
            pedido.setValorTotal(pedido.getValorTotal() - pedido.getPagamento().getCupomPromocional().getValor());
            if (pedido.getValorTotal() < 0) {
                pedido.getPagamento().getCupomPromocional().setValorPago(pedido.getPagamento().getCupomPromocional().getValor() + pedido.getValorTotal());
            }else 
                pedido.getPagamento().getCupomPromocional().setValorPago(pedido.getPagamento().getCupomPromocional().getValor());
        }

        if (!pedido.getPagamento().getCuponsTroca().isEmpty()) {
            
            Collections.sort(pedido.getPagamento().getCuponsTroca(), new Comparator<CupomTroca>(){
                
                public int compare(CupomTroca c1, CupomTroca c2){
                    return Double.compare(c2.getValor(), c1.getValor());
                }
                
            });
            
            if (pedido.getValorTotal() <= 0 && pedido.getPagamento().getCupomPromocional() != null) {
                return "Cupons de Troca estão sendo usados desnecessariamente";
            }

            int qtdCuponsTroca = pedido.getPagamento().getCuponsTroca().size();

            for (CupomTroca ct : pedido.getPagamento().getCuponsTroca()) {

                if (pedido.getValorTotal() <= 0 && qtdCuponsTroca > 0) {
                    return "Cupons de Troca estão sendo usados desnecesariamente";
                }

                pedido.setValorTotal(pedido.getValorTotal() - ct.getValor());

                if (pedido.getValorTotal() < 0) {
                    ct.setValorPago(ct.getValor() + pedido.getValorTotal());
                } else {
                    ct.setValorPago(ct.getValor());
                }

                qtdCuponsTroca--;

            }

        }

        if (pedido.getValorTotal() > 0 && pedido.getPagamento().getCartoes().isEmpty()) {
            return "Cupon(s) insuficientes para pagar a compra";
        }

        return null;
    }    
}
