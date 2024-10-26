/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import com.mycompany.redsocial.Controller.ControllerException;
import com.mycompany.redsocial.FacadeUsuario.IRedSocialFacade;
import com.mycompany.redsocial.Controller.UsuarioDTO;
import com.mycompany.redsocial.Entidad.Estado;
import com.mycompany.redsocial.Entidad.Municipio;
import com.mycompany.redsocial.Entidad.Usuario;
import com.mycompany.redsocial.FacadeUsuario.RedSocialFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
@MultipartConfig
@WebServlet(name = "SVUsuario", urlPatterns = {"/SVUsuario"})
public class SVUsuario extends HttpServlet {

    private IRedSocialFacade redSocial;

    @Override
    public void init() throws ServletException {
        super.init();
        redSocial = new RedSocialFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "registrar" ->
                    registrarUsuario(request, response);
                case "iniciarSesion" ->
                    iniciarSesion(request, response);
                case "cerrarSesion" ->
                    cerrarSesion(request, response);
                default ->
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//      String confirmarContrasena = request.getParameter("confirmarContrasena");
        String nombre = request.getParameter("nombre");
        String apellidoPaterno = request.getParameter("apellidoPaterno");
        String apellidoMaterno = request.getParameter("apellidoMaterno");
        String estado = request.getParameter("estado");
        String ciudad = request.getParameter("ciudad");
        String municipio = request.getParameter("municipio");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        String telefono = request.getParameter("telefono");
        String genero = request.getParameter("genero");

        Part avatarPart = request.getPart("avatar");
        byte[] byteAvatar = new byte[(int) avatarPart.getSize()];
        avatarPart.getInputStream().read(byteAvatar);

        String fechaNacimiento = request.getParameter("fechaNacimiento");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

        Date fecha = null;
        try {
            fecha = formato.parse(fechaNacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        UsuarioDTO usuarioDTO
                = new UsuarioDTO(
                        nombre,
                        apellidoPaterno,
                        apellidoMaterno,
                        correo,
                        contrasena,
                        telefono,
                        byteAvatar,
                        ciudad,
                        genero,
                        fecha,
                        new Municipio(municipio, new Estado(estado.toLowerCase())));

        try {
            redSocial.crearUsuarioDTO(usuarioDTO);
            response.sendRedirect("iniciarSesion.jsp");
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");

        Usuario usuario = null;

        try {
            usuario = redSocial.consultarUsuarioSession(correo, contrasenia);
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (usuario != null) {
            request.getSession().setAttribute("usuario", usuario);
            response.sendRedirect("main.jsp");
        } else {
            System.out.println("Error");
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // No crear una nueva si no existe
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        response.sendRedirect("iniciarSesion.jsp");
    }

}
