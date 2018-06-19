package livraria.core.negocio;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOPedido;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;
import livraria.dominio.Status;

public class TrocarStatusPedido implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Pedido p = (Pedido) entidadeDominio;
        
        if(p.isTroca() || p.isCancelado())
            return null;

        Status s = new Status();
        
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        String hora = sdf.format(new Date());
        String[] partes = hora.split(":");
        
        s.setData(new Date());
        s.setHora(partes[0] + "h" + partes[1] + "min");
                        
        System.out.println(p.getId() + " fer");
                
        
        if(p.getId() != 0){
            
            DAOPedido dped = new DAOPedido();
            
            Pedido pBuffer = new Pedido();
            pBuffer.setId(p.getId());
            
            
            List<EntidadeDominio> eds = null;
            try {
                eds = (List<EntidadeDominio>) dped.consultar(pBuffer);
            } catch (SQLException ex) {
                Logger.getLogger(TrocarStatusPedido.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            pBuffer = (Pedido) eds.get(0);
           
            p.setStatuses(pBuffer.getStatuses());
            
        }

        for(Status sb : p.getStatuses()){
            
            if(sb.getNome().equals("Em Troca") && p.getStatuses().size() == 1){
                if(p.isAtivo()) 
                    s.setNome("Troca Autorizada");
                else
                    s.setNome("Troca Não Autorizada");                
                
                p.getStatuses().add(s);
                
                return null;
                
            }else if(sb.getNome().equals("Troca Autorizada") && p.getStatuses().size() == 2){
                
                s.setNome("Trocado");
                p.getStatuses().add(s);
                
                return null;
            }
            
        }
        
        String[] certo = {"Em processamento","Pagamento Aprovado", "Em transporte", "Entregue", "Em Troca", "Troca Autorizada", "Trocado"};
        String[] errado = {"", "Pagamento Reprovado", "", "", "", "Troca Não Autorizada","Trocado"};
        
        
        
        if(p.isAtivo())
            s.setNome(certo[p.getStatuses().size()]);
        else
            s.setNome(errado[p.getStatuses().size()]);
                
        
        p.getStatuses().add(s);
               
        return null;
        
    }
        
}
