package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosEnderecoEntrega implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        String m = "";
        
        EnderecoEntrega ee = (EnderecoEntrega) entidadeDominio;
        
        if(ee.getApelido().equals(""))
            m += "Selecione o apelido do endereço de entrega<br>";
        
        if(ee.getTipoEndereco().getNome().equals(""))
            m += "Selecione o tipo do endereço de entrega<br>";
        
        if(ee.getTipoLogradouro().getNome().equals(""))
            m += "Selecione o tipo de logradouro do endereço de entrega<br>";
        
        if(ee.getLogradouro().equals(""))
            m += "Insira o logradouro do endereço de entrega<br>";
        
        if(ee.getNumero() == 0)
            m += "Insira o número do endereço de entrega<br>";
        
        if(ee.getCep().equals(""))
            m += "Insira o CEP do endereço de entrega<br>";
        
        if(ee.getBairro().equals(""))
            m += "Insira o bairro do endereço de entrega<br>";
        
        if(ee.getCidade().getNome().equals(""))
            m += "Insira o nome da cidade do endereço de entrega<br>";
        
        if(ee.getEstado().getNome().equals(""))
            m += "Insira o nome do estado do endereço de entrega<br>";
                    
        if(ee.getPais().getNome().equals(""))
            m += "Insira o nome do país do endereço de entrega<br>";
                
        if(m.length() > 0)
            return m;
        else
            return null;
        
        
    }
    
}
