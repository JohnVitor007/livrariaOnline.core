package livraria.core.negocio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class CriptografarSenha implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
    
        
        try {
            Usuario u = (Usuario) entidadeDominio;
            u.setSenha(sha1(u.getSenha()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CriptografarSenha.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return null;
    }
    
    private String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    } 


    
}
