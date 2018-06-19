package livraria.core.negocio;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.core.dao.DAOPedido;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;
import livraria.dominio.Pedido;
import livraria.dominio.Status;

public class TrocarItemPedido implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Pedido p = (Pedido) entidadeDominio;
        
        Status s = new Status();
        s.setNome("Em Troca");
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        String hora = sdf.format(new Date());
        String[] partes = hora.split(":");
        s.setData(new Date());
        s.setHora(partes[0] + "h" + partes[1] + "min");

        p.setStatuses(new ArrayList());
        
        DAOPedido daoPed = new DAOPedido();
        List<EntidadeDominio> eds = null;
        Pedido pBuffer = new Pedido();
        pBuffer.setId(p.getId());
        
        System.out.println(pBuffer.getId() + " " + p.getId() + "id pby");
                
        try {
            eds = (List<EntidadeDominio>) daoPed.consultar(pBuffer);
            pBuffer = (Pedido) eds.get(0);
                        
        } catch (SQLException ex) {
            Logger.getLogger(TrocarItemPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        p.setCliente(pBuffer.getCliente());
        
        IStrategy cdp = new ComplementarDadosPedido();
        cdp.processar(p);
        
        System.out.println(pBuffer.getCarrinho().getItens().size() + "pbu");
        
        for(ItemCarrinho ic : pBuffer.getCarrinho().getItens()){
            
            for(ItemCarrinho ict : p.getCarrinho().getItens()){
                
                if(ic.getProduto().getId() == ict.getProduto().getId()){
                    ict.setStatus("Em Troca");
                    ic.setStatus("Em Troca");
                    ict.setProduto(ic.getProduto());
                    
                }
                
            }
            
        }
        
        p.getStatuses().add(s);
                
        IStrategy cs = new CalculadoraValorSubtotal();
        cs.processar(p);
            
        IStrategy cvt = new CalculadoraValorTotal();
        cvt.processar(p);
        
        int id_pedido = p.getId();        
        
        try {
            daoPed.salvar(p);
        } catch (SQLException ex) {
            Logger.getLogger(TrocarItemPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        p.getCarrinho().setItens(pBuffer.getCarrinho().getItens());
        p.setId(id_pedido); 
        p.setAtivo(true);
        p.setStatuses(null);
        p.setTroca(true);
//        p.getCarrinho().setItens(pBuffer.getCarrinho().getItens());
        
        return null;
    }

}
