//Inteface IStrategies
//@author John Vitor da Silva Quispe
//@date: 19/03/2018
package livraria.core;

import livraria.dominio.EntidadeDominio;

public interface IStrategy {
    
    public String processar(EntidadeDominio entidadeDominio);
    
}
