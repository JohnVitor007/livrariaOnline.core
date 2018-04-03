//Classes para consultar Grupo de Precificação
//@author John Vitor da Silva Quispe
//@date 20/03/2018

package livraria.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.GrupoPrecificacao;

public class DAOGrupoPrecificacao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT GP_ID_GRUPO_PRECIFICACAO, GP_NOME, GP_MARGEM_LUCRO FROM GRUPOS_PRECIFICACAO";
        
        pst = connection.prepareStatement(query);
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> gps = new ArrayList<EntidadeDominio>();
        
        while(rs.next()){
        
            GrupoPrecificacao gp = new GrupoPrecificacao();
            gp.setId(rs.getInt("GP_ID_GRUPO_PRECIFICACAO"));
            gp.setNome(rs.getString("GP_NOME"));
            gp.setPercentual(rs.getDouble("GP_MARGEM_LUCRO"));
            
            gps.add(gp);
            
        }
                
        return gps;
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) throws SQLException{
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException{
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException{
    }
    
    
    
}
