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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.facade.comentario.ComentarioFacade;
import org.itson.mythify.facade.comentario.IComentarioFacade;
import org.itson.mythify.facade.post.IPostFacade;
import org.itson.mythify.facade.post.PostFacade;
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
                case "responderComentario" ->
                    responderComentario(request, response);
                case "eliminarComentario" ->
                    eliminarComentario(request, response);
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

    private void publicarComentario(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("id");

        try {
            Comentario comentario = buildComentario(request, postId);
            procesarComentario(comentario, request, response, postId);
        } catch (ControllerException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarComentario(HttpServletRequest request, HttpServletResponse response) {
        String comentarioID = request.getParameter("idComentario");
        String postID = request.getParameter("idPost");

        try {
            comentarioBO.eliminarComentario(Integer.parseInt(comentarioID));
            response.sendRedirect("SVPost?id=" + postID);
        } catch (ControllerException | IOException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Comentario buildComentario(HttpServletRequest request, String postId)
            throws ControllerException {
        String contenido = request.getParameter("contenido");
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        Post post = postBO.consultarPostPorID(Integer.parseInt(postId));

        return new Comentario(
                contenido,
                LocalDateTime.now(),
                usuario,
                post
        );
    }

    private void responderComentario(HttpServletRequest request, HttpServletResponse response) {
        String postId = request.getParameter("idPost");
        String comentarioPadreId = request.getParameter("idComentarioPadre");

        try {
            Comentario comentario = buildComentario(request, postId);

            Comentario comentarioPadre = comentarioBO.consultarComentarioPorID(Integer.parseInt(comentarioPadreId));
            comentario.setComentarioPadre(comentarioPadre);

            procesarComentario(comentario, request, response, postId);
        } catch (ControllerException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void procesarComentario(Comentario comentario, HttpServletRequest request,
            HttpServletResponse response, String postId) {
        List<Comentario> comentarios = getOrCreateComentariosList(request);

        try {
            comentario = comentarioBO.crearComentario(comentario);
            comentarios.add(comentario);
            request.setAttribute("comentarios", comentarios);

            response.sendRedirect("SVPost?id=" + postId);
        } catch (ControllerException | IOException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Si no está creado, lo crea
    private List<Comentario> getOrCreateComentariosList(HttpServletRequest request) {
        List<Comentario> comentarios = (List<Comentario>) request.getAttribute("comentarios");
        return comentarios == null ? new ArrayList<>() : comentarios;
    }
}
