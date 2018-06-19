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


public class ValidadorTrocaPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Pedido p = (Pedido) entidadeDominio;
        
        if(!p.isAtivo() || p.getCarrinho() == null )
            return null;
        
        p.setAtivo(true);
        IStrategy tp = null;
        /*
        if(!p.isTroca())        
            return null;
        
        p.setAtivo(true);
        /*
        if(p.getCarrinho().getItens().isEmpty())
            return "Selecione ao menos um item do carrinho para troca";
        */
        //IStrategy tp = new TrocarPedido();
        //tp.processar(p);        
        
        if(p.isTroca()){
            tp = new TrocarTodoPedido();
            tp.processar(p);
        }else{
            tp = new TrocarItemPedido();
            tp.processar(p);
        }
        
        return null;
        
        /*-
        
        
        Pedido pBuffer = new Pedido();
       
        pBuffer.setId(p.getId());
        
        DAOPedido daoPed = new DAOPedido();
        
        List<EntidadeDominio> eds = null;
        
        try {        
            eds = (List<EntidadeDominio>) daoPed.consultar(pBuffer);
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorTrocaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pBuffer = (Pedido) eds.get(0);
        
        
        
        p.setCarrinho(pBuffer.getCarrinho());
        */
        
    }
    
    
    
}
