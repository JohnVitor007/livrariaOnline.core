package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;

public class ComplementarDadosAlteracaoCadastroLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
       
        Livro l = (Livro) entidadeDominio;
        Livro lb = new Livro();
        
        if(l.getCapa() != null)
            return null;
        
        DAOLivro dl = new DAOLivro();
        
        lb.setId(l.getId());

        try {
            
            
            
            List<EntidadeDominio> eds = (List<EntidadeDominio>) dl.consultar(lb);
        
            lb = (Livro) ((List<EntidadeDominio>) eds).get(0);
            
            l.setAtivo(lb.isAtivo());
            l.setCusto(lb.getCusto());
            l.setStatus(lb.getStatus());
            l.setItemEstoque(lb.getItemEstoque());
            l.getItemEstoque().getGrupoPrecificacao();
            l.setPreco(lb.getPreco());
        
        } catch (SQLException ex) {
            Logger.getLogger(ComplementarDadosAlteracaoCadastroLivro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return null;
    }
    
    
    
}
