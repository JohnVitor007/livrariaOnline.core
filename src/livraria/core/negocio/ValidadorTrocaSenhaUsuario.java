package livraria.core.negocio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOUsuario;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class ValidadorTrocaSenhaUsuario implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        String msg = "";
        
        Usuario u = (Usuario) entidadeDominio;
        
        Usuario uBuffer = new Usuario();
        uBuffer.setEmail(u.getEmail());
        uBuffer.setTipoUsuario(u.getTipoUsuario());
        try {
            uBuffer.setSenha(this.sha1(u.getSenhaAnterior()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ValidadorTrocaSenhaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DAOUsuario du = new DAOUsuario();
        
        Collection eds = new ArrayList<>();
        
        try {
            eds = du.consultar(uBuffer);
        } catch (SQLException ex) {
            Logger.getLogger(ValidadorExistenciaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(eds.isEmpty())
            return "A senha atual está incorreta<br>";
            
        try {
            if((sha1(u.getSenha())).equals(sha1(u.getSenhaAnterior())))
                return "A nova senha não pode ser igual a senha atual<br>";
                
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ValidadorTrocaSenhaUsuario.class.getName()).log(Level.SEVERE, null, ex);
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
