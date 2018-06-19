package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Cliente;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosClientes implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Cliente c = (Cliente) entidadeDominio;
        
        String m = "";
        String mb = "";
        
        if(c.getNome().equals(""))
            m += "Insira o nome<br>";
        
        if(c.getCpf().equals(""))
            m += "Insira o cpf<br>";
        
        if(c.getTelefone().getTipo().equals(""))
            m += "Insira o tipo de telefone<br>";
        
        if(c.getTelefone().getDdd() == 0)
            m += "Insira o DDD do telefone<br>";
        
        if(c.getTelefone().getNumero() == 0)
            m += "Insira o número do telefone<br>";
        
        if(c.getUsuario().getEmail().equals(""))
            m += "Insira o email<br>";
        
        if(c.getUsuario().getSenha().equals(""))
            m += "Insira a senha<br>";
        else{
            ValidadorSenhaUsuario vsu = new ValidadorSenhaUsuario();
            if((mb = vsu.processar((EntidadeDominio) c.getUsuario())) != null)
                m += mb;
        }
        
        if(c.getGenero().equals(""))
            m += "Selecione o gênero<br>";
        
        IStrategy vdode = new ValidadorDadosObrigatoriosEnderecoEntrega();
        
        for(EnderecoEntrega ee : c.getEnderecosEntrega()){
            if((mb = vdode.processar((EntidadeDominio) ee)) != null)
                m += mb;   
        }
        
        IStrategy vdodc = new ValidadorDadosObrigatoriosEnderecoCobranca();
        if((mb = vdodc.processar((EntidadeDominio) c.getEnderecoCobranca())) != null)
                m += mb;
                
        IStrategy vdoer = new ValidadorDadosObrigatoriosEnderecoResidencial();
        if((mb = vdoer.processar((EntidadeDominio) c.getEnderecoResidencial())) != null)
            m += mb;
            
        if(m.length() > 0)
            return m;
        else
            return null;
        
    }
    
    
    
}
