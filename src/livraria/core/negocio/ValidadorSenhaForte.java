package livraria.core.negocio;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class ValidadorSenhaForte implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        String m = "";
        String mb = "";
        
        Usuario u = (Usuario) entidadeDominio;
        
        if(u.getSenha().length() < 8)
            m += "A senha possir 8 caracteres<br>";
      
        Matcher matcher;
        
        Pattern regex = Pattern.compile("[!%*/$&+,:;=?@#|]");        
        matcher = regex.matcher(u.getSenha());
        if (!matcher.find())
            m += "A senha deve possuir caracteres especiais<br>";
        
        Pattern regexMaisculo = Pattern.compile("[A-Z]");
        matcher = regexMaisculo.matcher(u.getSenha());
        if (!matcher.find())
            m += "A senha deve possuir caracteres maiúsculos<br>";
        
        Pattern regexMinuscula = Pattern.compile("[a-z]");
        matcher = regexMinuscula.matcher(u.getSenha());
        if (!matcher.find())
            m += "A senha deve possuir caracteres minúsculos<br>";
        
        System.out.println(u.getSenha());
        
        IStrategy criptografarSenha = new CriptografarSenha();
        criptografarSenha.processar(u);
        
        if(m.length() > 0){            
            return m;
        }
        else 
            return null;
                    
        
    }
    
    


    
}
