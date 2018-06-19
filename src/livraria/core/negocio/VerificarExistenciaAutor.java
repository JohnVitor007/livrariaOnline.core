package livraria.core.negocio;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IStrategy;
import livraria.core.dao.DAOAutor;
import livraria.dominio.Autor;
import livraria.dominio.EntidadeDominio;
import livraria.dominio.Livro;

public class VerificarExistenciaAutor implements IStrategy {

    @Override
    public String processar(EntidadeDominio entidadeDominio) {

        Livro livro = (Livro) entidadeDominio;

        DAOAutor da = new DAOAutor();

        if(livro.getCapa() != null)
            return null;
        
        for (Autor a : livro.getAutor()) {

            List<EntidadeDominio> eds;
            try {
                eds = (List<EntidadeDominio>) da.consultar(a);

                if (!eds.isEmpty()) {

                    a.setId(((Autor) eds.get(0)).getId());
                }

            } catch (SQLException ex) {
                Logger.getLogger(VerificarExistenciaAutor.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

}
