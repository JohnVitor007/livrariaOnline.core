package livraria.core.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCupomPromocional;
import livraria.core.dao.DAOCupomTroca;
import livraria.dominio.Cupom;
import livraria.dominio.CupomPromocional;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pagamento;

public class ValidadorExistenciaCupom implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        boolean cupP = false;
        boolean cupT = false;
        
        DAOCupomPromocional dcp = new DAOCupomPromocional();
        DAOCupomTroca dct = new DAOCupomTroca();
        
        Cupom c = (Cupom) entidadeDominio;
        
        CupomPromocional bCp = new CupomPromocional();
        bCp.setIdentificador(c.getIdentificador());
        
        CupomTroca bCt = new CupomTroca();
        bCt.setIdentificador(c.getIdentificador());
        
        if(c.getPedido().getPagamento() == null){
            c.getPedido().setPagamento(new Pagamento());
            c.getPedido().getPagamento().setCuponsTroca(new ArrayList());
        }
        
        List<EntidadeDominio> eds = new ArrayList();
        
        try {
            eds = (List<EntidadeDominio>) dcp.consultar(bCp);
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorExistenciaCupom.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eds.size() > 0){
            cupP = true;
            CupomPromocional cp = (CupomPromocional) eds.get(0);
            if(c.getPedido().getPagamento().getCupomPromocional() != null)
                return "Apenas um cumpom promocional é permitido<br>";
            else
                c.getPedido().getPagamento().setCupomPromocional(cp);
        }
        
        try {
            eds = (List<EntidadeDominio>) dct.consultar(bCt);
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorExistenciaCupom.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eds.size() > 0){
            cupT = true;
            CupomTroca cp = (CupomTroca) eds.get(0);
            c.getPedido().getPagamento().getCuponsTroca().add(cp);
            
        }
        
        if(!cupT && !cupP)
            return "Cupom inválido";
        else
            return "Cupom Adicionado";
        
        
    }
    
}
