package livraria.core.negocio;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOConfiguracao;
import livraria.core.dao.DAOCupomPromocional;
import livraria.core.dao.DAOPedido;
import livraria.dominio.Configuracao;
import livraria.dominio.Cupom;
import livraria.dominio.CupomPromocional;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class GeradorCupomPromocional implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        double[] valores = {5.00, 7.00, 10.00, 15.00};
        
        Random random = new Random();
                
        Pedido pedido = (Pedido) entidadeDominio;

        if (pedido.isAtivo()) {
            
            DAOConfiguracao dconf = new DAOConfiguracao();
            Configuracao conf = new Configuracao();
           
            Pedido p = new Pedido();
            p.setCliente(pedido.getCliente());
            DAOPedido daoPed = new DAOPedido();
            
            int qtd = 0;
            int qtdPedidos = 0;
            
            try {
                List<EntidadeDominio> eds = (List<EntidadeDominio>) dconf.consultar(conf);
                conf = (Configuracao) eds.get(0);
                
                List<EntidadeDominio> edsPed = (List<EntidadeDominio>) daoPed.consultar(p);
                qtdPedidos = edsPed.size();
                
                qtd = conf.getQtdCupons();
                
            } catch (SQLException ex) {
                Logger.getLogger(GeradorCupomPromocional.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(qtd + " qtd " + qtdPedidos + " " + (qtdPedidos%qtd));
            
           if(qtdPedidos % qtd != 0)
               return null;
            
           /*
           DAOCupomPromocional dcp = new DAOCupomPromocional();
           List<EntidadeDominio> cupons = null;
           
           CupomPromocional cpb = new CupomPromocional();
           cpb.setCliente(pedido.getCliente());
           
           boolean temNaoUsado = false;
           
            try {
                cupons = (List<EntidadeDominio>) dcp.consultar(cpb);
            
                for(EntidadeDominio ed : cupons){
                    
                    Cupom c = (Cupom) ed;
                    if(!c.isUsado()){
                        temNaoUsado = true;
                    }
                    
                }
                
                
            } catch (SQLException ex) {
                Logger.getLogger(GeradorCupomPromocional.class.getName()).log(Level.SEVERE, null, ex);
            }
           
           */
           
           
            CupomPromocional cp = new CupomPromocional();

            java.util.Date now = new Date();
            Calendar myCal = Calendar.getInstance();
            myCal.setTime(now);
            myCal.add(Calendar.MONTH, +1);
            now = myCal.getTime();

            String uniqueID = UUID.randomUUID().toString();
            uniqueID = uniqueID.toUpperCase().replaceAll("-", "").substring(24, 32);
            
            cp.setIdentificador("PROMO-" + uniqueID);
            cp.setUsado(false);
            cp.setValidade(now);
            cp.setValor(valores[random.nextInt(4)]);
            cp.setCliente(pedido.getCliente());

            DAOCupomPromocional dcp = new DAOCupomPromocional();
            
            try {
                dcp.salvar(cp);
            } catch (SQLException ex) {
                Logger.getLogger(GeradorCupomPromocional.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return null;

    }

}
