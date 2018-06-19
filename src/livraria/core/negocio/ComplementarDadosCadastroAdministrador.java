package livraria.core.negocio;

import java.util.Date;
import livraria.core.IStrategy;
import livraria.dominio.Administrador;
import livraria.dominio.EntidadeDominio;

public class ComplementarDadosCadastroAdministrador implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Administrador a = (Administrador) entidadeDominio;
        
        a.setDataCadastro(new Date());
        a.setAtivo(true);
        a.getUsuario().setStatus("ts");
        
        return null;
        
    }
    
}
