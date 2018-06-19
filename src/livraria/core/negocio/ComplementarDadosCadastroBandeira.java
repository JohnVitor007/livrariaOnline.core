package livraria.core.negocio;

import java.util.Date;
import livraria.core.IStrategy;
import livraria.dominio.Bandeira;
import livraria.dominio.EntidadeDominio;

public class ComplementarDadosCadastroBandeira implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Bandeira bandeira = (Bandeira) entidadeDominio;
        
        bandeira.setDataCadastro(new Date());
    
        return null;
    }
    
}
