/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author GuideKai
 */
public class User {
    String email ;
    String password ;
    String firstname ;
    String lastname ;
    String tel ;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getTel() {
        return tel;
    }
    
    @Override
    public String toString() {
        return "User{" + "email=" + email + ", password=" + password + '}';
    }
    
}
