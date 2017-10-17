/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import negocio.UsuarioController;
import org.json.JSONException;
import org.json.JSONObject;
import util.DBHelper;

/**
 *
 * @author Cristofer
 */
@WebServlet(name = "LOGIN_CONTROLLER", urlPatterns = {"/LOGIN_CONTROLLER"})
public class LOGIN_CONTROLLER extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            HttpSession sesion = request.getSession();
            String email, pass;
            email = request.getParameter("email");
            pass = request.getParameter("password");
            JSONObject resp = UsuarioController.validarUsuario(email, pass);
            //deberíamos buscar el usuario en la base de datos, pero dado que se escapa de este tema, ponemos un ejemplo en el mismo código
            if (resp.getBoolean("res")) {
                //si coincide usuario y password y además no hay sesión iniciada
                sesion.setAttribute("usuario", resp.getInt("id"));
                sesion.setAttribute("email", resp.getString("email"));
                //redirijo a página con información de login exitoso
                response.sendRedirect("main.html");
            } else {
                response.sendRedirect("index.html");

            }
        } catch (JSONException ex) {
            Logger.getLogger(LOGIN_CONTROLLER.class.getName()).log(Level.SEVERE, null, ex);
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
