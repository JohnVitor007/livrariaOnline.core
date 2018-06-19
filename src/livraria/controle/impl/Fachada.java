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
import livraria.core.negocio.CalculadoraPagamentoCartao;
import livraria.core.negocio.ValidadorUsoCupom;
import livraria.core.negocio.CalculadoraValorSubtotal;
import livraria.core.negocio.CalculadoraValorTotal;
import livraria.core.negocio.CalcularPrecoPeloCusto;
import livraria.core.negocio.ComplementarDadosAlteracaoCadastroLivro;
import livraria.core.negocio.ComplementarDadosAtivacaoLivro;
import livraria.core.negocio.ComplementarDadosCadastroAdministrador;
import livraria.core.negocio.ComplementarDadosCadastroBandeira;
import livraria.core.negocio.ComplementarDadosCadastroCliente;
import livraria.core.negocio.ComplementarDadosCadastroLivro;
import livraria.core.negocio.ComplementarDadosCartao;
import livraria.core.negocio.ComplementarDadosEntradaEstoque;
import livraria.core.negocio.ComplementarDadosInativacaoLivro;
import livraria.core.negocio.ComplementarDadosPedido;
import livraria.core.negocio.CriptografarSenha;
import livraria.core.negocio.DarBaixaCupomTroca;
import livraria.core.negocio.DarBaixaEstoque;
import livraria.core.negocio.ExcluirCupom;
import livraria.core.negocio.GeradorCupomPromocional;
import livraria.core.negocio.GeradorCupomTroca;
import livraria.core.negocio.RetornarItemEstoque;
import livraria.core.negocio.TrocarStatusPedido;
import livraria.core.negocio.ValidadorCartaoPreferencial;
import livraria.core.negocio.ValidadorDadosObrigatoriosAdministrador;
import livraria.core.negocio.ValidadorDadosObrigatoriosCadastroCliente;
import livraria.core.negocio.ValidadorDadosObrigatoriosCartao;
import livraria.core.negocio.ValidadorDadosObrigatoriosClientes;
import livraria.core.negocio.ValidadorDadosObrigatoriosEnderecoEntrega;
import livraria.core.negocio.ValidadorExistenciaCupom;
import livraria.core.negocio.ValidadorExistenciaEmail;
import livraria.core.negocio.ValidadorExistenciaUsuario;
import livraria.core.negocio.ValidadorInformacoesEntrega;
import livraria.core.negocio.ValidadorInformacoesPagamento;
import livraria.core.negocio.ValidadorQtdItensCarrinho;
import livraria.core.negocio.ValidadorSenhaUsuario;
import livraria.core.negocio.ValidadorTrocaPedido;
import livraria.core.negocio.ValidadorTrocaSenhaUsuario;
import livraria.core.negocio.ValidadorPagamentoCartao;
import livraria.core.negocio.ValidadorPrecoEstoque;
import livraria.core.negocio.ValidadorQuantidadeItensCarrinho;
import livraria.core.negocio.ValidarCupomPromocional;
import livraria.core.negocio.ValidarDadosObrigatoriosLivro;
import livraria.core.negocio.ValidarExistenciaLivro;
import livraria.core.negocio.VerificaExistenciaCategoria;
import livraria.core.negocio.VerificaExistenciaEditora;
import livraria.core.negocio.VerificarExistenciaAutor;
import livraria.dominio.Administrador;
import livraria.dominio.Ativacao;
import livraria.dominio.Bandeira;
import livraria.dominio.Carrinho;
import livraria.dominio.Cartao;
import livraria.dominio.Cliente;
import livraria.dominio.Cupom;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Entrada;
import livraria.dominio.Inativacao;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;
import livraria.dominio.Pedido;
import livraria.dominio.Usuario;
import livraria.factory.impl.FactoryDAO;

public class Fachada implements IFachada {

    private Map<String, Map<String, List<IStrategy>>> rns;

    public Fachada() {

        //REGRAS DE NEGÓCIO
        rns = new HashMap<String, Map<String, List<IStrategy>>>();

        //LIVRO
        //Lista das regras de Negócios do livro do salvar livro
        List<IStrategy> lrnsl = new ArrayList<IStrategy>();
        lrnsl.add(new ValidarDadosObrigatoriosLivro());
        lrnsl.add(new ValidarExistenciaLivro());
        lrnsl.add(new VerificaExistenciaCategoria());
        lrnsl.add(new VerificarExistenciaAutor());
        lrnsl.add(new VerificaExistenciaEditora());
        lrnsl.add(new ComplementarDadosCadastroLivro());

        List<IStrategy> lrnal = new ArrayList();
        lrnal.add(new ValidarDadosObrigatoriosLivro());
        //lrnal.add(new ValidarExistenciaLivro());
        lrnal.add(new VerificaExistenciaCategoria());
        lrnal.add(new VerificarExistenciaAutor());
        lrnal.add(new VerificaExistenciaEditora());
        lrnal.add(new ComplementarDadosAlteracaoCadastroLivro());
        
        //HashMap que contém todas as regras de negócio de salvar Livro
        Map<String, List<IStrategy>> rnl = new HashMap<String, List<IStrategy>>();
        rnl.put("salvar", lrnsl);      
        rnl.put("alterar", lrnal);
                
        //HashMap que conterá todas as regras de negócio do sistema
        rns.put(Livro.class.getName(), rnl);

        //CLIENTES
        List<IStrategy> lrnsc = new ArrayList<>();
        lrnsc.add(new ComplementarDadosCadastroCliente());
        lrnsc.add(new ValidadorExistenciaEmail());
        lrnsc.add(new ValidadorDadosObrigatoriosClientes());

        Map<String, List<IStrategy>> rnc = new HashMap<String, List<IStrategy>>();

        rnc.put("salvar", lrnsc);

        List<IStrategy> lrnac = new ArrayList<>();
        lrnac.add(new ValidadorDadosObrigatoriosCadastroCliente());
        //lrnac.add(new ValidadorExistenciaEmail());

        rnc.put("alterar", lrnac);

        rns.put(Cliente.class.getName(), rnc);

        //USUARIOS
        Map<String, List<IStrategy>> rnu = new HashMap<String, List<IStrategy>>();
        List<IStrategy> lrncu = new ArrayList<>();
        lrncu.add(new CriptografarSenha());
        lrncu.add(new ValidadorExistenciaUsuario());
        //lrncu.add(new ValidadorAcessoUsuario());

        rnu.put("consultar", lrncu);

        List<IStrategy> lrnau = new ArrayList<>();
        lrnau.add(new ValidadorTrocaSenhaUsuario());
        lrnau.add(new ValidadorSenhaUsuario());

        rnu.put("alterar", lrnau);

        rns.put(Usuario.class.getName(), rnu);

        //ENDERECOS ENTREGAS
        List<IStrategy> lrnsee = new ArrayList<>();
        lrnsee.add(new ValidadorDadosObrigatoriosEnderecoEntrega());

        List<IStrategy> lrasee = new ArrayList<>();
        lrasee.add(new ValidadorDadosObrigatoriosEnderecoEntrega());

        Map<String, List<IStrategy>> rnee = new HashMap<String, List<IStrategy>>();
        rnee.put("salvar", lrnsee);
        rnee.put("alterar", lrasee);
        rns.put(EnderecoEntrega.class.getName(), rnee);

        //CARTOES
        List<IStrategy> lrnscc = new ArrayList<>();
        lrnscc.add(new ValidadorCartaoPreferencial());
        lrnscc.add(new ValidadorDadosObrigatoriosCartao());
        lrnscc.add(new ComplementarDadosCartao());

        Map<String, List<IStrategy>> rncc = new HashMap<String, List<IStrategy>>();
        rncc.put("salvar", lrnscc);

        List<IStrategy> lrnacc = new ArrayList<>();
        lrnacc.add(new ValidadorDadosObrigatoriosCartao());
        lrnacc.add(new ValidadorCartaoPreferencial());
        rncc.put("alterar", lrnacc);

        rns.put(Cartao.class.getName(), rncc);

        //Entrada Estoque
        List<IStrategy> lrnsees = new ArrayList<>();
        lrnsees.add(new ComplementarDadosEntradaEstoque());
        lrnsees.add(new CalcularPrecoPeloCusto());
        lrnsees.add(new ValidadorPrecoEstoque());

        Map<String, List<IStrategy>> rnees = new HashMap<String, List<IStrategy>>();
        rnees.put("salvar", lrnsees);

        rns.put(Entrada.class.getName(), rnees);

        //PEDIDOS
        List<IStrategy> lrnsped = new ArrayList();
        lrnsped.add(new ValidadorInformacoesEntrega());
        lrnsped.add(new ValidadorInformacoesPagamento());
        lrnsped.add(new CalculadoraValorSubtotal());
        lrnsped.add(new CalculadoraValorTotal());
        lrnsped.add(new ValidadorUsoCupom());
        lrnsped.add(new ValidadorPagamentoCartao());
        lrnsped.add(new CalculadoraPagamentoCartao());
        lrnsped.add(new ComplementarDadosPedido());
        lrnsped.add(new TrocarStatusPedido());
        lrnsped.add(new ValidarCupomPromocional());
        lrnsped.add(new DarBaixaCupomTroca());
        lrnsped.add(new GeradorCupomPromocional());
        lrnsped.add(new GeradorCupomTroca());
        lrnsped.add(new DarBaixaEstoque());

        Map<String, List<IStrategy>> rnped = new HashMap<String, List<IStrategy>>();
        rnped.put("salvar", lrnsped);

        List<IStrategy> lrnaped = new ArrayList();
        lrnaped.add(new ValidadorTrocaPedido());
        lrnaped.add(new TrocarStatusPedido());
        lrnaped.add(new RetornarItemEstoque());
        //lrnaped.add(new TrocarStatusPedido());

        rnped.put("alterar", lrnaped);

        rns.put(Pedido.class.getName(), rnped);

        //ITENS PEDIDO
        List<IStrategy> lrnsic = new ArrayList();
        lrnsic.add(new ValidadorQtdItensCarrinho());

        Map<String, List<IStrategy>> lrnic = new HashMap<String, List<IStrategy>>();
        lrnic.put("salvar", lrnsic);

        rns.put(ItemCarrinho.class.getName(), lrnic);

        //CUPOM
        List<IStrategy> lrnscup = new ArrayList();
        lrnscup.add(new ValidadorExistenciaCupom());

        List<IStrategy> lrnecup = new ArrayList();
        lrnecup.add(new ExcluirCupom());

        Map<String, List<IStrategy>> lrncup = new HashMap<String, List<IStrategy>>();
        lrncup.put("salvar", lrnscup);
        lrncup.put("excluir", lrnecup);

        rns.put(Cupom.class.getName(), lrncup);
        
        //INATIVACAOLIVRO
        List<IStrategy> lrnsi = new ArrayList();
        lrnsi.add(new ComplementarDadosInativacaoLivro());
        
        Map<String, List<IStrategy>> lrni = new HashMap<String, List<IStrategy>>();
        lrni.put("salvar", lrnsi);
        
        rns.put(Inativacao.class.getName(), lrni);
        
        //ATIVACAO LIVRO
        List<IStrategy> lrnsa = new ArrayList();
        lrnsa.add(new ComplementarDadosAtivacaoLivro());
        
        Map<String, List<IStrategy>> lrna = new HashMap<String, List<IStrategy>>();
        lrna.put("salvar", lrnsa);
        
        rns.put(Ativacao.class.getName() , lrna);
        
        //CUPONSPROMOCIONAIS
        
        
        //Admnistradores
        List<IStrategy> lrnsadm = new ArrayList();
        lrnsadm.add(new ValidadorDadosObrigatoriosAdministrador());
        lrnsadm.add(new ValidadorExistenciaEmail());
        lrnsadm.add(new ComplementarDadosCadastroAdministrador());
        
        Map<String, List<IStrategy>> rnadm = new HashMap<String, List<IStrategy>>();
        rnadm.put("salvar", lrnsadm);
        rns.put(Administrador.class.getName(), rnadm);
        
        //Bandeira
        List<IStrategy> lrnsb = new ArrayList();
        lrnsb.add(new ComplementarDadosCadastroBandeira());
        
        Map<String, List<IStrategy>> rnban = new HashMap<String, List<IStrategy>>();
        rnban.put("salvar", lrnsb);
        
        rns.put(Bandeira.class.getName(), rnban);        
        
        //Carrinho
        List<IStrategy> lrncar = new ArrayList();
        lrncar.add(new ValidadorQuantidadeItensCarrinho());
        
        Map<String, List<IStrategy>> rncar = new HashMap<String, List<IStrategy>>();
        rncar.put("salvar", lrncar);
        
        rns.put(Carrinho.class.getName(), rncar);
        
        
    }

    @Override
    public Resultado consultar(EntidadeDominio entidadeDominio) {

        String msg = executarRegras(entidadeDominio, "consultar");

        Resultado resultado = new Resultado();

        if (msg == null) {

            IDAO dao = FactoryDAO.factory(entidadeDominio.getClass().getName());

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

        String msg = executarRegras(entidadeDominio, "salvar");

        Resultado resultado = new Resultado();

        resultado.setEntidades(new ArrayList());
        resultado.getEntidades().add(entidadeDominio);

        if (msg == null) {

            IDAO dao = FactoryDAO.factory(entidadeDominio.getClass().getName());

            if (dao != null) {

                try {
                    resultado.setEntidades(new ArrayList());
                    resultado.getEntidades().add(dao.salvar(entidadeDominio));

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }//If

        resultado.setResultados(msg);

        return resultado;

    }

    @Override
    public Resultado alterar(EntidadeDominio entidadeDominio) {

        String msg = executarRegras(entidadeDominio, "alterar");

        Resultado resultado = new Resultado();

        if (msg == null) {

            IDAO dao = FactoryDAO.factory(entidadeDominio.getClass().getName());

            if (dao != null) {
                try {
                    dao.alterar(entidadeDominio);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }

        }//If

        resultado.setResultados(msg);

        return resultado;

    }

    @Override
    public Resultado excluir(EntidadeDominio entidadeDominio) {

        String msg = executarRegras(entidadeDominio, "excluir");

        Resultado resultado = new Resultado();

        if (msg == null) {

            IDAO dao = FactoryDAO.factory(entidadeDominio.getClass().getName());

            if (dao != null) {
                try {
                    dao.excluir(entidadeDominio);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }//If

        resultado.setResultados(msg);

        return resultado;

    }

    private String executarRegras(EntidadeDominio entidadeDominio, String operacao) {

        String msg = "";

        Map<String, List<IStrategy>> regrasEntidade = rns.get(entidadeDominio.getClass().getName());

        if (regrasEntidade != null) {

            List<IStrategy> regras = regrasEntidade.get(operacao);

            if (regras != null) {

                for (IStrategy strategy : regras) {

                    String m = strategy.processar(entidadeDominio);
                    if (m != null) {
                        msg += m;

                        return msg;
                    }

                }

            }

        }

        if (msg.isEmpty()) {
            return null;
        } else {
            return msg;
        }

    }
}
