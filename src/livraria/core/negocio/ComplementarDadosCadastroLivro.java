
package livraria.core.negocio;

import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.dao.DAOCategoriaInativacao;
import livraria.dominio.CategoriaInativacao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;

public class ComplementarDadosCadastroLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        DAOCategoriaInativacao daoInativacao = new DAOCategoriaInativacao();
        
        CategoriaInativacao categoriaInativacao = new CategoriaInativacao();
        
        Livro l = (Livro) entidadeDominio;
        
        Collection<EntidadeDominio> eds = null;
        try {
            eds = daoInativacao.consultar(categoriaInativacao);
        } catch (SQLException ex) {
            Logger.getLogger(ComplementarDadosCadastroLivro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Collection<Inativacao> inativacoes = new ArrayList<>();
        
        Inativacao inativacao;
        
        for(EntidadeDominio ed : eds){
             CategoriaInativacao ci = (CategoriaInativacao) ed;
             
             inativacao = new Inativacao();
             
             if(ci.getNome().equals("Produto sem estoque")){
                 inativacao.setCategoriaInativacao(ci);
                 inativacao.setJustificativa("Não produtos em estoque");
                 inativacoes.add(inativacao);
                 l.setAtivo(false);
                 l.setStatus(ci.getNome());
             }
                        
        } 
        
        l.setInativacoes(inativacoes);
        
        l.setDataCadastro(new Date());
     
      return null;
      
    }
    
}
