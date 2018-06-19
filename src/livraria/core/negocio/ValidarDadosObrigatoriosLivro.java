//Classe para validar dados obrigatorios do cadastro de livro
//@author John Vitor da Silva Quispe
//@date 19/03/2018

package livraria.core.negocio;

import java.util.Collection;
import livraria.core.IStrategy;
import livraria.dominio.Autor;
import livraria.dominio.Categoria;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;

public class ValidarDadosObrigatoriosLivro implements IStrategy{

    @Override
    public String processar(EntidadeDominio entidadeDominio) {
        
        Livro l = (Livro) entidadeDominio;
        
        StringBuilder sb = new StringBuilder();
        
        if(l.getCapa() != null)
            return null;
        
        if(l.getTitulo().equals("")){
            sb.append("Insira o título do livro<br>");   
        }
        
        Collection<Autor> autores = l.getAutor();
        
        for(Autor autor: autores){
            if(autor.getId() == 0 && autor.getNome().equals(""))
                sb.append("Insira as informações dos autor(es) corretamente<br>");
        }
        
        if(l.getEditora().getId() == 0 && l.getEditora().equals("")){
            sb.append("Insira a informação da editora corretamente<br>");
        }
        
        Collection<Categoria> categorias = l.getCategoria();
        
        for(Categoria categoria : categorias){
            if(categoria.getId() == 0 && categoria.getNome().equals(""))
                sb.append("Selecione ao menos uma categoria<br>");
        }
        
        if(l.getEdicao().getAno() == 0){
            sb.append("Insira o ano corretamente<br>");
        }
        
        if(l.getEdicao().getNumero() == 0){
            sb.append("Insira o número da edição do livro<br>");
        }
        
        if(l.getEdicao().getNumeroPaginas() == 0){
            sb.append("Insira o número de páginas<br>");
        }
        
        if(l.getEdicao().getDimensoes().getAltura() == 0){
            sb.append("Insira a altura<br>");
        }
               
        if(l.getEdicao().getDimensoes().getPeso().getQuantidade() == 0){
            sb.append("Insira a peso<br>");
        }
            
        if(l.getItemEstoque().getGrupoPrecificacao().getId() == 0)
            sb.append("Selecione o grupo de precificação<br>");
        
        if(l.getCodigoBarras().equals(""))
            sb.append("Insira o código de barras<br>");
        
        if(l.getSinopse().equals(""))
            sb.append("Insira a sinopse<br>");
           
        if(l.getEdicao().getNumero() == 0)
            sb.append("Insira a edição do livro<br>");
        
        if(l.getEdicao().getDimensoes().getAltura() == 0)
            sb.append("Insira a altura do livro<br>");
        
        if(l.getEdicao().getDimensoes().getLargura() == 0)
            sb.append("Insira a largura o livro<br>");
        
        if(l.getEdicao().getDimensoes().getPeso().getQuantidade()== 0)
            sb.append("Insira o peso do livro<br>");
               
        
        if(sb.length() > 0)
            return sb.toString();
        else
            return null;
                
        
    }
    
}
