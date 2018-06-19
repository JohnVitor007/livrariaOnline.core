package livraria.core.negocio;

import java.util.Date;
import livraria.core.IStrategy;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;

public class ComplementarDadosCartao implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Cartao c = (Cartao) entidadeDominio;
        
        c.setDataCadastro(new Date());
        c.setAtivo(true);
    
        return null;
    }
    
}
