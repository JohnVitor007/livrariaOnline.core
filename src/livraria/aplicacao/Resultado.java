//Classe Resultado
//@author John Vitor da Silva Quispe
//@date 19/03/2018

package livraria.aplicacao;

import java.util.Collection;
import livraria.dominio.EntidadeDominio;

public class Resultado {

    private Collection<EntidadeDominio> entidades;
    private String resultados;
    
    /**
     * @return the entidades
     */
    public Collection<EntidadeDominio> getEntidades() {
        return entidades;
    }

    /**
     * @param entidades the entidades to set
     */
    public void setEntidades(Collection<EntidadeDominio> entidades) {
        this.entidades = entidades;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
    
}
