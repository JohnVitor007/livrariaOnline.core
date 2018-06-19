package livraria.core.negocio;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOSaidaEstoque;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;
import livraria.dominio.Pedido;
import livraria.dominio.Saida;

public class DarBaixaEstoque implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Pedido pedido = (Pedido) entidadeDominio;

        if (pedido.isAtivo()) {

            DAOSaidaEstoque se = new DAOSaidaEstoque();

            Saida s;

            for (ItemCarrinho ic : pedido.getCarrinho().getItens()) {

                try {
                    s = new Saida();
                    
                    ic.getProduto().getItemEstoque().setQuantidade(ic.getProduto().getItemEstoque().getQuantidade() - ic.getQuantidade());
                    Livro l = (Livro) ic.getProduto();
                    l.setCapa(null);
                    
                    System.out.println(l.getCategoria().size() + " tamanho");
                    
                    l.setQuantidadeVendida(l.getQuantidadeVendida() + ic.getQuantidade());                    
                    
                    s.setData(new Date());
                    s.setPedido(pedido);
                    s.setProduto(ic.getProduto());
                    s.setQuantidade(ic.getQuantidade());

                    se.salvar(s);
                } catch (SQLException ex) {
                    Logger.getLogger(DarBaixaEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        return null;

    }

}
