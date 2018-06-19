package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.Cliente;
import livraria.dominio.CupomPromocional;
import livraria.dominio.EntidadeDominio;

public class DAOCupomPromocional extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomPromocional cp = (CupomPromocional) entidadeDominio;
        List<EntidadeDominio> cps = new ArrayList();
        
        StringBuilder sql = new StringBuilder();
        
        if(cp.getId() != 0){
            sql.append("select cp_id_cupom_promocional, cp_identificacao, cp_validade, cp_usado, cp_valor from cupons_promocionais cp join cupons_promocionais_clientes cpc on cpc.cpc_id_cupom_promocional = cp.cp_id_cupom_promocional ");
            sql.append("where ");
            sql.append("cpc.cpc_id_cupom_promocional = ?");            
        }else if(cp.getCliente() != null){
            sql.append("select cp_id_cupom_promocional, cp_identificacao, cp_validade, cp_usado, cp_valor from cupons_promocionais cp join cupons_promocionais_clientes cpc on cpc.cpc_id_cupom_promocional = cp.cp_id_cupom_promocional ");
            sql.append("where ");
            sql.append("cpc.cpc_id_cliente = ?");
        }else if(cp.getIdentificador() != null){
            sql.append("select cp_id_cupom_promocional, cp_identificacao, cp_validade, cp_usado, cp_valor from cupons_promocionais cp ");
            sql.append("where ");
            sql.append("cp.cp_identificacao = ?");
        }else{
            sql.append("select cp_id_cupom_promocional, cp_identificacao, cp_validade, cp_usado, cp_valor from cupons_promocionais cp ");
        }
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
                
        if(cp.getId() != 0)
            pst.setInt(1, cp.getId());
        else if(cp.getCliente() != null)
            pst.setInt(1, cp.getCliente().getId());
        else if(cp.getIdentificador() != null)
            pst.setObject(1, cp.getIdentificador());
        
        
        ResultSet rs = pst.executeQuery();
        
        CupomPromocional bufferCp;
        
        while(rs.next()){
            
            bufferCp = new CupomPromocional();
            bufferCp.setCliente(new Cliente());
            bufferCp.getCliente().setId(cp.getId());
            bufferCp.setId(rs.getInt("cp_id_cupom_promocional"));
            bufferCp.setIdentificador(rs.getString("cp_identificacao"));
            bufferCp.setUsado(rs.getBoolean("cp_usado"));
            bufferCp.setValidade(rs.getDate("cp_validade"));
            bufferCp.setValor(rs.getDouble("cp_valor"));
            bufferCp.setClientes(new ArrayList());
            
            cps.add(bufferCp);
        }
        
        connection.close();
        
        return cps;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomPromocional cp = (CupomPromocional) entidadeDominio;
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into cupons_promocionais(cp_identificacao, cp_validade, cp_usado, cp_valor)values(?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        
        pst.setObject(1, cp.getIdentificador());
        pst.setObject(2, new java.sql.Date(cp.getValidade().getTime()));
        pst.setObject(3, cp.isUsado());
        pst.setObject(4, cp.getValor());
        
        pst.execute();
        
        ResultSet rs = pst.getGeneratedKeys();
        
        if(rs.next())
            cp.setId(rs.getInt(1));
        
        sql.setLength(0);
        
        sql.append("insert into cupons_promocionais_clientes(cpc_id_cliente, cpc_id_cupom_promocional)values(?,?)");
        
        pst = connection.prepareStatement(sql.toString());
                
        pst.setInt(1, cp.getCliente().getId());
        pst.setInt(2, cp.getId());
        
        pst.execute();
        
        connection.close();
        
        return cp;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomPromocional cp = (CupomPromocional) entidadeDominio;
               
        StringBuilder sql = new StringBuilder();
        
        sql.append("update cupons_promocionais set cp_usado = ? where cp_id_cupom_promocional = ?");

        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, cp.isUsado());
        pst.setObject(2, cp.getId());
        
        pst.execute();
        
        connection.close();
                
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
