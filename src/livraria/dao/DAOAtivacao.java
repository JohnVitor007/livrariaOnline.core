/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import livraria.dominio.Ativacao;
import livraria.dominio.CategoriaAtivacao;
import livraria.dominio.EntidadeDominio;

/**
 *
 * @author johnv
 */
public class DAOAtivacao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Collection<EntidadeDominio> entidades = new ArrayList<>();
        
        return entidades;
        
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio) throws SQLException {

        this.openConnection();
        
        PreparedStatement pst = null;
        
        Ativacao ativacao = (Ativacao) entidadeDominio;
        
        String sql = "insert into ativacoes (ati_id_livro, ati_id_categoria_ativacao, ati_justificativa, ati_data) " +
                "values(?,?,?,?)";
        
        pst = connection.prepareStatement(sql);        
        
        pst.setInt(1 , ativacao.getLivro().getId());
        pst.setInt(2, ativacao.getCategoriaAtivacao().getId());
        pst.setString(3, ativacao.getJustificativa());
        
        Date date = new Date();
        
        pst.setDate(4, java.sql.Date.valueOf((date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" +(date.getDate())));
        
        pst.executeUpdate();
        pst.close();
        
        DAOAtivacao daoAtivacao = new DAOAtivacao();
        Collection<EntidadeDominio> entidades = daoAtivacao.consultar(ativacao.getCategoriaAtivacao());

        for(EntidadeDominio eds : entidades){
            
            CategoriaAtivacao categoriaAtivacao = (CategoriaAtivacao) eds;
            
            System.out.println("ID: " + categoriaAtivacao.getId());
            
            sql= "update livros set liv_ativo = ?, liv_status = ? where liv_id_livro = ?";
            pst = connection.prepareStatement(sql);

            pst.setBoolean(1, true);
            pst.setString(2, categoriaAtivacao.getNome());
            pst.setInt(3, ativacao.getLivro().getId());

            pst.executeUpdate();
            pst.close();
            
        }
        
        connection.commit();
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    }
    
}
