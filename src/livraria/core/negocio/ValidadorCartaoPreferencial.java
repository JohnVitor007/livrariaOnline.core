package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCartao;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;

public class ValidadorCartaoPreferencial implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Cartao cartao = (Cartao) entidadeDominio;
        
        DAOCartao dc = new DAOCartao();
        Cartao cb = new Cartao();
        cb.setCliente(cartao.getCliente());
        
        List<EntidadeDominio> eds = null;
        
        try {
            
            eds = (List<EntidadeDominio>) dc.consultar(cb);
            
            if(eds.isEmpty()){
                cartao.setPreferencial(true);                
                return null;
            }
            
            if(!cartao.isPreferencial()){
            
                boolean naoTemPreferencial = true;
                
                for(EntidadeDominio ed : eds){
                    Cartao c = (Cartao) ed;
                    if(c.isPreferencial())
                        naoTemPreferencial = false;
                    
                    if(naoTemPreferencial)
                        return "Selecione ao menos um cartão para ser preferencial";
                    
                }
                
                return null;
            
            }
            
            for(EntidadeDominio ed : eds){
                Cartao c = (Cartao) ed;
                if(c.getId() != cartao.getId()){
                    if(c.isPreferencial()){
                        c.setPreferencial(false);
                        dc.alterar(c);
                    }
                }
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorCartaoPreferencial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }
    
    
    
}
