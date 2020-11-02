/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Entity.Users;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author GuideKai
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/Main"})
public class MainServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_DebtHunter_war_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();
        String userName = request.getParameter("email");
        String password = request.getParameter("password");
//        int i = Integer.parseInt(userName) ;
        Users u = em.createQuery("SELECT u from Users u WHERE u.email = :email", Users.class)
                .setParameter("email", userName).getSingleResult() ;
//        UsersJpaController uc =  new UsersJpaController(emf) ;
//        Users u = uc.findUsersByEmail(userName);  
//        System.out.println(u.toString());
        if (u != null && u.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", u);
//            request.setAttribute("fname", u.getFirstName);
//            request.setAttribute("lname", u.getLastName);
            Cookie c1 = new Cookie("USER_NAME", userName);
            c1.setMaxAge(60 * 60 * 24);
            response.addCookie(c1);
            request.getRequestDispatcher("/WEB-INF/Main.jsp").forward(request, response);
        }
        else {
            request.setAttribute("message", "Invalid Username or Password, Please try again.");
            request.getRequestDispatcher("/Login.jsp").forward(request, response);  
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
