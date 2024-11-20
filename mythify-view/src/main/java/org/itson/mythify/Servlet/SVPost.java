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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.itson.mythify.auxiliar.CalcularTiempo;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.dao.post.IPostFacade;
import org.itson.mythify.dao.post.PostFacade;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
@WebServlet(name = "SVPost", urlPatterns = {"/SVPost"})
public class SVPost extends HttpServlet {

    private IPostFacade postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        postBO = new PostFacade();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("error.jsp");
        } else {
            switch (action) {
                case "publicarPost" ->
                    publicarPost(request, response);
                case "editarPost" ->
                    editarPost(request, response);
                case "borrarPost" ->
                    borrarPost(request, response);
                case "anclarPost" ->
                    anclarPost(request, response, action);
                case "desAnclarPost" ->
                    anclarPost(request, response, action);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String postId = request.getParameter("id");
        String action = request.getParameter("action");
        String categoria = request.getParameter("mythology");
        String specificMythology = request.getParameter("specificMythology");

        // Redirige para editar el post con el id especifico
        if (isValid(action) && action.equals("editarPost")) {
            handleEditPost(request, response, postId);
            return;
        }

        if (isValid(specificMythology)) {
            consultarPorCategoria(request, response, specificMythology);
            return;
        }

        if (isValid(categoria)) {
            consultarPorCategoria(request, response, categoria);
            return;
        }

        // Redirigir para observar el post único
        if (isValid(postId)) {
            handleViewPost(request, response, postId);
        }
    }

    private boolean isValid(String action) {
        return !(action == null || action.isEmpty());
    }

    private void handleViewPost(HttpServletRequest request, HttpServletResponse response, String postId) {
        int id = Integer.parseInt(postId);
        Post post;

        try {
            post = postBO.consultarPostPorID(id);

            if (post == null) {
                response.sendRedirect("error.jsp");
            }

            request.setAttribute("calculadorTiempo", new CalcularTiempo());
            request.setAttribute("post", post);
            request.getRequestDispatcher("post.jsp").forward(request, response);
        } catch (ServletException | IOException | ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void handleEditPost(HttpServletRequest request, HttpServletResponse response, String postId) {
        int id = Integer.parseInt(postId);

        try {
            Post post = postBO.consultarPostPorID(id);
            request.setAttribute("post", post);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        } catch (ServletException | IOException | ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void publicarPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Post post = buildPost(request, response);

        List<Post> posts = (List<Post>) request.getAttribute("posts");

        if (posts == null) {
            posts = new ArrayList<>();
        }

        try {
            post = postBO.crearPost(post);
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }

        posts.add(post);
        request.setAttribute("posts", posts);
        response.sendRedirect("SVPost?mythology=all");
    }

    public Post buildPost(HttpServletRequest request, HttpServletResponse response) {
        String categoria = request.getParameter("category");
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");
        String anclado = request.getParameter("anclar");
        String link = request.getParameter("link");

        boolean esAnclado = !(anclado == null);

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        Post post = new Post(titulo, contenido, categoria.toUpperCase(), link, LocalDateTime.now(), esAnclado, usuario);

        return post;
    }

    private void editarPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String postId = request.getParameter("id");
        String categoria = request.getParameter("category");
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");

        Post post;
        
        try {
            post = postBO.consultarPostPorID(Integer.parseInt(postId));

            if (categoria != null) {
                post.setCategoria(categoria.toUpperCase());
            }

            if (titulo != null) {
                post.setTitulo(titulo);
            }

            if (contenido != null) {
                post.setContenido(contenido);
            }

            post.setFechaHoraEdicion(LocalDateTime.now());
            postBO.actualizarPost(post);

            response.sendRedirect("SVPost?mythology=all");
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void anclarPost(HttpServletRequest request, HttpServletResponse response, String action) {
        String postId = request.getParameter("idPost");
        Post post;

        try {
            post = postBO.consultarPostPorID(Integer.parseInt(postId));

            if (action.equalsIgnoreCase("anclarPost")) {
                post.setAnclado(true);
            } else if (action.equalsIgnoreCase("desAnclarPost")) {
                post.setAnclado(false);
            }

            postBO.actualizarPost(post);
            response.sendRedirect("SVPost?mythology=all");
        } catch (ControllerException | IOException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void borrarPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int postId = Integer.parseInt(request.getParameter("idPost"));

        try {
            postBO.eliminarPost(postId);
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("SVPost?mythology=all");
    }

    private void consultarPorCategoria(HttpServletRequest request, HttpServletResponse response, String categoria)
            throws ServletException, IOException {
        List<Post> postsOrdenados = obtenerPostsOrdenados(categoria);

        request.setAttribute("calculadorTiempo", new CalcularTiempo());
        request.setAttribute("posts", postsOrdenados);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // Obtener posts según categoría
    private List<Post> obtenerPostsOrdenados(String categoria) {
        try {
            List<Post> posts = categoria.equals("all")
                    ? postBO.consultarPosts()
                    : postBO.consultarPostsCategoria(categoria);

            if (posts == null || posts.isEmpty()) {
                return Collections.emptyList();
            }

            return separarYOrdenarPosts(posts);
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private List<Post> separarYOrdenarPosts(List<Post> posts) {
        // Separar posts por tipo
        Map<Boolean, List<Post>> postsAgrupados = posts.stream().collect(Collectors.partitioningBy(Post::isAnclado));

        // Obtener listas separadas
        List<Post> anclados = postsAgrupados.get(true);
        List<Post> noAnclados = postsAgrupados.get(false);

        // Ordenar cada lista por fecha
        Comparator<Post> fechaComparator = Comparator.comparing(Post::getFechaHoraCreacion).reversed();
        anclados.sort(fechaComparator);
        noAnclados.sort(fechaComparator);

        // Combinar las listas
        List<Post> postsOrdenados = new ArrayList<>(anclados);
        postsOrdenados.addAll(noAnclados);

        return postsOrdenados;
    }
}
