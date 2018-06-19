//Classes para consultar Grupo de Precificação
//@author John Vitor da Silva Quispe
//@date 20/03/2018

package livraria.core.dao;

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
    
        GrupoPrecificacao gp = (GrupoPrecificacao) entidadeDominio;
                
        this.openConnection();
        
        PreparedStatement pst = null; 
        
        String query = "SELECT GP_ID_GRUPO_PRECIFICACAO, GP_NOME, GP_MARGEM_LUCRO FROM GRUPOS_PRECIFICACAO";
        
        if(gp.getId() != 0)
            query += " where gp_id_grupo_precificacao = ? ";
        
        pst = connection.prepareStatement(query);
        
        if(gp.getId() != 0)
            pst.setInt(1, gp.getId());
        
        ResultSet rs = pst.executeQuery();
        
        Collection<EntidadeDominio> gps = new ArrayList<EntidadeDominio>();
        
        GrupoPrecificacao grupoPrecificacao = null;
        
        while(rs.next()){

            grupoPrecificacao = new GrupoPrecificacao();
            
            grupoPrecificacao.setId(rs.getInt("GP_ID_GRUPO_PRECIFICACAO"));
            grupoPrecificacao.setNome(rs.getString("GP_NOME"));
            grupoPrecificacao.setPercentual(rs.getDouble("GP_MARGEM_LUCRO"));
            
            gps.add(grupoPrecificacao);
            
        }
                
        connection.close();
        
        return gps;
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException{
    
        return null;
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException{
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException{
    }
    
    
    
}
