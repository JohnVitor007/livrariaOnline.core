package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;

public class ComplementarDadosCadastroCliente implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Cliente c = (Cliente) entidadeDominio;
        c.getUsuario().setStatus("Ativo");
        c.getUsuario().setAtivo(true);
        c.getUsuario().setTipoUsuario("Cliente");
        
        return null;
        
    }
    
}
