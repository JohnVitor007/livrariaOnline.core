
package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.core.IDAO;
import livraria.dominio.Administrador;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Usuario;

public class DAOUsuario extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Usuario usuario = (Usuario) entidadeDominio;
        
        Collection<EntidadeDominio> eds = new ArrayList<>();
        
        DAOCliente dc = new DAOCliente();
        DAOAdministrador da = new DAOAdministrador();        
                
        if(usuario.getTipoUsuario().equals("Cliente")){
            Cliente c = new Cliente();
            c.setUsuario(usuario);
            eds = dc.consultar(c);
        }else if(usuario.getTipoUsuario().equals("Administrador")){
            Administrador a = new Administrador();
            a.setUsuario(usuario);
            eds = da.consultar(a);
        }
                
        return eds;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Usuario u = (Usuario) entidadeDominio;
                
        StringBuilder query = new StringBuilder();
        
        query.append("update clientes set cli_senha = ? where cli_id_cliente = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());

        pst.setObject(1, u.getSenha());
        pst.setObject(2, u.getPessoa().getId());
        
        pst.execute();
        
        connection.close();
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    
        Usuario u = (Usuario) entidadeDominio;
                
        StringBuilder query = new StringBuilder();
        
        query.append("update clientes set cli_ativo = ?, cli_status= ? where cli_id_cliente = ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(query.toString());

        if(u.isAtivo()){        
            pst.setObject(1, false);
            pst.setObject(2, "Inativo");
        }else{
            pst.setObject(1, true);
            pst.setObject(2, "Ativo");
        }
        
        pst.setObject(3, u.getPessoa().getId());
        
        pst.execute();
        
        connection.close();
        
        
    }
    
}
