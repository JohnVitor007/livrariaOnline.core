package livraria.core.negocio;

import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCupomTroca;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class GeradorCupomTroca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        
        Pedido pedido = (Pedido) entidadeDominio;

        //1 - Verificar se é Troca
        //2 - Verificar se o valor da compra é menor que 0
        
        if (pedido.isAtivo() && pedido.getPagamento().getCartoes().isEmpty() && (pedido.isTroca() || pedido.getValorTotal() < 0) ) {            
                        
            CupomTroca ct = new CupomTroca();
            
            if(pedido.isTroca()){
                ct.setValor(pedido.getValorTotal());
            }
            
            if(pedido.getValorTotal() < 0)
                ct.setValor(pedido.getValorTotal() * -1);
            
            pedido.setValorTotal(0);
            
            String uniqueID = UUID.randomUUID().toString();
            uniqueID = uniqueID.toUpperCase().replaceAll("-", "").substring(24, 32);
            
            ct.setIdentificador("TROCA-" + uniqueID);
            ct.setUsado(false);
            ct.setCliente(pedido.getCliente());

            DAOCupomTroca dct = new DAOCupomTroca();
            try {
                dct.salvar(ct);
            } catch (SQLException ex) {
                Logger.getLogger(GeradorCupomPromocional.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    
        return null;
    
    }
    
}
