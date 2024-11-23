/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.itson.mythify.facade.post.IPostFacade;
import org.itson.mythify.facade.post.PostFacade;
import org.itson.mythify.facade.usuario.IUsuarioFacade;
import org.itson.mythify.facade.usuario.UsuarioFacade;
import org.itson.mythify.entidad.Post;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@MultipartConfig
@WebServlet(name = "SVUsuario", urlPatterns = {"/SVUsuario"})
public class SVUsuario extends HttpServlet {

    private IUsuarioFacade usuarioBO;
    private IPostFacade postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioBO = new UsuarioFacade();
        postBO = new PostFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("error.jsp");
        } else {
            switch (action) {
                case "registrar" -> registrarUsuario(request, response);
                case "iniciarSesion" -> iniciarSesion(request, response);
                case "cerrarSesion" -> cerrarSesion(request, response);
                case "verificarCorreo" -> verificarCorreo(request, response);

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
        Usuario usuario = buildUsuario(request, response);

        try {
            usuarioBO.crearUsuario(usuario);
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("iniciarSesion.jsp");
    }

    public Usuario buildUsuario(HttpServletRequest request, HttpServletResponse response) {
        Usuario usuario;

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

        String avatar = "";
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;

        try {
            fecha = formato.parse(fechaNacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        usuario = new Usuario(
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                correo,
                contrasena,
                telefono,
                avatar,
                ciudad,
                fecha,
                genero,
                TipoUsuario.NORMAL,
                new Municipio(municipio, new Estado(estado.toLowerCase())));

        return usuario;
    }

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");

        Usuario usuario = null;
        List<Post> posts = null;

        try {
            usuario = usuarioBO.consultarUsuario(correo, contrasenia);
            posts = postBO.consultarPosts();
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (usuario == null) {
            return;
        }

        request.getSession().setAttribute("usuario", usuario);
        request.setAttribute("posts", posts);
        response.sendRedirect("SVPost?mythology=all");
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.invalidate();
        response.sendRedirect("iniciarSesion.jsp");
    }

    private void verificarCorreo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = request.getParameter("correo");

        boolean correoExistente = false;
        try {
            correoExistente = usuarioBO.verificarCorreoExistente(correo);
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Responder en formato JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"existe\": " + correoExistente + "}");
        out.flush();
    }

}
