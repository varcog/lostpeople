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

/**
 *
 * @author Cristofer
 */
@WebServlet(name = "DESAPARECIDO_CONTROLLER", urlPatterns = {"/DESAPARECIDO_CONTROLLER"})
public class DESAPARECIDO_CONTROLLER extends HttpServlet {

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

            String evento = request.getParameter("evento");
            String resp;
            switch (evento) {
                case "REGISTRAR_USUARIO":
                    resp = registrarUsuario(request, response);
                    break;
                case "REGISTRAR_PUBLICACION":
                    resp = registrarPubicacion(request, response);
                    break;
                case "LISTAR_PUBLICACIONES":
                    resp = "";
                    break;
                case "VER_PUBLICACION":
                    resp = "";
                    break;
                case "COMENTAR":
                    resp = "";
                    break;
                default:
                    resp = "false";
            }

            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println(resp);
            }
        } catch (JSONException ex) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                /* TODO output your page here. You may use following sample code. */
                out.println("false");
            }
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

    private String registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws JSONException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String sexo = request.getParameter("sexo");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        String estado = request.getParameter("estado");
        return UsuarioController.registrarUsuario(nombre, apellido, sexo, email, pass, estado).toString();
    }

    private String registrarPubicacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession();
        try {
            int usuario = (int) sesion.getAttribute("usuario");
        } catch (Exception e) {
            response.sendRedirect("index.html");
        }
        
        return "";
    }

}
