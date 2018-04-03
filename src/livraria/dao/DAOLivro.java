/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livraria.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import livraria.dominio.Ativacao;
import livraria.dominio.Autor;
import livraria.dominio.Categoria;
import livraria.dominio.CategoriaInativacao;
import livraria.dominio.Edicao;
import livraria.dominio.Editora;
import livraria.dominio.Dimensao;
import livraria.dominio.Disponibilidade;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.GrupoPrecificacao;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;
import livraria.dominio.Peso;
import livraria.dominio.UnidadeMedida;

/**
 *
 * @author johnv
 */
public class DAOLivro extends AbstractJDBCDAO{

    private Map<Object, String> mapaString;
    private Map<Object, String> mapaDouble;
    private Map<Object, String> mapaInteger;
    
    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {

        this.openConnection();
                        
        Livro l = (Livro) entidadeDominio;
        
        //Categorias
        //Autores
        //Data cadastro
        
        String sql = "select a.liv_id_livro, a.liv_titulo, a.liv_subtitulo, a.liv_edicao, a.liv_ano, a.liv_id_editora, a.liv_isbn, \n" +
        "a.liv_numero_paginas, a.liv_sinopse, a.liv_altura, a.liv_largura, a.liv_peso, a.liv_profundidade, a.liv_id_grupo_precificacao,\n" +
        "a.liv_quantidade, a.liv_ativo, a.liv_status, a.liv_unidade_medida_peso, a.liv_data_cadastro, a.liv_cod_barras from livros a";
                
        PreparedStatement pst = null; 
        
        if(l.getId() != 0 && l.getEdicao() == null){
            sql+= " where a.liv_id_livro = ?";
        }
       
        else if(l.getEdicao() != null && l.getId() == 0){
            
            sql = "select a.liv_id_livro, a.liv_titulo, a.liv_subtitulo, a.liv_edicao, a.liv_ano, a.liv_id_editora, a.liv_isbn, "+
            "a.liv_numero_paginas, a.liv_sinopse, a.liv_altura, a.liv_largura, a.liv_peso, a.liv_profundidade, "+
            "a.liv_id_grupo_precificacao, a.liv_quantidade, a.liv_ativo, a.liv_status, a.liv_unidade_medida_peso, "+ 
            "a.liv_data_cadastro, a.liv_cod_barras, b.gp_nome from livros a inner join grupos_precificacao b on "+
            "a.liv_id_grupo_precificacao = b.gp_id_grupo_precificacao inner join editoras c on "+
            "a.liv_id_editora = c.edi_id_editora where 1 = 1";
            
            mapaString = new HashMap<>();
            mapaDouble = new HashMap<>();
            mapaInteger = new HashMap<>();
            //Map<Object, String> mapaDate = new HashMap<>();
            
            mapaString.put(l.getTitulo(), "and a.liv_titulo = ?");
            mapaString.put(l.getSubtitulo(), "and a.liv_subtitulo = ?");
            mapaInteger.put(l.getEdicao().getNumero(), "and a.liv_edicao = ?");
            mapaInteger.put(l.getEdicao().getAno(), "and a.liv_ano = ?");
            mapaString.put(l.getEditora().getNome(), "and c.edi_nome = ?");
            mapaString.put(l.getISBN(), "and a.liv_isbn = ?");
            mapaInteger.put(l.getEdicao().getNumeroPaginas(), "and liv_numero_paginas = ?");
            mapaString.put(l.getSinopse(), "and a.liv_sinopse = ?");
            mapaDouble.put(l.getEdicao().getDimensoes().getAltura(), "and a.liv_altura = ?");
            mapaDouble.put(l.getEdicao().getDimensoes().getLargura(), "and a.liv_largura = ?");
            mapaDouble.put(l.getEdicao().getDimensoes().getProfundidade(), "and a.liv_profundidade = ?");
            mapaDouble.put(l.getEdicao().getDimensoes().getPeso().getQuantidade(), "and a.liv_peso = ?");
            mapaString.put(l.getEdicao().getDimensoes().getPeso().getUnidadeMedida().getNome(), "and a.liv_unidade_medida_peso = ?");
            mapaInteger.put(l.getGrupoPrecificacao().getId(), "and b.gp_id_grupo_precificacao = ?");
            mapaString.put(l.getCodigoBarras(), "and a.liv_cod_barras = ?");  
            
            for(Map.Entry<Object, String> entry : mapaString.entrySet()){
                if (!entry.getKey().equals("")){
                    sql+= " " + entry.getValue();
                }                
            }
            
            for(Map.Entry<Object, String> entry : mapaInteger.entrySet()){
                if (Integer.valueOf(entry.getKey().toString()) != 0){
                    sql+= " " + entry.getValue();
                }                
            }
            
            for(Map.Entry<Object, String> entry : mapaDouble.entrySet()){
                if (Double.valueOf(entry.getKey().toString()) != 0){
                    sql+= " " + entry.getValue();
                }                
            }
            
        }
        
        pst = connection.prepareStatement(sql);        
         
        if(l.getId() != 0 && l.getEdicao() == null){
            pst.setInt(1, l.getId());
        }
        
        if(l.getEdicao() != null && l.getId() == 0){
            int pos = 0;

            for(Map.Entry<Object, String> entry : mapaString.entrySet()){
                if (!entry.getKey().equals("")){
                    pos++;
                    pst.setString(pos, entry.getKey().toString());
                }                
            }
            
            for(Map.Entry<Object, String> entry : mapaInteger.entrySet()){
                if (Integer.valueOf(entry.getKey().toString()) != 0){
                    pos++;
                    pst.setInt(pos, Integer.valueOf(entry.getKey().toString()));
                }                
            }
            
            for(Map.Entry<Object, String> entry : mapaDouble.entrySet()){
                if (Double.valueOf(entry.getKey().toString()) != 0){
                    pos++;
                    pst.setDouble(pos, Double.valueOf(entry.getKey().toString()));
                            
                }                
            }

        }
        
        Collection<EntidadeDominio> livros = new ArrayList<>();
        
        ResultSet rs = pst.executeQuery();
        
        while(rs.next()){
            
            l = new Livro();
            
            l.setId(rs.getInt("liv_id_livro"));
            l.setTitulo(rs.getString("liv_titulo"));
            l.setSubtitulo(rs.getString("liv_subtitulo"));
            l.setISBN(rs.getString("liv_isbn"));
            l.setSinopse(rs.getString("liv_sinopse"));
            l.setQuantidade(rs.getInt("liv_quantidade"));
            l.setStatus(rs.getString("liv_status"));
            l.setCodigoBarras(rs.getString("liv_cod_barras"));
            l.setAtivo(rs.getBoolean("liv_ativo"));
            l.setDataCadastro(rs.getDate("liv_data_cadastro"));
            
            Edicao edicao = new Edicao();
            edicao.setNumero(rs.getInt("liv_edicao"));
            edicao.setAno(rs.getInt("liv_ano"));
            edicao.setNumeroPaginas(rs.getInt("liv_numero_paginas"));
            
            Dimensao dimensao = new Dimensao();
            dimensao.setAltura(rs.getDouble("liv_altura"));
            dimensao.setLargura(rs.getDouble("liv_largura"));
            dimensao.setProfundidade(rs.getDouble("liv_profundidade"));
            
            Peso peso = new Peso();
            peso.setQuantidade(rs.getDouble("liv_peso"));
            
            UnidadeMedida unidadeMedida = new UnidadeMedida();
            unidadeMedida.setNome(rs.getString("liv_unidade_medida_peso"));
            peso.setUnidadeMedida(unidadeMedida);
            
            dimensao.setPeso(peso);
            
            edicao.setDimensoes(dimensao);
            
            l.setEdicao(edicao);
            
            Editora editora = new Editora();
            editora.setId(rs.getInt("liv_id_editora"));
            
            l.setEditora(editora);
            
            GrupoPrecificacao grupoPrecificacao = new GrupoPrecificacao();
            
            grupoPrecificacao.setId(rs.getInt("liv_id_grupo_precificacao"));
            
            l.setGrupoPrecificacao(grupoPrecificacao);
            
            Collection<Disponibilidade> disponibilidades = new ArrayList<>();
            Disponibilidade disponibilidade = new Disponibilidade();
            disponibilidade.setAtivo(rs.getBoolean("liv_ativo"));
      
            disponibilidades.add(disponibilidade);
            l.setDisponibilidades(disponibilidades);
            
            
            PreparedStatement prepareStatement = null; 
            ResultSet resultSet = null;
            
            sql = "select a.aut_id_autor, a.aut_nome from autores a inner join livros_autores b on "+
                    "a.aut_id_autor = b.la_id_autor where b.la_id_livro = ?";
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getId());
            
            resultSet = prepareStatement.executeQuery();
            
            Collection<Autor> autores = new ArrayList<>();
            
            while(resultSet.next()){
                Autor autor = new Autor();
                autor.setId(resultSet.getInt("aut_id_autor"));
                autor.setNome(resultSet.getString("aut_nome"));
                autores.add(autor);
            }
            
            
            l.setAutor(autores);
                        
            sql = "select a.cat_id_categoria, a.cat_nome from categorias a inner join categorias_livro b "+
                    "on a.cat_id_categoria = cl_id_categoria where b.cl_id_livro = ?";
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getId());
            
            resultSet = prepareStatement.executeQuery();
            
            Collection<Categoria> categorias = new ArrayList<>();
            
            while(resultSet.next()){
                Categoria categoria = new Categoria();
                categoria.setId(resultSet.getInt("cat_id_categoria"));
                categoria.setNome(resultSet.getString("cat_nome"));
                categorias.add(categoria);
            }
            
            l.setCategoria(categorias);
                       
            sql = "select gp_nome, gp_margem_lucro from"+
                    " grupos_precificacao where gp_id_grupo_precificacao = ?";
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getGrupoPrecificacao().getId());
            
            resultSet = prepareStatement.executeQuery();
            
            if(resultSet.next()){
                
                grupoPrecificacao.setNome(resultSet.getString("gp_nome"));
                grupoPrecificacao.setPercentual(resultSet.getDouble("gp_margem_lucro"));
                
            }
            
            sql = "select edi_nome from editoras where edi_id_editora = ?";
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getEditora().getId());
            
            resultSet = prepareStatement.executeQuery();
            
            if(resultSet.next()){
                
                editora.setNome(resultSet.getString("edi_nome"));
                
            }
            
            Collection<Inativacao> inativacoes = new ArrayList<>();
            
            sql = "select a.ina_id_livro, a.ina_id_categoria_inativacao, a.ina_justificativa, a.ina_data, b.cin_nome_categoria_inativacao "+
                "from inativacoes a inner join categorias_inativacao b on a.ina_id_categoria_inativacao = "+
                "b.cin_id_categoria_inativacao where a.ina_id_livro = ?";
            
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getId());
            
            resultSet = prepareStatement.executeQuery();
            /*
            if(resultSet.next() != false){
                
                do{
                    /*
                    CategoriaInativacao ci = new CategoriaInativacao();
                    ci.setId(rs.getInt("ina_id_categoria_inativacao"));
                    ci.setNome("cin_nome_categoria_inativacao");

                    Inativacao inativacao = new Inativacao();
                    inativacao.setCategoriaInativacao(ci);
                    inativacao.setJustificativa(rs.getString("ina_justificativa"));
                    inativacao.setData(rs.getDate("ina_data"));

                    inativacoes.add(inativacao);
                    
                    
                }while(rs.next());
            
            }
            
            l.setInativacoes(inativacoes);
            
            
            Collection<Ativacao> ativacoes = new ArrayList<>();
                        
            sql = "select a.ati_id_categoria_ativacao, a.ati_justificativa, a.ati_data, b.caa_nome_categoria_ativacao "+
                "from ativacoes a inner join categorias_ativacao b on a.ati_id_categoria_ativacao = "+
                "b.caa_id_categoria_ativacao where a.ati_id_livro = ?";
            
            
            prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setInt(1, l.getId());
            
            //resultSet = prepareStatement.executeQuery();
            /*
            while(resultSet.next()){
                /*
                CategoriaInativacao ci = new CategoriaInativacao();
                ci.setId(resultSet.getInt("ati_id_categoria_inativacao"));
                ci.setNome("caa_nome_categoria_ativacao");
                
                Inativacao inativacao = new Inativacao();
                inativacao.setCategoriaInativacao(ci);
                inativacao.setJustificativa(resultSet.getString("ati_justificativa"));
                inativacao.setData(resultSet.getDate("ati_data"));
                
                inativacoes.add(inativacao);
                
            }
            
            l.setInativacoes(inativacoes);
            */
            livros.add(l);
            
        }
                
        return livros;
        
    }

    @Override
    public void salvar(EntidadeDominio entidadeDominio){
        
        this.openConnection();
        PreparedStatement pst = null;
                
        Livro l = (Livro) entidadeDominio;
                
        try {
            
            connection.setAutoCommit(false);
            
            Collection<Autor> autores = l.getAutor();
            
            String sql = "INSERT INTO AUTORES(AUT_NOME)VALUES(?)";
            
            for(Autor autor : autores){
                System.out.println("Nome" + autor.getNome());
                
                if(autor.getNome().length() > 0){

                    PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    
                    prepareStatement.setString(1, autor.getNome());
                    prepareStatement.executeUpdate();
                    
                    ResultSet resultSet = prepareStatement.getGeneratedKeys();
                    if(resultSet.next())                    
                        autor.setId(resultSet.getInt(1));
                    
                    prepareStatement.close();
                    
                    
                }
                

            }
            
            
            Collection<Categoria> categorias = l.getCategoria();

            sql = "INSERT INTO CATEGORIAS(CAT_NOME)VALUES(?)";

            for(Categoria categoria : categorias){
                    
                    if(categoria.getNome().length() > 0){
                        pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, categoria.getNome());
                        pst.executeUpdate();
                        ResultSet resultSet = pst.getGeneratedKeys();
                        if(resultSet.next())
                            categoria.setId(resultSet.getInt(1));
                        pst.close();

                    }
                    
            }

            Editora editora = l.getEditora();

            sql = "INSERT INTO EDITORAS(EDI_NOME)VALUES(?)";

            if(editora.getNome().length() > 0){
                pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, editora.getNome());
                pst.executeUpdate();

                ResultSet resultSet = pst.getGeneratedKeys();
                if(resultSet.next())
                    editora.setId(resultSet.getInt(1));
                pst.close();
                
            }
            
            sql = "insert into livros(liv_titulo, liv_edicao, liv_ano, liv_id_editora,"+
            "liv_isbn, liv_numero_paginas, liv_sinopse, liv_altura, liv_largura,"+
            "liv_profundidade, liv_peso, liv_id_grupo_precificacao, liv_quantidade, liv_ativo, liv_status, "
            + "liv_unidade_medida_peso, liv_data_cadastro, liv_subtitulo, liv_cod_barras)"
            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    
            pst.setString(1, l.getTitulo());
            pst.setInt(2, l.getEdicao().getNumero());
            pst.setInt(3, l.getEdicao().getAno());
            pst.setInt(4, l.getEditora().getId());
            pst.setString(5, l.getISBN());
            pst.setInt(6, l.getEdicao().getNumeroPaginas());
            pst.setString(7, l.getSinopse());
            pst.setDouble(8, l.getEdicao().getDimensoes().getAltura());
            pst.setDouble(9, l.getEdicao().getDimensoes().getLargura());
            pst.setDouble(10, l.getEdicao().getDimensoes().getProfundidade());
            pst.setDouble(11, l.getEdicao().getDimensoes().getPeso().getQuantidade());
            pst.setInt(12, l.getGrupoPrecificacao().getId());
            pst.setInt(13, 0);
            pst.setBoolean(14, l.isAtivo());
            pst.setString(15, l.getStatus());
            pst.setString(16, l.getEdicao().getDimensoes().getPeso().getUnidadeMedida().getNome());
            pst.setDate(17, Date.valueOf((l.getDataCadastro().getYear() + 1900) + "-" + (l.getDataCadastro().getMonth() + 1) + "-" +(l.getDataCadastro().getDate())));
            pst.setString(18, l.getSubtitulo());
            pst.setString(19, l.getCodigoBarras());
            
            pst.executeUpdate();
           
            ResultSet rs = pst.getGeneratedKeys();
            if(rs.next())
                l.setId(rs.getInt(1));
            
            pst.close();
            
            
            sql = "INSERT INTO CATEGORIAS_LIVRO(CL_ID_LIVRO, CL_ID_CATEGORIA)VALUES(?,?)";
            
            for(Categoria categoria : categorias){
             
                pst = connection.prepareStatement(sql);
                pst.setInt(1, l.getId());
                pst.setInt(2, categoria.getId());
                pst.executeUpdate();
                pst.close();
                
            }
            
            sql = "INSERT INTO LIVROS_AUTORES(LA_ID_AUTOR, LA_ID_LIVRO)VALUES(?,?)";
            
            for(Autor autor : autores){
                
                pst = connection.prepareStatement(sql);
                pst.setInt(1, autor.getId());
                pst.setInt(2, l.getId());
                pst.executeUpdate();
                pst.close();
                
            }
            
            Collection<Inativacao> inativacoes = l.getInativacoes();
            
            for(Inativacao inativacao : inativacoes){
                
                sql = "INSERT INTO INATIVACOES(INA_ID_LIVRO, INA_ID_CATEGORIA_INATIVACAO, INA_JUSTIFICATIVA, INA_DATA)"+
                    "VALUES(?,?,?,?)";
            
                pst = connection.prepareStatement(sql);
                pst.setInt(1, l.getId());
                pst.setInt(2, inativacao.getCategoriaInativacao().getId());
                pst.setString(3, inativacao.getJustificativa());
                pst.setDate(4, Date.valueOf((l.getDataCadastro().getYear() + 1900) + "-" + (l.getDataCadastro().getMonth() + 1) + "-" +(l.getDataCadastro().getDate())));
                pst.executeUpdate();
                pst.close();
                
            }
            
            connection.commit();
            
        
        } catch (SQLException ex) {
            try{
                connection.rollback();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
            ex.printStackTrace();
        }finally{
            try{
                pst.close();
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        
    }
    
    @Override
    public void alterar(EntidadeDominio entidadeDominio) {
    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) {
    }
    
}
