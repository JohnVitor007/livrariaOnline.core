package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.Cliente;
import livraria.dominio.CupomTroca;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Pedido;

public class DAOCupomTroca extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomTroca ct = (CupomTroca) entidadeDominio;
        List<EntidadeDominio> cts = new ArrayList();
        
        StringBuilder sql = new StringBuilder();
        
        if(ct.getId() != 0){
            sql.append("select ct_id_cupom_troca, ct_identificacao, ct_usado, ct_valor from cupons_troca ct join cupons_troca_clientes ctc on ctc.ctc_id_cupom_troca = ct.ct_id_cupom_troca ");
            sql.append("where ");
            sql.append(" ctc.ctc_id_cupom_troca = ?");
            
        }else if(ct.getCliente() != null){
            sql.append("select ct_id_cupom_troca, ct_identificacao, ct_usado, ct_valor from cupons_troca ct join cupons_troca_clientes ctc on ctc.ctc_id_cupom_troca = ct.ct_id_cupom_troca ");
            sql.append("where ");    
            sql.append("ctc.ctc_id_cliente = ?");
        }else if(ct.getIdentificador() != null){
            sql.append("select ct_id_cupom_troca, ct_identificacao, ct_usado, ct_valor from cupons_troca ct ");
            sql.append("where ");    
            sql.append("ct.ct_identificacao = ?");
        }
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        if(ct.getId() != 0)
            pst.setInt(1, ct.getId());
        else if(ct.getCliente() != null)
            pst.setInt(1, ct.getCliente().getId());
        else if(ct.getIdentificador() != null){
            pst.setObject(1, ct.getIdentificador());
        }
        
        ResultSet rs = pst.executeQuery();
        
        CupomTroca bufferCt;
        
        while(rs.next()){
            
            bufferCt = new CupomTroca();
            bufferCt.setCliente(new Cliente());
            bufferCt.getCliente().setId(ct.getId());
            bufferCt.setId(rs.getInt("ct_id_cupom_troca"));
            bufferCt.setIdentificador(rs.getString("ct_identificacao"));
            bufferCt.setUsado(rs.getBoolean("ct_usado"));
            bufferCt.setValor(rs.getDouble("ct_valor"));
            bufferCt.setPedido(new Pedido());

            cts.add(bufferCt);
        }
        
        connection.close();
        
        return cts;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomTroca ct = (CupomTroca) entidadeDominio;
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into cupons_troca(ct_identificacao, ct_usado, ct_valor)values(?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        
        pst.setObject(1, ct.getIdentificador());
        pst.setObject(2, ct.isUsado());
        pst.setObject(3, ct.getValor());
        
        pst.execute();
        
        ResultSet rs = pst.getGeneratedKeys();
        
        if(rs.next())
            ct.setId(rs.getInt(1));
        
        sql.setLength(0);
        sql.append("insert into cupons_troca_clientes(ctc_id_cliente, ctc_id_cupom_troca)values(?,?)");
        
        pst = connection.prepareStatement(sql.toString());
        
        pst.setInt(1, ct.getCliente().getId());
        pst.setInt(2, ct.getId());
        
        pst.execute();
        
        connection.close();
        
        return ct;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        CupomTroca ct = (CupomTroca) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("update cupons_troca set ct_usado = ? where ct_id_cupom_troca =  ?");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());

        pst.setObject(1, ct.isUsado());
        pst.setObject(2, ct.getId());
        
        pst.execute();
                
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
