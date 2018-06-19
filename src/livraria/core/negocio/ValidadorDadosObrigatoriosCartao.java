package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cartao;
import livraria.dominio.EntidadeDominio;


public class ValidadorDadosObrigatoriosCartao implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Cartao c = (Cartao) entidadeDominio;
        
        String m = "";
        
        if(c.getNomeImpresso().equals(""))
            m += "Insira o nome do titular do cartão (exatamento como está impresso no cartão)<br>";
        
        if(c.getCodigoSeguranca() == 0)
            m += "Insira o código de segurança do cartão<br>";
        
        if(c.getNumero().equals(""))
            m += "Insira o número do cartão<br>";
        
        if(m.length() > 0)
            return m;
            
        return null;
    
    }

    


    
}
