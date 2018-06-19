package livraria.core.util.calculo.correios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.dao.DAOLivro;
import livraria.dominio.Carrinho;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;

public class CalcularFrete {

    public static List<DadosFrete> calcular (Carrinho c, String cepDestino){
        
        double pesoT = 0.0;
        double alturaCm = 0.0;
        double larguraCm = 0.0;
        double comprimentoCm = 0.0;
            
        Livro l;
        
        for(ItemCarrinho ic : c.getItens()){
            
            l = (Livro) ic.getProduto();
            
            DAOLivro dl = new DAOLivro();
            
            List<EntidadeDominio> eds = null;
            try {
                eds = (List<EntidadeDominio>) dl.consultar(l);
            } catch (SQLException ex) {
                Logger.getLogger(CalcularFrete.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            l = (Livro) eds.get(0);           
                        
            if(l.getEdicao().getDimensoes().getPeso().getUnidadeMedida().getNome().equals("Gramas"))
                pesoT+= l.getEdicao().getDimensoes().getPeso().getQuantidade() * 0.1;
            else
                pesoT+= l.getEdicao().getDimensoes().getPeso().getQuantidade();
            
            if(alturaCm < l.getEdicao().getDimensoes().getAltura())
                alturaCm = l.getEdicao().getDimensoes().getAltura();
                        
            if(larguraCm < l.getEdicao().getDimensoes().getLargura())
               larguraCm = l.getEdicao().getDimensoes().getLargura();
            
            comprimentoCm += l.getEdicao().getDimensoes().getProfundidade();
            
        }
        
        
        Pacote p = new Pacote(String.valueOf(pesoT) , comprimentoCm, alturaCm, larguraCm, larguraCm, 1);

        DadosFrete dfPAC = null;
        DadosFrete dfSedex = null;
                
        try {
            dfPAC = CorreiosFrete.consultarFretePAC(cepDestino, "08570050", p);
            dfSedex = CorreiosFrete.consultarFreteSEDEX(cepDestino, "08570050", p);
        } catch (Exception ex) {
            Logger.getLogger(CalcularFrete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        dfPAC.setNomeTipo("Correios PAC");
        dfSedex.setNomeTipo("Correios Sedex");        
        
        List<DadosFrete> dfs = new ArrayList<>();
        
        dfs.add(dfPAC);
        dfs.add(dfSedex);
        
        return dfs;
        
        
    }
    
}
