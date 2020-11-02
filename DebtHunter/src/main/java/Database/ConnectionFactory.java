/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GuideKai
 */
public class ConnectionFactory {
    private static final String URL="jdbc:derby://localhost:1527/TestDebt";
    private static final String USERNAME="app";
    private static final String PASSWORD="123";
    
     public static Connection getInstance() {
        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver") ;
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}
