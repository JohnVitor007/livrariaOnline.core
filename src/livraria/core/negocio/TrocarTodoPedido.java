package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOPedido;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Pedido;

public class TrocarTodoPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;

        List<EntidadeDominio> eds;
        DAOPedido daoPed = new DAOPedido();
        
        Pedido pBuffer = new Pedido();
        pBuffer.setId(p.getId());
        
        try {
            eds = (List<EntidadeDominio>) daoPed.consultar(p);
            pBuffer = (Pedido) eds.get(0);
            
        } catch (SQLException ex) {
            Logger.getLogger(TrocarItemPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        for(ItemCarrinho ic : pBuffer.getCarrinho().getItens())
            ic.getProduto().setStatus("Em Troca");
        
        p.setCarrinho(pBuffer.getCarrinho());
        
        IStrategy ts = new TrocarStatusPedido();
        ts.processar(p);
        
        p.setTroca(false);
        
        return null;
    }
    
}
