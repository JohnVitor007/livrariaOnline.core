package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import livraria.dominio.Auditoria;
import livraria.dominio.Cliente;
import livraria.dominio.EntidadeDominio;

public class DAOAuditoria extends AbstractJDBCDAO {

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Auditoria a = (Auditoria) entidadeDominio;
        List<EntidadeDominio> eds = new ArrayList();
        
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("select log_evento, log_query, log_data, log_hora, log_id_cliente from logs");
        
        if(a.getCliente() != null)
            sql.append(" where log_id_cliente = ?");
        
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        if(a.getCliente() != null)
            pst.setObject(1, a.getCliente().getId());
            
       ResultSet rs = pst.executeQuery();
        
       while(rs.next()){
           
           Auditoria au = new Auditoria();
           au.setEvento(rs.getString("log_evento"));
           au.setQuery(rs.getString("log_query"));
           au.setData(rs.getDate("log_data"));
           au.setHora(rs.getString("log_hora"));
           //au.setCliente(new Cliente());
           //au.getCliente().setId(rs.getInt("log_id_cliente"));
           
           eds.add(au);
           
       }
    
       return eds;
       
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Auditoria au = new Auditoria();
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into logs(log_evento, log_query, log_data, log_hora)values(?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());

        pst.setObject(1, au.getEvento());
        pst.setObject(2, au.getQuery());
        pst.setObject(3, new java.sql.Date((new Date()).getTime()));
        pst.setObject(4, System.currentTimeMillis());
        
        pst.execute();
        
        return au;       
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
