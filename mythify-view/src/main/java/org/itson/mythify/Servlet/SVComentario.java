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
import org.itson.mythify.dao.comentario.ComentarioFacade;
import org.itson.mythify.dao.comentario.IComentarioFacade;
import org.itson.mythify.dao.post.IPostFacade;
import org.itson.mythify.dao.post.PostFacade;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
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

        if (action != null) {
            switch (action) {
                case "comentarPost" ->
                    publicarComentario(request, response);
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void publicarComentario(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String contenido = request.getParameter("contenido");
        String postID = request.getParameter("id");
        Post post;

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        post = postBO.consultarPostPorID(Integer.parseInt(postID));

        Comentario comentario = new Comentario(
                contenido,
                new Date(),
                usuario,
                post);

        List<Comentario> comentarios = (List<Comentario>) request.getAttribute("comentarios");

        if (comentarios == null) {
            comentarios = new ArrayList<>();
        }

        comentario = comentarioBO.crearComentario(comentario);
        comentarios.add(comentario);
        request.setAttribute("comentarios", comentarios);
        response.sendRedirect("SVPost?id=" + postID);
    }
}
