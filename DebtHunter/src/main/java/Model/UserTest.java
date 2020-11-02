/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

/**
 *
 * @author GuideKai
 */
public class UserTest {
    public static void main(String[] args) {
        List<User> u = new ArrayList();
        User u1 = new User("admin@mail", "123") ;
        User u2 = new User("admin2@mail", "123") ;
        u.add(u1) ;
        u.add(u2) ;
    }
    
}
