
package livraria.core.negocio;

import java.util.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOCategoriaInativacao;
import livraria.dominio.CategoriaInativacao;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;

public class ComplementarDadosCadastroLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Livro l = (Livro) entidadeDominio;
       
        l.setStatus("Inativo");
        l.setDataCadastro(new Date());
             
      return null;
      
    }
    
}
