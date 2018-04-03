/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import livraria.dominio.EntidadeDominio;

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
    public void salvar(EntidadeDominio entidadeDominio) throws SQLException {

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
    }
    
}
