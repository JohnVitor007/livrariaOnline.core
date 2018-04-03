//Classe Abstrata de conexão com o JDBC
//@author John Vitor da Silva Quispe
//@date 20/03/2018


package livraria.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import livraria.core.IDAO;
import livraria.core.util.DataBaseConnection;

public abstract class AbstractJDBCDAO implements IDAO {
    
    protected Connection connection;
    protected boolean ctrlTransaction = true;
        
    protected void openConnection(){
            
        try {
            if(connection == null || connection.isClosed())
                connection = DataBaseConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
                 
    }
    
}
