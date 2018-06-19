package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Endereco;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosEnderecoResidencial implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        String m = "";
        
        Endereco er = (Endereco) entidadeDominio;
        
        if(er.getTipoEndereco().getNome().equals(""))
            m += "Selecione o tipo do endereço residencial<br>";
        
        if(er.getTipoLogradouro().getNome().equals(""))
            m += "Selecione o tipo de logradouro do endereço residencial<br>";
        
        if(er.getLogradouro().equals(""))
            m += "Insira o logradouro do endereço residencial<br>";
        
        if(er.getNumero() == 0)
            m += "Insira o número do endereço residencial<br>";
        
        if(er.getCep().equals(""))
            m += "Insira o CEP do endereço residencial<br>";
        
        if(er.getBairro().equals(""))
            m += "Insira o bairro do endereço residencial<br>";
        
        if(er.getCidade().getNome().equals(""))
            m += "Insira o nome da cidade do endereço residencial<br>";
        
        if(er.getEstado().getNome().equals(""))
            m += "Insira o nome do estado do endereço residencial<br>";
                    
        if(er.getPais().getNome().equals(""))
            m += "Insira o nome do país do endereço residencial<br>";
                
        if(m.length() > 0)
            return m;
        else
            return null;
    
    }
    
    
    
}
