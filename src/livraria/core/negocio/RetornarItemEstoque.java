package livraria.core.negocio;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOEntradaEstoque;
import livraria.core.dao.DAOLivro;
import livraria.core.dao.DAOPedido;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Entrada;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.ItemEstoque;
import livraria.dominio.Livro;
import livraria.dominio.Pagamento;
import livraria.dominio.Pedido;
import livraria.dominio.Status;

public class RetornarItemEstoque implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        
        Pedido p = (Pedido) entidadeDominio;
        
        if(p.getStatuses() == null)
            return null;
        
                
        for(Status s : p.getStatuses()){
        
            if(s.getNome().equals("Trocado")){
                                
                for(ItemCarrinho ic : p.getCarrinho().getItens()){
                    
                    Livro l = (Livro) ic.getProduto();
                    
                    if(l.getStatus().equals("retornar")){
                        
                        Entrada e = new Entrada();
                        e.setData(new Date());
                        e.setFornecedor(null);
                        
                        Livro lb = new Livro();
                        lb.setId(l.getId());
                        ic.setStatus("Em Troca");
                        DAOLivro dl = new DAOLivro();
                        DAOEntradaEstoque dee = new DAOEntradaEstoque();                        
                        
                        try {
                            
                            lb = (Livro) ((List<EntidadeDominio>) dl.consultar(l)).get(0);                        
                            
                            System.out.println("anter" + lb.getItemEstoque().getQuantidade());
                            
                            lb.getItemEstoque().setQuantidade(lb.getItemEstoque().getQuantidade() + ic.getQuantidade());
                            
                            System.out.println(ic.getQuantidade() + " qtst" + lb.getItemEstoque().getQuantidade());
                            
                            lb.setCapa(null);
                            
                            e.setItemEstoque(new ItemEstoque());
                            e.getItemEstoque().setProduto(lb);
                            e.setQuantidade(ic.getQuantidade());
                            e.setPreco(lb.getPreco());
                            e.setCusto(lb.getPreco());
                                          
                            dl.alterar(lb);                            
                            dee.salvar(e);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(RetornarItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                        }       
                    }else{
                        ic.setStatus("Em processamento");
                    }
                    
                }
                
                DAOPedido daoPed = new DAOPedido();
                
                try {
                    Pedido pBuffer = (Pedido) ((List<EntidadeDominio>) daoPed.consultar(p)).get(0);
                    
                    p.setCliente(pBuffer.getCliente());
                    p.setValorTotal(pBuffer.getValorTotal() * -1);
                    
                    //pedido.isAtivo() && pedido.getPagamento().getCartoes().isEmpty() && (pedido.isTroca() || pedido.getValorTotal() < 0) 
                    
                    p.setPagamento(new Pagamento());
                    p.getPagamento().setCartoes(new ArrayList());
                    p.setAtivo(true);
                    p.setTroca(true);
                    
                    IStrategy gct = new GeradorCupomTroca();
                    gct.processar(p);
                    
                    
                } catch (SQLException ex) {
                    Logger.getLogger(RetornarItemEstoque.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }
                        
        }
        
        return null;
        
    }
    
    
    
}
