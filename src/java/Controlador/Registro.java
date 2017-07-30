/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Funciones;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

/**
 *
 * @author pepo
 */
public class Registro extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
                Funciones f = new Funciones();
                JSONArray json= new JSONArray();
                String roles[] = f.obtenerRoles();
                for(String rol: roles)
                    json.add(rol);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                            out.println(json);
            }
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
        //registrarUsuario(String pnombre, String snombre, String papellido, String sapellido, 
        //String cedula, String usuario, String clave, String fnacimiento, String correo, String direccion, String rol, String telefono) {
        Funciones f = new Funciones();
        int resultado = f.registrarUsuario(request.getParameter("primer_nombre"), request.getParameter("segundo_nombre"), request.getParameter("primer_apellido"),
                request.getParameter("segundo_apellido"), request.getParameter("cedula"), request.getParameter("usuario"), request.getParameter("clave"), request.getParameter("nacimiento"),
                request.getParameter("email"), request.getParameter("direccion"), request.getParameter("repeatSelect"), request.getParameter("telefono"));
        
        switch (resultado){
            // si devuelve 0, no hay error
            // si devuelve 1, usuario existe
            // si devuelve 2, cedula existe
            // si devuelve 3, cedula con formato incorrecto
            // si devuelve 4, correo existe
            // si devuelve 5, correo con formato incorrecto
            // si devuelve 6, telefono con formato incorrecto
            case 0: 
                request.getSession().removeAttribute("errorMessage");
                request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);
                break;
            case 1: 
                request.getSession().setAttribute("errorMessage", "El usuario ingresado ya existe");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case 2:  
                request.getSession().setAttribute("errorMessage", "La cedula ingresada ya existe");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case 3:  
                request.getSession().setAttribute("errorMessage", "La cedula tiene un formato incorrecto");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case 4:
                request.getSession().setAttribute("errorMessage", "El correo ingresado ya existe");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case 5: 
                request.getSession().setAttribute("errorMessage", "El correo tiene un formato incorrecto");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
            case 6: 
                request.getSession().setAttribute("errorMessage", "El telefono tiene un formato incorrecto");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
                break;
        }
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
