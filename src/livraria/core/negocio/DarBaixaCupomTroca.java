package livraria.core.negocio;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCupomTroca;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class DarBaixaCupomTroca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;
        
        if(!p.getPagamento().getCuponsTroca().isEmpty()){
            
            DAOCupomTroca cpt = new DAOCupomTroca();
            
            for(CupomTroca ct : p.getPagamento().getCuponsTroca()){
                
                if(ct.isUsado())
                    return "Cupom de Troca " + ct.getIdentificador() + " já foi usado";
                
                ct.setUsado(true);
                try {
                    cpt.alterar(ct);
                } catch (SQLException ex) {
                    Logger.getLogger(DarBaixaCupomTroca.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            
        }
        
        return null;
        
    }
    
}
