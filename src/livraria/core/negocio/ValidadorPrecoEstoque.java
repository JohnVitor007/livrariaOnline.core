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

public class ValidadorPrecoEstoque implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Entrada e = (Entrada) entidadeDominio;
        
        DAOLivro dl = new DAOLivro();
        
        Livro l = (Livro) e.getItemEstoque().getProduto();
        
        Livro lb = new Livro();
        lb.setId(l.getId());
        
        List<EntidadeDominio> eds = null;
        
        try {
            eds = (List<EntidadeDominio>) dl.consultar(lb);
        
            if(eds.isEmpty())
                return null;
            
            lb = (Livro) eds.get(0);
            
            System.out.println("Entrei " + lb.getPreco() + " " + e.getPreco() + " " + e.getItemEstoque().getProduto().getPreco());
            
            if(lb.getPreco() > e.getPreco()){
                
                e.setPreco(lb.getPreco());
                e.getItemEstoque().getProduto().setPreco(lb.getPreco());                
                
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorPrecoEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }

    
    
}
