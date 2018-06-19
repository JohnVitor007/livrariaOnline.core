package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cupom;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;

public class ExcluirCupom implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        boolean excluiu = false;
        
        Cupom cupom = (Cupom) entidadeDominio;
        
        int i = 0;
        int remover = 0;
        
        
        for(CupomTroca ct : cupom.getPedido().getPagamento().getCuponsTroca()){
            if(ct.getId() == cupom.getId()){
                remover = i;
                excluiu = true;
            }
            
            i++;
        }
        
        
        if(excluiu)
            cupom.getPedido().getPagamento().getCuponsTroca().remove(remover);
        
            
        if(!excluiu)
            cupom.getPedido().getPagamento().setCupomPromocional(null);
       
            
        return null;
            
    }
    
}
