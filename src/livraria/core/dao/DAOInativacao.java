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
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Inativacao;

/**
 *
 * @author johnv
 */
public class DAOInativacao extends AbstractJDBCDAO{

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {
    
        Collection<EntidadeDominio> entidades = new ArrayList<>();
        
        return entidades;        
    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {
        
        Inativacao inativacao = (Inativacao) entidadeDominio;
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("insert into inativacoes_livro (il_id_livro, il_justificativa, il_categoria, il_data) values(?,?,?,?)");
        
        this.openConnection();
        
        PreparedStatement pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, inativacao.getLivro().getId());
        pst.setObject(2, inativacao.getJustificativa());
        pst.setObject(3, inativacao.getCategoriaInativacao());
        pst.setObject(4, new java.sql.Date((new Date()).getTime()));
        
        pst.execute();
        
        sql.setLength(0);
        sql.append("update livros set liv_ativo = ?, liv_status = ? where liv_id_livro = ?");

        pst = connection.prepareStatement(sql.toString());
        
        pst.setObject(1, inativacao.getLivro().isAtivo());
        pst.setObject(2, inativacao.getLivro().getStatus());
        pst.setObject(3, inativacao.getLivro().getId());
        
        pst.execute();
        
        return inativacao;
        
    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    }
    
}
