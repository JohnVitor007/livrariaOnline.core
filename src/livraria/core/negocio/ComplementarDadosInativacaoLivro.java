package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOLivro;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;

public class ComplementarDadosInativacaoLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Inativacao inativacao = (Inativacao) entidadeDominio;
                
        if(inativacao.getCategoriaInativacao().equals("Fora do Mercado")){
            inativacao.getLivro().setAtivo(true);
            inativacao.getLivro().setStatus("Fora do Mercado");
        }else{ 
            inativacao.getLivro().setAtivo(false);
            inativacao.getLivro().setStatus("Inativo");
        }
        return null;
        
    }
    
}
