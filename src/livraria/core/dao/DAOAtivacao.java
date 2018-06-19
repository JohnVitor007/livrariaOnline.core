/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.core.dao;

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
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        
        this.openConnection();
        
        PreparedStatement pst = null;
        
        Ativacao ativacao = (Ativacao) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        
        sql.append("insert into ativacoes_livro (al_id_livro, al_justificativa, al_categoria, al_data)values(?,?,?,?)");
        
        pst = connection.prepareStatement(sql.toString());        
        
        pst.setInt(1 , ativacao.getLivro().getId());
        pst.setString(2, ativacao.getJustificativa());
        pst.setString(3, ativacao.getCategoriaAtivacao());
        pst.setObject(4, new java.sql.Date(ativacao.getData().getTime()));
                
        pst.execute();
        
        sql.setLength(0);
        sql.append("update livros set liv_ativo = ?, liv_status = ? where liv_id_livro = ?");

        pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, ativacao.getLivro().isAtivo());
        pst.setObject(2, ativacao.getLivro().getStatus());
        pst.setObject(3, ativacao.getLivro().getId());
        
        pst.execute();
        
        connection.close();
        
        return ativacao;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    }
    
}
