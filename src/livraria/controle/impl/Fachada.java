//Classe Fachada
//@author John Vitor da Silva Quispe
//@date 19/03/2018

package livraria.controle.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import livraria.aplicacao.Resultado;
import livraria.core.IDAO;
import livraria.core.IFachada;
import livraria.core.IStrategy;
import livraria.core.negocio.ComplementarDadosCadastroLivro;
import livraria.core.negocio.ValidarDadosObrigatoriosLivro;
import livraria.core.negocio.ValidarExistenciaLivro;
import livraria.dao.DAOAtivacao;
import livraria.dao.DAOAutor;
import livraria.dao.DAOCategoria;
import livraria.dao.DAOCategoriaAtivacao;
import livraria.dao.DAOEditora;
import livraria.dao.DAOGrupoPrecificacao;
import livraria.dao.DAOInativacao;
import livraria.dao.DAOLivro;
import livraria.dominio.Ativacao;
import livraria.dominio.Autor;
import livraria.dominio.Categoria;
import livraria.dominio.CategoriaAtivacao;
import livraria.dominio.Editora;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.GrupoPrecificacao;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;

public class Fachada implements IFachada{

    private Map<String, IDAO> daos;
    private Map<String, Map<String, List<IStrategy>>> rns;
    
    public Fachada(){
        
        //DAO
        daos = new HashMap<String, IDAO>();
        //Instaciando DAOS e armazenando no HashMap
        daos.put(Livro.class.getName(), new DAOLivro());
        daos.put(GrupoPrecificacao.class.getName(), new DAOGrupoPrecificacao());
        daos.put(Editora.class.getName(), new DAOEditora());
        daos.put(Autor.class.getName(), new DAOAutor());
        daos.put(Categoria.class.getName(), new DAOCategoria());
        daos.put(CategoriaAtivacao.class.getName(), new DAOCategoriaAtivacao());
        daos.put(Ativacao.class.getName(), new DAOAtivacao());
        daos.put(Inativacao.class.getName(), new DAOInativacao());
        
        //REGRAS DE NEGÓCIO
        rns = new  HashMap<String, Map<String, List<IStrategy>>>();        
                
        //LIVRO
        //Lista das regras de Negócios do livro do salvar livro
        List<IStrategy> lrnsl = new ArrayList<IStrategy>();
        lrnsl.add(new ValidarDadosObrigatoriosLivro());
        lrnsl.add(new ValidarExistenciaLivro());
        lrnsl.add(new ComplementarDadosCadastroLivro());
       
        //HashMap que contém todas as regras de negócio de salvar Livro
        Map<String, List<IStrategy>> rnsl = new HashMap<String, List<IStrategy>>();
        rnsl.put("salvar", lrnsl);
        
        //HashMap que conterá todas as regras de negócio do sistema
        rns.put(Livro.class.getName(), rnsl);
        
        //
        
    }
    
    
    @Override
    public Resultado consultar(EntidadeDominio entidadeDominio) {
        
        List<String> msg = executarRegras(entidadeDominio, "consultar");
       
        Resultado resultado = new Resultado();
        
        if(msg == null){
        
            IDAO dao = daos.get(entidadeDominio.getClass().getName());
            
            try {
                resultado.setEntidades(dao.consultar(entidadeDominio));                
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
                        
        }//If
        
        resultado.setResultados(msg);
        
        return resultado;
    }

    @Override
    public Resultado salvar(EntidadeDominio entidadeDominio) {

        List<String> msg = executarRegras(entidadeDominio, "salvar");
       
        Resultado resultado = new Resultado();
        
        if(msg == null){
        
            IDAO dao = daos.get(entidadeDominio.getClass().getName());
            
            try {
                dao.salvar(entidadeDominio);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
                        
        }//If
        
        resultado.setResultados(msg);
        
        return resultado;
        
        
    }

    @Override
    public Resultado alterar(EntidadeDominio entidadeDominio) {
        
        return null;
    }

    @Override
    public Resultado excluir(EntidadeDominio entidadeDominio) {
        
        return null;
    }
    
    
    private List<String> executarRegras(EntidadeDominio entidadeDominio, String operacao){
        
        List<String> msg = new ArrayList<String>();
                      
        Map<String, List<IStrategy>> regrasEntidade = rns.get(entidadeDominio.getClass().getName());
    
        if(regrasEntidade != null){
            
            List<IStrategy> regras = regrasEntidade.get(operacao);
            
            if(regras != null){
            
                for(IStrategy strategy : regras){
                    
                    String m = strategy.processar(entidadeDominio);
                    if(m != null){
                        msg.add(m);
                        return msg;
                    }
                    
                }
            
            }
            
        }
        
        if(msg.isEmpty())
            return null;
        else 
            return msg;
        
    }
}
