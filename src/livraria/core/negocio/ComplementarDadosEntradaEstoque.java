
package livraria.core.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Entrada;
import livraria.dominio.Livro;

public class ComplementarDadosEntradaEstoque implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Entrada e = (Entrada) entidadeDominio;
        
        Livro l = (Livro) e.getItemEstoque().getProduto();
        
        DAOLivro dl = new DAOLivro();
        
        List<EntidadeDominio> eds = new ArrayList();
        try {
            eds = (List<EntidadeDominio>) dl.consultar(l);
        } catch (SQLException ex) {
            Logger.getLogger(ComplementarDadosEntradaEstoque.class.getName()).log(Level.SEVERE, null, ex);
        }

        l = (Livro) eds.get(0);
                        
        l.getItemEstoque().setQuantidade(l.getItemEstoque().getQuantidade() + e.getQuantidade());
        
        System.out.println(e.getQuantidade());
        
        e.getItemEstoque().setProduto(l);
        
        return null;
        
    }
    
    
    
}
