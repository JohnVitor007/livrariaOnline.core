//Classe Resultado
//@author John Vitor da Silva Quispe
//@date 19/03/2018

package livraria.aplicacao;

import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.EntidadeDominio;

public class Resultado {

    private Collection<EntidadeDominio> entidades;
    private Collection<String> resultados;

    public Resultado(){
        
        resultados = new ArrayList<>();
        
    }
    
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

    /**
     * @return the resultados
     */
    public Collection<String> getResultados() {
        return resultados;
    }

    /**
     * @param resultados the resultados to set
     */
    public void setResultados(Collection<String> resultados) {
        this.resultados = resultados;
    }

    
    
}
