package livraria.core.negocio;

import livraria.core.IStrategy;
import livraria.dominio.Administrador;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;

public class ValidadorDadosObrigatoriosAdministrador implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        Administrador a = (Administrador) entidadeDominio;
        
        String m = "";
        String mb = "";
        
        if(a.getNome().equals(""))
            m += "Insira o nome<br>";
        
        if(a.getCpf().equals(""))
            m += "Insira o cpf<br>";
        
        if(a.getTelefone().getTipo().equals(""))
            m += "Insira o tipo de telefone<br>";
        
        if(a.getTelefone().getDdd() == 0)
            m += "Insira o DDD do telefone<br>";
        
        if(a.getTelefone().getNumero() == 0)
            m += "Insira o número do telefone<br>";
        
        if(a.getUsuario().getEmail().equals(""))
            m += "Insira o email<br>";
        
        if(a.getUsuario().getSenha().equals(""))
            m += "Insira a senha<br>";
        
        if(a.getUsuario().getSenhaRepetida().equals(""))
            m+= "Insira a senha repetida<br>";
        
        else{
            ValidadorSenhaUsuario vsu = new ValidadorSenhaUsuario();
            if((mb = vsu.processar((EntidadeDominio) a.getUsuario())) != null)
                m += mb;
        }
        
        if(m.length() > 0)
            return m;
        
        return null;
        
        
    }
    
    
    
}
