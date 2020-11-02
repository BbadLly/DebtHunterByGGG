/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Database.DerbyDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author GuideKai
 */
public class UserDAO implements Database.DAO {
    List<User> list = new ArrayList() ;

    public int add(User u) {
        int row=0;
        String sql="INSERT INTO USER VALUES(?,?)";
        try(Connection conn=DerbyDatabaseConnection.getConn();
                PreparedStatement stm=conn.prepareStatement(sql)){
            stm.setString(1, u.email);
            stm.setString(2, u.getPassword());
            stm.setString(3, u.getFirstname());
            stm.setString(4, u.getLastname());
            stm.setString(5, u.getTel());
            stm.executeUpdate();
//            row=stm.executeUpdate("INSERT INTO STUDENT VALUES("+std.getId()+",'"+std.getName()+"')");
            
        }catch (ClassNotFoundException ex) {
            System.out.println("Cannot add: "+ex.getMessage());
        } catch (SQLException ex) {
            System.out.println("Cannot add: "+ex.getMessage());
        }
        return row;
    }
    
}
