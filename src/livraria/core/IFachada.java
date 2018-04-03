//Interface IFachada

package livraria.core;

import java.util.Collection;
import livraria.aplicacao.Resultado;
import livraria.dominio.EntidadeDominio;


public interface IFachada {
    
    public Resultado consultar(EntidadeDominio entidadeDominio);
    public Resultado salvar(EntidadeDominio entidadeDominio);
    public Resultado alterar(EntidadeDominio entidadeDominio);
    public Resultado excluir(EntidadeDominio entidadeDominio);
    
}
