package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;

public class ValidadorQtdItensCarrinho implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        ItemCarrinho ic = (ItemCarrinho) entidadeDominio; 
        
        Livro l = (Livro) ic.getProduto();
             
        DAOLivro dl = new DAOLivro();
        
        List<EntidadeDominio> eds = null;
        
        try {
            eds = (List<EntidadeDominio>) dl.consultar(l);
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorQtdItensCarrinho.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        l = (Livro) eds.get(0);
        
        if(l.getItemEstoque().getQuantidade() < ic.getQuantidade())
            return "Só é possível adicionar até " + l.getItemEstoque().getQuantidade() + " unidades deste produto<br>";
        
        return null;
        
    }
    
}
