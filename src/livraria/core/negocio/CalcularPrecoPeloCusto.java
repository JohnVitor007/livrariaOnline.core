package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Entrada;
import livraria.dominio.Livro;

public class CalcularPrecoPeloCusto implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Entrada entrada = (Entrada) entidadeDominio;
        
        Livro l = (Livro) entrada.getItemEstoque().getProduto();
        
        DAOLivro dl = new DAOLivro();
        
        try {
            Livro lBuffer = (Livro) ((List<EntidadeDominio>) dl.consultar(l)).get(0);
            
        } catch (SQLException ex) {
            Logger.getLogger(CalcularPrecoPeloCusto.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        l.setPreco(((((l.getItemEstoque().getGrupoPrecificacao().getPercentual()) / 100) * entrada.getCusto())) + entrada.getCusto());
       
        l.setCapa(null);
        
        entrada.getItemEstoque().setProduto(l);
        entrada.setPreco(l.getPreco());
        
        return null;
    }


    
}
