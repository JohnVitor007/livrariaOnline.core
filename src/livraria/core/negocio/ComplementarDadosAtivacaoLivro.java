package livraria.core.negocio;

import java.util.Date;
import livraria.core.IStrategy;
import livraria.dominio.Ativacao;
import livraria.dominio.EntidadeDominio;

public class ComplementarDadosAtivacaoLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Ativacao ativacao = (Ativacao) entidadeDominio;
        
        ativacao.setData(new Date());
        ativacao.getLivro().setAtivo(true);
        ativacao.getLivro().setStatus("Ativo");
                
        return null;
        
    }
    
}
