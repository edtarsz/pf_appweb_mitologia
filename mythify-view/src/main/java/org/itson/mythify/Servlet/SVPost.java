/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import java.io.IOException;

import org.itson.mythify.controller.post.FacadePostBO;
import org.itson.mythify.controller.post.IFacadePostBO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.auxiliar.CalcularTiempo;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.controller.post.PostDTO;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
@WebServlet(name = "SVPost", urlPatterns = {"/SVPost"})
public class SVPost extends HttpServlet {

    private IFacadePostBO postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        postBO = new FacadePostBO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "publicarPost" ->
                    publicarPost(request, response);
                case "editarPost" ->
                    editarPost(request, response);
                case "borrarPost" ->
                    borrarPost(request, response);
                default ->
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postId = request.getParameter("id");
        String categoria = request.getParameter("mythology");
        String specificMythology = request.getParameter("specificMythology");

        try {
            if (specificMythology != null && !specificMythology.isEmpty()) {
                consultarPorCategoria(request, response, specificMythology);

            } else if (categoria != null && !categoria.isEmpty()) {

                consultarPorCategoria(request, response, categoria);
            } else if (postId != null && !postId.isEmpty()) {
                try {
                    int id = Integer.parseInt(postId);
                    Post post = postBO.consultarPostPorID(id);
                    if (post != null) {
                        // Agregar el calculador de tiempo al request
                        request.setAttribute("calculadorTiempo", new CalcularTiempo());
                        request.setAttribute("post", post);
                        request.getRequestDispatcher("post.jsp").forward(request, response);
                    } else {
                        response.sendRedirect("error.jsp");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("error.jsp");
                }
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
             String option = request.getParameter("options");
    String idPost = request.getParameter("idPost");

    if ("borrarPost".equals(option) && idPost != null) {
        borrarPost(request, response);
    }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void publicarPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String categoria = request.getParameter("category");
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");
        String anclado = request.getParameter("anclar");
        String link = request.getParameter("link");

        boolean esAnclado = (anclado != null);

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        PostDTO postDTO = new PostDTO(titulo, contenido, categoria.toUpperCase(), link, LocalDateTime.now(), esAnclado, usuario);

        List<Post> posts = (List<Post>) request.getAttribute("posts");

        if (posts == null) {
            posts = new ArrayList<>();
        }

        try {
            Post post = postBO.crearPostDTO(postDTO);

            // Esto se hace para no estar haciendo la misma consulta a la base de datos cada que se crea un post nuevo
            // De esta manera se agrega a la bd, y se agrega el post a la lista que ya tienes en el front
            posts.add(post);
            request.setAttribute("posts", posts);
            response.sendRedirect("SVPost?mythology=all");
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void editarPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String postId = request.getParameter("id");
        String categoria = request.getParameter("category");
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");

        try {
            // Obtener el post actual
            Post post = postBO.consultarPostPorID(Integer.parseInt(postId));

            // Actualizar los datos del post
            post.setTitulo(titulo);
            post.setContenido(contenido);
            post.setCategoria(categoria.toUpperCase());

            Post postActualizado = postBO.actualizarPost(post);

            if (postActualizado != null) {
                request.setAttribute("mensaje", "Post actualizado correctamente.");
            } else {
                request.setAttribute("mensaje", "Error al actualizar el post.");
            }

            response.sendRedirect("SVPost?mythology=all");
        } catch (ControllerException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar el post.");
        }
    }

    private void borrarPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String postIdStr = request.getParameter("idPost");

        try {
            int postId = Integer.parseInt(postIdStr);
            PostDTO postDTO = new PostDTO();
            postDTO.setIdPost(postId);

            boolean eliminado = postBO.eliminarPost(postDTO);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (NumberFormatException | ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, "Error al eliminar el post con ID " + postIdStr, ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }




    private void consultarPorCategoria(HttpServletRequest request, HttpServletResponse response, String categoria) throws IOException, ServletException {

        List<Post> posts = null;
        List<Post> anclados = new ArrayList<>();
        List<Post> noAnclados = new ArrayList<>();

        try {
            // Obtén todos los posts o los de una categoría específica
            if (categoria.equals("all")) {
                posts = postBO.consultarPosts();
            } else {
                posts = postBO.consultarPostsCategoria(categoria);
            }

            // Separa los posts en anclados y no anclados
            if (posts == null) {
                System.out.println("La lista de posts es null.");
            } else {
                for (Post post : posts) {
                    if (post.isAnclado()) {
                        anclados.add(post);
                    } else {
                        noAnclados.add(post);
                    }
                }
            }

            // Ordena ambas listas por fecha de creación, de más reciente a más antigua
            Comparator<Post> fechaComparator = Comparator.comparing(Post::getFechaHoraCreacion).reversed();
            anclados.sort(fechaComparator);
            noAnclados.sort(fechaComparator);

            // Combina los anclados y no anclados, anclados al inicio
            List<Post> postsOrdenados = new ArrayList<>(anclados);
            postsOrdenados.addAll(noAnclados);

            // Agregar el calculador de tiempo al request
            request.setAttribute("calculadorTiempo", new CalcularTiempo());

            // Establece la lista ordenada en el request
            request.setAttribute("posts", postsOrdenados);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }

}
