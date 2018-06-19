
package livraria.factory.impl;

import java.util.HashMap;
import livraria.core.IDAO;
import livraria.core.dao.DAOAdministrador;
import livraria.core.dao.DAOAtivacao;
import livraria.core.dao.DAOAutor;
import livraria.core.dao.DAOBandeira;
import livraria.core.dao.DAOCartao;
import livraria.core.dao.DAOCategoria;
import livraria.core.dao.DAOCategoriaAtivacao;
import livraria.core.dao.DAOCliente;
import livraria.core.dao.DAOCupomPromocional;
import livraria.core.dao.DAOCupomTroca;
import livraria.core.dao.DAOEditora;
import livraria.core.dao.DAOEnderecoCobranca;
import livraria.core.dao.DAOEnderecoEntrega;
import livraria.core.dao.DAOEntradaEstoque;
import livraria.core.dao.DAOFornecedor;
import livraria.core.dao.DAOGrupoPrecificacao;
import livraria.core.dao.DAOInativacao;
import livraria.core.dao.DAOLivro;
import livraria.core.dao.DAOPedido;
import livraria.core.dao.DAOUsuario;
import livraria.dominio.Administrador;
import livraria.dominio.Ativacao;
import livraria.dominio.Autor;
import livraria.dominio.Bandeira;
import livraria.dominio.Cartao;
import livraria.dominio.Categoria;
import livraria.dominio.CategoriaAtivacao;
import livraria.dominio.Cliente;
import livraria.dominio.CupomPromocional;
import livraria.dominio.CupomTroca;
import livraria.dominio.Editora;
import livraria.dominio.EnderecoCobranca;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.Entrada;
import livraria.dominio.Fornecedor;
import livraria.dominio.GrupoPrecificacao;
import livraria.dominio.Inativacao;
import livraria.dominio.Livro;
import livraria.dominio.Pedido;
import livraria.dominio.Usuario;


public class FactoryDAO{
    
    public static IDAO factory(String dao) {
    
        //DAO
        HashMap<String, IDAO>daos = new HashMap<String, IDAO>();
        //Instaciando DAOS e armazenando no HashMap
        daos.put(Livro.class.getName(), new DAOLivro());
        daos.put(GrupoPrecificacao.class.getName(), new DAOGrupoPrecificacao());
        daos.put(Editora.class.getName(), new DAOEditora());
        daos.put(Autor.class.getName(), new DAOAutor());
        daos.put(Categoria.class.getName(), new DAOCategoria());
        daos.put(CategoriaAtivacao.class.getName(), new DAOCategoriaAtivacao());
        daos.put(Ativacao.class.getName(), new DAOAtivacao());
        daos.put(Inativacao.class.getName(), new DAOInativacao());
        daos.put(Cliente.class.getName(), new DAOCliente());
        daos.put(Usuario.class.getName(), new DAOUsuario());
        daos.put(EnderecoEntrega.class.getName(), new DAOEnderecoEntrega());
        daos.put(Cartao.class.getName(), new DAOCartao());
        daos.put(Administrador.class.getName(), new DAOAdministrador());
        daos.put(Entrada.class.getName(), new DAOEntradaEstoque());
        daos.put(Pedido.class.getName(), new DAOPedido());
        daos.put(CupomPromocional.class.getName(), new DAOCupomPromocional());
        daos.put(CupomTroca.class.getName(), new DAOCupomTroca());
        daos.put(Inativacao.class.getName(), new DAOInativacao());
        daos.put(Ativacao.class.getName(), new DAOAtivacao());
        daos.put(Bandeira.class.getName(), new DAOBandeira());
        daos.put(EnderecoCobranca.class.getName(), new DAOEnderecoCobranca());
        daos.put(Fornecedor.class.getName(), new DAOFornecedor());
        
        return daos.get(dao);
    }
    
}
