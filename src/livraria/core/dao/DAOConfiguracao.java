package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.Configuracao;
import livraria.dominio.EntidadeDominio;

public class DAOConfiguracao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Collection<EntidadeDominio> confs = new ArrayList();

        String sql = "select qtd_cupons, tempo_carrinho, valor_minimo_venda from configuracoes";
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql);
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            
            Configuracao c = new Configuracao();
            c.setQtdCupons(rs.getInt("qtd_cupons"));
            c.setValor_minimo_venda(rs.getInt("valor_minimo_venda"));
            confs.add(c);
            
        }

        return confs;
        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Configuracao c = (Configuracao) entidadeDominio;
        
        String sql = "update configuracoes set qtd_cupons = ?";
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql);
        
        pst.setObject(1, c.getQtdCupons());
        
        pst.execute();
        
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
