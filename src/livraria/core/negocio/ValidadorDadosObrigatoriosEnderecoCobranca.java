package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EnderecoCobranca;
import livraria.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosEnderecoCobranca implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        String m = "";
        
        EnderecoCobranca ec = (EnderecoCobranca) entidadeDominio;
        
        if(ec.getTipoEndereco().getNome().equals(""))
            m += "Selecione o tipo do endereço de cobrança<br>";
        
        if(ec.getTipoLogradouro().getNome().equals(""))
            m += "Selecione o tipo de logradouro do endereço de cobrança<br>";
        
        if(ec.getLogradouro().equals(""))
            m += "Insira o logradouro do endereço de cobrança<br>";
        
        if(ec.getNumero() == 0)
            m += "Insira o número do endereço de cobrança<br>";
        
        if(ec.getCep().equals(""))
            m += "Insira o CEP do endereço de cobrança<br>";
        
        if(ec.getBairro().equals(""))
            m += "Insira o bairro do endereço de cobrança<br>";
        
        if(ec.getCidade().getNome().equals(""))
            m += "Insira o nome da cidade do endereço de cobrança<br>";
        
        if(ec.getEstado().getNome().equals(""))
            m += "Insira o nome do estado do endereço de cobrança<br>";
                    
        if(ec.getPais().getNome().equals(""))
            m += "Insira o nome do país do endereço de cobrança<br>";
                
        if(m.length() > 0)
            return m;
        else
            return null;
    
        
    }
    
}
