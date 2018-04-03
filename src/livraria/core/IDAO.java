//Interface IDAO
//@author John Vitor da Silva Quispe
//@date 19/03/2018

package livraria.core;

import java.sql.SQLException;
import java.util.Collection;
import livraria.dominio.EntidadeDominio;

public interface IDAO {

    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException;
    public void salvar(EntidadeDominio entidadeDominio) throws SQLException;
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException;
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException;
    
}
