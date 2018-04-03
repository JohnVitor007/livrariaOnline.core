//Classe de conexão com banco de dados
//@author John Vitor da Silva Quispe
//@date 19/03/2018
package livraria.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    
    public static Connection getConnection() throws SQLException, ClassNotFoundException
    {
        
        String connectionString = "jdbc:postgresql://localhost:5432/livraria";
        String user = "postgres";
        String pass = "94769546";
        
        String driver = "org.postgresql.Driver";
        
        Class.forName(driver);
        
        Connection connection = null;
        
        connection = DriverManager.getConnection(connectionString, user, pass);
            
        return connection;
        
    }   
    
}
