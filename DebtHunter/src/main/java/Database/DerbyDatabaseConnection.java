
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDatabaseConnection {
    private static final String DRIVER="org.apache.derby.jdbc.ClientDriver";
    private static final String URL="jdbc:derby://localhost:1527/TestDebt";
    private static final String USERNAME="app";
    private static final String PASSWORD="123";
            
    public static Connection getConn() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        Connection conn = DriverManager.getConnection(URL, USERNAME,PASSWORD);
        return conn;
    }
}
