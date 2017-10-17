/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import negocio.AnuncioController;
import org.json.JSONException;

/**
 *
 * @author Cristofer
 */
@MultipartConfig
@WebServlet(name = "REGISTRAR_ANUNCIO", urlPatterns = {"/REGISTRAR_ANUNCIO"})
public class REGISTRAR_ANUNCIO extends HttpServlet {

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

    private String registrarPubicacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, JSONException {
        HttpSession sesion = request.getSession();
        int usuario = 0;
        try {
            usuario = (int) sesion.getAttribute("usuario");
        } catch (Exception e) {
            response.sendRedirect("index.html");
        }
        String nombre_c = request.getParameter("nombre_c");
        String apellido_c = request.getParameter("apellido_c");
        String sexo_c = request.getParameter("sexo_c");
        String parentesco_c = request.getParameter("parentesco_c");
        int telefono_c = 0;
        try {
            telefono_c = Integer.parseInt(request.getParameter("telefono_c"));
        } catch (Exception e) {
        }

        String email_c = request.getParameter("email_c");
        String nombre_d = request.getParameter("nombre_d");
        String apellido_d = request.getParameter("apellido_d");
        int edad_d = 0;
        try {
            edad_d = Integer.parseInt(request.getParameter("edad_d"));
        } catch (Exception e) {
        }
        String sexo_d = request.getParameter("sexo_d");
        int estatura_d = 0;
        try {
            estatura_d = Integer.parseInt(request.getParameter("estatura_d"));
        } catch (Exception e) {
        }
        String colorpelo_d = request.getParameter("colorpelo_d");
        String colorpiel_d = request.getParameter("colorpiel_d");
        String constitucion_d = request.getParameter("constitucion_d");
        String otros_d = request.getParameter("constitucion_d");
        Part part = request.getPart("imagen");
        String nombre_imagen = null;
        if (part != null) {
            if (AnuncioController.guardarImagen(part.getInputStream(), nombre_imagen)) {
                nombre_imagen = part.getSubmittedFileName();
            }
        }
        return AnuncioController.anunciar(nombre_c, apellido_c, sexo_c, parentesco_c, telefono_c, email_c, nombre_d, apellido_d, edad_d, sexo_d, estatura_d, colorpelo_d, colorpiel_d, constitucion_d, otros_d, nombre_imagen, usuario).toString();
    }

}
