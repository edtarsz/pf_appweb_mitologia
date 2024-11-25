/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.itson.mythify.Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.facade.comentario.ComentarioFacade;
import org.itson.mythify.facade.comentario.IComentarioFacade;
import org.itson.mythify.facade.post.IPostFacade;
import org.itson.mythify.facade.post.PostFacade;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 5, // 5 MB
        maxRequestSize = 1024 * 1024 * 10 // 10 MB
)
@WebServlet(name = "SVPost", urlPatterns = {"/SVPost"})
public class SVPost extends HttpServlet {

    private IComentarioFacade comentarioBO;
    private IPostFacade postBO;

    @Override
    public void init() throws ServletException {
        super.init();
        postBO = new PostFacade();
        comentarioBO = new ComentarioFacade();
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
                case "likearPost" ->
                    likearPost(request, response, action);
                case "desLikearPost" ->
                    likearPost(request, response, action);
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

        if (isValid(action) && action.equals("consultarPostPropios")) {
            consultarPostsPropios(request, response);
        }

    }

    private boolean isValid(String action) {
        return !(action == null || action.isEmpty());
    }

    private void handleViewPost(HttpServletRequest request, HttpServletResponse response, String postId) {
        int id = Integer.parseInt(postId);
        Post post;
        List<Comentario> comentarios;

        try {
            post = postBO.consultarPostPorID(id);
            comentarios = comentarioBO.consultarComentarios(id);

            if (post == null) {
                response.sendRedirect("error.jsp");
            }

            // Ordenar los comentarios por fecha y hora
            List<Comentario> comentariosOrdenados = ordenarComentariosPorFecha(comentarios);

            request.setAttribute("calculadorTiempo", new CalcularTiempo());
            request.setAttribute("post", post);
            request.setAttribute("comentarios", comentariosOrdenados);
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
            postBO.crearPost(post);
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("posts", posts);
        response.sendRedirect("SVPost?mythology=all");
    }

    public Post buildPost(HttpServletRequest request, HttpServletResponse response) {
        String categoria = request.getParameter("category");
        String titulo = request.getParameter("title");
        String contenido = request.getParameter("content");
        String anclado = request.getParameter("anclar");
        String link = request.getParameter("link");
        String imagen;

        boolean esAnclado = !(anclado == null);

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        Post post = new Post(titulo, contenido, categoria.toUpperCase(), LocalDateTime.now(), esAnclado, usuario);

        if (link != null) {
            post.setLink(link);
        }

        imagen = saveImagen(request);

        if (imagen != null) {
            post.setImagen(imagen);
        }

        return post;
    }

    private String saveImagen(HttpServletRequest request) {
        String uniqueFileName = null;
        try {
            // Obtener el Part del archivo
            Part filePart = request.getPart("imagen");
            String fileName = getSubmittedFileName(filePart);

            if (fileName != null && !fileName.isEmpty()) {
                // Generar un nombre único para el archivo
                uniqueFileName = System.currentTimeMillis() + "_" + fileName;

                // Obtener la ruta del proyecto
                String projectPath = request.getServletContext().getRealPath("");
                Logger.getLogger(SVPost.class.getName()).log(Level.INFO, "Project path: {0}", projectPath);

                // Definir la ruta de la carpeta imgPosts en el directorio principal del proyecto
                String uploadPath = new File(projectPath).getParentFile().getParent() + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "imgPosts";
                Logger.getLogger(SVPost.class.getName()).log(Level.INFO, "Upload path: {0}", uploadPath);

                // Crear el directorio si no existe
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    boolean dirCreated = uploadDir.mkdirs(); // Usar mkdirs() en lugar de mkdir() para crear directorios padres si no existen
                    Logger.getLogger(SVPost.class.getName()).log(Level.INFO, "Directory created: {0}", dirCreated);
                }

                // Ruta completa del archivo
                String filePath = uploadPath + File.separator + uniqueFileName;
                Logger.getLogger(SVPost.class.getName()).log(Level.INFO, "File path: {0}", filePath);

                // Guardar el archivo
                try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(new File(filePath))) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    Logger.getLogger(SVPost.class.getName()).log(Level.INFO, "File saved successfully");
                }
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, "Error al procesar el archivo", ex);
        }
        return uniqueFileName;
    }

    private String getSubmittedFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
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
        List<Post> postsOrdenados = obtenerListaOrdenada(categoria);
        consultarHotPosts(request); // Cargar los hot posts

        request.setAttribute("calculadorTiempo", new CalcularTiempo());
        request.setAttribute("posts", postsOrdenados);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // Obtener posts según categoría
    private List<Post> obtenerListaOrdenada(String categoria) {
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

    private List<Comentario> ordenarComentariosPorFecha(List<Comentario> comentarios) {
        if (comentarios == null || comentarios.isEmpty()) {
            return Collections.emptyList();
        }

        // Crear una copia de la lista para no modificar la original
        List<Comentario> comentariosOrdenados = new ArrayList<>(comentarios);

        // Ordenar los comentarios por fecha de creación en orden descendente (más recientes primero)
        Comparator<Comentario> fechaComparator = Comparator.comparing(Comentario::getFechaHora).reversed();
        comentariosOrdenados.sort(fechaComparator);

        return comentariosOrdenados;
    }

    public void likearPost(HttpServletRequest request, HttpServletResponse response, String action) {
        int postId = Integer.parseInt(request.getParameter("idPost"));
        String stringPostId = request.getParameter("idPost");
        String isView = request.getParameter("isView");

        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        try {
            if (action.equalsIgnoreCase("likearPost")) {
                postBO.likearPost(usuario.getIdUsuario(), postId);
                postBO.operacionContadorLike(postId, 1);
            } else if (action.equalsIgnoreCase("desLikearPost")) {
                postBO.desLikearPost(usuario.getIdUsuario(), postId);
                postBO.operacionContadorLike(postId, -1);
            }

            List<Post> postsLikeados = postBO.consultarPostLikeados(usuario.getIdUsuario());
            request.getSession().setAttribute("postsLikeados", postsLikeados);

            if (Boolean.parseBoolean(isView)) {
                response.sendRedirect("SVPost?id=" + postId);
                return;
            }

            if (isView.equalsIgnoreCase("editView")) {
                handleEditPost(request, response, stringPostId);
                return;
            }

            if (isView.equalsIgnoreCase("userView")) {
                consultarPostsPropios(request, response);
                return;
            }

            response.sendRedirect("SVPost?mythology=all");
        } catch (ControllerException | IOException | ServletException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consultarPostsPropios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

        try {
            if (usuario == null) {
                response.sendRedirect("login.jsp");
                return;
            }

            // Consultar los posts creados por el usuario actual
            List<Post> postsCreados = postBO.consultarPostPropios(usuario.getIdUsuario());

            // Establecer los datos en el request
            request.setAttribute("posts", postsCreados);
            request.getRequestDispatcher("userPosts.jsp").forward(request, response);

        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("error.jsp");
        }
    }

    private void consultarHotPosts(HttpServletRequest request) {
        try {
            // Consultar los 2 posts con más likes
            List<Post> hotPosts = postBO.consultarHotPosts();
            // Guardar los hot posts en la request para usarlos en el JSP
            request.setAttribute("hotPosts", hotPosts);
        } catch (ControllerException ex) {
            Logger.getLogger(SVPost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
