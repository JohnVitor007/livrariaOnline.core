package livraria.core.negocio;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCupomPromocional;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class ValidarCupomPromocional implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Pedido p = (Pedido) entidadeDominio;

        if (p.getPagamento().getCupomPromocional() != null) {
            if (p.getPagamento().getCupomPromocional().getValidade().getTime() < (new Date()).getTime()) {
                p.setAtivo(false);
                IStrategy tsp = new TrocarStatusPedido();
                tsp.processar(p);
                System.out.println(" aqui");
                return null;
            }

            if (p.getPagamento().getCupomPromocional().isUsado()) {
                return "Cupom Promocional " + p.getPagamento().getCupomPromocional().getIdentificador() + " já foi usado";
            }

            p.getPagamento().getCupomPromocional().setUsado(true);
            DAOCupomPromocional dcp = new DAOCupomPromocional();

            try {
                dcp.alterar(p.getPagamento().getCupomPromocional());
            } catch (SQLException ex) {
                Logger.getLogger(ValidarCupomPromocional.class.getName()).log(Level.SEVERE, null, ex);
            }

            
        }

            p.setAtivo(true);
            IStrategy tsp = new TrocarStatusPedido();
            tsp.processar(p);

        
        return null;

    }

}
