package livraria.core.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import livraria.dominio.Carrinho;
import livraria.dominio.Cartao;
import livraria.dominio.Cliente;
import livraria.dominio.CupomPromocional;
import livraria.dominio.CupomTroca;
import livraria.dominio.EnderecoEntrega;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Frete;
import livraria.dominio.ItemCarrinho;
import livraria.dominio.Livro;
import livraria.dominio.Pagamento;
import livraria.dominio.Pedido;
import livraria.dominio.Status;
import livraria.dominio.TipoFrete;

public class DAOPedido extends AbstractJDBCDAO {

    @Override
    public Collection<EntidadeDominio> consultar(EntidadeDominio entidadeDominio) throws SQLException {

        Pedido pedido = (Pedido) entidadeDominio;
        List<EntidadeDominio> pedidos = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("select ped_id_pedido, pcl_id_cliente, ped_valor_total, ped_data_pedido, ped_ativo, ped_cancelado from pedidos ped ");
        sql.append("join pedidos_clientes pcl on ped.ped_id_pedido = pcl.pcl_id_pedido");

        /*
        sql.append("select ped_id_pedido, ped_valor_total, ped_data_pedido, ped_ativo, pee_id_endereco_entrega ");
        sql.append("from pedidos p join pedidos_enderecos_entrega pee on p.ped_id_pedido = pee.pee_id_pedido ");
        sql.append("join pedidos_clientes pcl on p.ped_id_pedido = pcl.pcl_id_pedido");
         */
        if (pedido.getId() != 0) {
            sql.append(" where ped_id_pedido = ?");
        } else if (pedido.getCliente() != null) {
            sql.append(" where pcl.pcl_id_cliente = ?");
        }
        
        sql.append(" order by ped_id_pedido");

        this.openConnection();

        PreparedStatement pst = connection.prepareStatement(sql.toString());

        if (pedido.getId() != 0) {
            pst.setObject(1, pedido.getId());
        } else if (pedido.getCliente() != null) {
            pst.setObject(1, pedido.getCliente().getId());
        }

        ResultSet rs = pst.executeQuery();

        Pedido pBuffer;

        while (rs.next()) {

            pBuffer = new Pedido();
            pBuffer.setId(rs.getInt("ped_id_pedido"));
            pBuffer.setValorTotal((rs.getDouble("ped_valor_total")));
            pBuffer.setAtivo(rs.getBoolean("ped_ativo"));
            pBuffer.setData(rs.getDate("ped_data_pedido"));
            pBuffer.setCliente(new Cliente());
            pBuffer.getCliente().setId(rs.getInt("pcl_id_cliente"));
            pBuffer.setCancelado(rs.getBoolean("ped_cancelado"));
            
            //Clientes
            DAOCliente dc = new DAOCliente();
            List<EntidadeDominio> eCli = (List<EntidadeDominio>) dc.consultar(pBuffer.getCliente());
            pBuffer.setCliente((Cliente) eCli.get(0));

            //Fretes
            sql.setLength(0);
            sql.append("select fre_valor_frete, fre_tipo_frete from fretes where fre_id_pedido = ?");
            pst = connection.prepareStatement(sql.toString());
            pst.setObject(1, pedido.getId());

            ResultSet rsFrete = pst.executeQuery();

            pBuffer.setFrete(new Frete());

            if (rsFrete.next()) {

                pBuffer.getFrete().setTipoFrete(new TipoFrete());
                pBuffer.getFrete().setValor(rsFrete.getDouble("fre_valor_frete"));
                pBuffer.getFrete().getTipoFrete().setNome(rsFrete.getString("fre_tipo_frete"));

            }

            //Enderecos Entrega
            pBuffer.getFrete().setEnderecoEntrega(new EnderecoEntrega());
            sql.setLength(0);
            sql.append("select pee_id_endereco_entrega from pedidos_enderecos_entrega where pee_id_pedido = ?");
            pst = connection.prepareStatement(sql.toString());
            pst.setObject(1, pBuffer.getId());
            ResultSet rsEe = pst.executeQuery();
            if (rsEe.next()) {
                pBuffer.getFrete().getEnderecoEntrega().setId(rsEe.getInt("pee_id_endereco_entrega"));
            }

            if (pBuffer.getFrete().getEnderecoEntrega().getId() != 0) {
                DAOEnderecoEntrega dee = new DAOEnderecoEntrega();
                pBuffer.getFrete().setEnderecoEntrega((EnderecoEntrega) ((List<EntidadeDominio>) (dee.consultar(pBuffer.getFrete().getEnderecoEntrega()))).get(0));
            }

            //Pagamento           
            pBuffer.setPagamento(new Pagamento());
            pBuffer.getPagamento().setCartoes(new ArrayList());
            sql.setLength(0);
            sql.append("select sp_data, sp_id_pedido, sp_hora, sp_nome from status_pedido where sp_id_pedido = ?");

            pBuffer.setStatuses(new ArrayList());

            Status s;

            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, pBuffer.getId());

            ResultSet rsStatus = pst.executeQuery();

            while (rsStatus.next()) {

                s = new Status();
                s.setData(rsStatus.getDate("sp_data"));
                s.setHora(rsStatus.getString("sp_hora"));
                s.setNome(rsStatus.getString("sp_nome"));

                pBuffer.getStatuses().add(s);

            }

            sql.setLength(0);
            sql.append("select pc_id_cartao, pc_valor_pago from pedidos_cartoes where pc_id_pedido = ?");

            pst = connection.prepareStatement(sql.toString());
            pst.setObject(1, pBuffer.getId());
            ResultSet rsCartao = pst.executeQuery();
            Cartao cartao;

            while (rsCartao.next()) {

                cartao = new Cartao();

                cartao.setId(rsCartao.getInt("pc_id_cartao"));

                DAOCartao dcli = new DAOCartao();

                List<EntidadeDominio> eCar = (List<EntidadeDominio>) dcli.consultar(cartao);

                cartao = (Cartao) eCar.get(0);
                cartao.setValorPago(rsCartao.getDouble("pc_valor_pago"));

                pBuffer.getPagamento().getCartoes().add(cartao);

            }

            Carrinho carrinho = new Carrinho();
            carrinho.setItens(new ArrayList());

            sql.setLength(0);
            sql.append("select ip_id_livro, ip_quantidade, ip_valor_unitario, ip_valor_total, ip_status ");
            sql.append("from itens_pedidos where ip_id_pedido = ?");

            pst = connection.prepareStatement(sql.toString());

            pst.setInt(1, pBuffer.getId());

            ResultSet rsItens = pst.executeQuery();

            ItemCarrinho ic;

            while (rsItens.next()) {

                ic = new ItemCarrinho();

                ic.setQuantidade(rsItens.getInt("ip_quantidade"));
                ic.setStatus(rsItens.getString("ip_status"));
                ic.setValorSubtotal(rsItens.getDouble("ip_valor_total"));

                Livro l = new Livro();
                l.setId(rsItens.getInt("ip_id_livro"));

                DAOLivro dl = new DAOLivro();

                List<EntidadeDominio> livros = (List<EntidadeDominio>) dl.consultar(l);

                ic.setProduto((Livro) livros.get(0));
                ic.getProduto().setPreco(rsItens.getDouble("ip_valor_unitario"));

                carrinho.getItens().add(ic);

            }

            pBuffer.setCarrinho(carrinho);

            sql.setLength(0);
            sql.append("select pcp_id_cupom_promocional, pcp_valor_pago from pedidos_cupons_promocionais where pcp_id_pedido = ?");

            pst = connection.prepareStatement(sql.toString());

            pst.setInt(1, pBuffer.getId());

            ResultSet rsCp = pst.executeQuery();

            if (rsCp.next()) {

                CupomPromocional cp = new CupomPromocional();
                cp.setId(rsCp.getInt("pcp_id_cupom_promocional"));

                DAOCupomPromocional dcp = new DAOCupomPromocional();

                List<EntidadeDominio> eds = (List<EntidadeDominio>) dcp.consultar(cp);

                CupomPromocional bCp = new CupomPromocional();
                bCp = (CupomPromocional) eds.get(0);
                bCp.setValorPago(rsCp.getDouble("pcp_valor_pago"));
                pBuffer.getPagamento().setCupomPromocional(bCp);

            }
            
            sql.setLength(0);
            sql.append("select ct.ct_identificacao, ct.ct_id_cupom_troca, p.pct_id_pedido, p.pct_valor_pago from pedidos_cupons_troca p join cupons_troca ct on pct_id_cupom_troca = ct.ct_id_cupom_troca where p.pct_id_pedido = ?");

            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, pBuffer.getId());
            
            ResultSet rsCt = pst.executeQuery();
            
            pBuffer.getPagamento().setCuponsTroca(new ArrayList());
            
            CupomTroca ct;
            
            while(rsCt.next()){
                
                ct = new CupomTroca();
                ct.setId(rsCt.getInt("ct_id_cupom_troca"));
                ct.setIdentificador(rsCt.getString("ct_identificacao"));
                ct.setValorPago(rsCt.getDouble("pct_valor_pago"));
                pBuffer.getPagamento().getCuponsTroca().add(ct);
                
            }
            
            pedidos.add(pBuffer);

        }

        connection.close();

        return pedidos;

    }

    @Override
    public EntidadeDominio salvar(EntidadeDominio entidadeDominio) throws SQLException {

        Pedido pedido = (Pedido) entidadeDominio;

        StringBuilder sql = new StringBuilder();

        sql.append("insert into pedidos(ped_valor_total, ped_data_pedido, ped_ativo, ped_cancelado)values(?,?,?,?)");

        this.openConnection();

        PreparedStatement pst = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

        pst.setObject(1, pedido.getValorTotal());
        pst.setObject(2, new java.sql.Date(pedido.getData().getTime()));
        pst.setObject(3, pedido.isAtivo());
        pst.setObject(4, pedido.isCancelado());
                

        pst.execute();

        ResultSet rs = pst.getGeneratedKeys();

        if (rs.next()) {
            pedido.setId(rs.getInt(1));
        }

        sql.setLength(0);
        sql.append("insert into itens_pedidos (ip_id_pedido, ip_id_livro, ip_quantidade, ip_status, ip_valor_unitario, ip_valor_total) ");
        sql.append("values(?,?,?,?,?,?)");

        pst = connection.prepareStatement(sql.toString());

        for (ItemCarrinho ic : pedido.getCarrinho().getItens()) {

            pst.setObject(1, pedido.getId());
            pst.setObject(2, ic.getProduto().getId());
            pst.setObject(3, ic.getQuantidade());
            pst.setObject(4, ic.getStatus());
            pst.setObject(5, ic.getProduto().getPreco());
            pst.setObject(6, ic.getValorSubtotal());

            pst.execute();

        }

        if (pedido.getFrete() != null) {
            sql.setLength(0);
            sql.append("insert into fretes(fre_id_pedido, fre_tipo_frete, fre_valor_frete)values(?,?,?)");

            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, pedido.getId());
            pst.setObject(2, pedido.getFrete().getTipoFrete().getNome());
            pst.setObject(3, pedido.getFrete().getValor());

            pst.execute();

            sql.setLength(0);
            sql.append("insert into pedidos_enderecos_entrega(pee_id_pedido, pee_id_endereco_entrega)values(?,?)");

            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, pedido.getId());
            pst.setObject(2, pedido.getFrete().getEnderecoEntrega().getId());

            pst.execute();

        }

        if (pedido.getPagamento() != null) {
            sql.setLength(0);
            sql.append("insert into pedidos_cartoes(pc_id_cartao, pc_id_pedido, pc_valor_pago)values(?,?,?)");

            pst = connection.prepareStatement(sql.toString());

            for (Cartao cartao : pedido.getPagamento().getCartoes()) {
                pst.setObject(1, cartao.getId());
                pst.setObject(2, pedido.getId());
                pst.setObject(3, cartao.getValorPago());
                pst.execute();
            }

            if (pedido.getPagamento().getCupomPromocional() != null) {

                sql.setLength(0);
                sql.append("insert into pedidos_cupons_promocionais(pcp_id_cupom_promocional, pcp_id_pedido, pcp_valor_pago)values(?,?,?)");

                pst = connection.prepareStatement(sql.toString());

                pst.setObject(1, pedido.getPagamento().getCupomPromocional().getId());
                pst.setObject(2, pedido.getId());
                pst.setObject(3, pedido.getPagamento().getCupomPromocional().getValorPago());

                pst.execute();

            }

            if (pedido.getPagamento().getCuponsTroca() != null) {

                for (CupomTroca ct : pedido.getPagamento().getCuponsTroca()) {

                    sql.setLength(0);
                    sql.append("insert into pedidos_cupons_troca(pct_id_cupom_troca, pct_id_pedido, pct_valor_pago)values(?,?,?)");

                    pst = connection.prepareStatement(sql.toString());

                    pst.setObject(1, ct.getId());
                    pst.setObject(2, pedido.getId());
                    pst.setObject(3, ct.getValorPago());

                    pst.execute();

                }
            }

        }

        sql.setLength(0);
        sql.append("insert into pedidos_clientes(pcl_id_cliente, pcl_id_pedido) values(?,?)");

        pst = connection.prepareStatement(sql.toString());

        pst.setObject(1, pedido.getCliente().getId());
        pst.setObject(2, pedido.getId());

        pst.execute();

        sql.setLength(0);
        sql.append("insert into status_pedido(sp_nome, sp_data, sp_hora, sp_id_pedido)values(?,?,?,?)");

        for (Status s : pedido.getStatuses()) {

            pst = connection.prepareStatement(sql.toString());

            pst.setObject(1, s.getNome());
            pst.setObject(2, new java.sql.Date(s.getData().getTime()));
            pst.setObject(3, s.getHora());
            pst.setObject(4, pedido.getId());

            pst.execute();

        }

        connection.close();

        return pedido;

    }

    @Override
    public void alterar(EntidadeDominio entidadeDominio) throws SQLException {

        Pedido pedido = (Pedido) entidadeDominio;

        StringBuilder sql = new StringBuilder();
        
        sql.append("update pedidos set ped_ativo = ?, ped_cancelado = ? where ped_id_pedido = ?");

        this.openConnection();

        PreparedStatement pst = connection.prepareStatement(sql.toString());

        pst.setObject(1, pedido.isAtivo());
        pst.setObject(2, pedido.isCancelado());
        pst.setObject(3, pedido.getId());

        pst.execute();

        if (pedido.getStatuses() != null) {

            sql.setLength(0);

            sql.append("insert into status_pedido(sp_data, sp_id_pedido, sp_hora, sp_nome)values(?,?,?,?)");

            pst = connection.prepareStatement(sql.toString());

            Status s = pedido.getStatuses().get(pedido.getStatuses().size() - 1);

            pst.setObject(1, new java.sql.Date(s.getData().getTime()));
            pst.setObject(2, pedido.getId());
            pst.setObject(3, s.getHora());
            pst.setObject(4, s.getNome());

            pst.execute();

        }

        if(pedido.getCarrinho() != null){
            
            sql.setLength(0);
            
            sql.append("update itens_pedidos set ip_status = ? where ip_id_pedido = ? and ip_id_livro = ?");
            
            for(ItemCarrinho ic : pedido.getCarrinho().getItens()){
            
                pst = connection.prepareStatement(sql.toString());
                
                pst.setObject(1, ic.getProduto().getStatus());
                pst.setObject(2, pedido.getId());
                pst.setObject(3, ic.getProduto().getId());
                
                pst.execute();
                
            }
                       
        }

        connection.close();

    }

    @Override
    public void excluir(EntidadeDominio entidadeDominio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
