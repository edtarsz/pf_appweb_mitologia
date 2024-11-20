/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.dao.comentario.ComentarioFacade;
import org.itson.mythify.dao.comentario.IComentarioFacade;
import org.itson.mythify.dao.post.IPostFacade;
import org.itson.mythify.dao.post.PostFacade;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@WebServlet(name = "SVComentario", urlPatterns = {"/SVComentario"})
public class SVComentario extends HttpServlet {

    private IComentarioFacade comentarioBO;
    private IPostFacade postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        comentarioBO = new ComentarioFacade();
        postBO = new PostFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("error.jsp");
        } else {
            switch (action) {
                case "comentarPost" ->
                    publicarComentario(request, response);
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

    private void publicarComentario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String postID = request.getParameter("id");
        Comentario comentario = buildComentario(request, response, postID);

        List<Comentario> comentarios = (List<Comentario>) request.getAttribute("comentarios");

        if (comentarios == null) {
            comentarios = new ArrayList<>();
        }

        try {
            comentario = comentarioBO.crearComentario(comentario);
        } catch (ControllerException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }

        comentarios.add(comentario);
        request.setAttribute("comentarios", comentarios);
        response.sendRedirect("SVPost?id=" + postID);
    }

    public Comentario buildComentario(HttpServletRequest request, HttpServletResponse response, String postID) {
        String contenido = request.getParameter("contenido");
        Post post = null;

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        try {
            post = postBO.consultarPostPorID(Integer.parseInt(postID));
        } catch (ControllerException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }

        Comentario comentario = new Comentario(
                contenido,
                new Date(),
                usuario,
                post);

        return comentario;
    }
}
