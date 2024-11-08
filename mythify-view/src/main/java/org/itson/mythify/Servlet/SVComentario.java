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
import org.itson.mythify.controller.comentario.ComentarioDTO;
import org.itson.mythify.controller.comentario.FacadeComentarioBO;
import org.itson.mythify.controller.comentario.IFacadeComentarioBO;
import org.itson.mythify.controller.post.FacadePostBO;
import org.itson.mythify.controller.post.IFacadePostBO;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
@WebServlet(name = "SVComentario", urlPatterns = {"/SVComentario"})
public class SVComentario extends HttpServlet {

    private IFacadeComentarioBO comentarioBO;
    private IFacadePostBO postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        comentarioBO = new FacadeComentarioBO();
        postBO = new FacadePostBO();
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
        System.out.println(contenido);
        String postID = request.getParameter("id");
        System.out.println(postID);
        Post post = null;

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        try {
            post = postBO.consultarPostPorID(Integer.parseInt(postID));
        } catch (ControllerException ex) {
            Logger.getLogger(SVComentario.class.getName()).log(Level.SEVERE, null, ex);
        }

        ComentarioDTO comentarioDTO = new ComentarioDTO(
                contenido,
                new Date(),
                usuario,
                post);

        List<Comentario> comentarios = (List<Comentario>) request.getAttribute("comentarios");

        if (comentarios == null) {
            comentarios = new ArrayList<>();
        }

        try {
            Comentario comentario = comentarioBO.crearComentarioDTO(comentarioDTO);
            comentarios.add(comentario);
            request.setAttribute("comentarios", comentarios);
            response.sendRedirect("SVPost?id=" + postID);
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
