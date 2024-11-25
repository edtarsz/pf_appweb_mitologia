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
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;
import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.facade.comentario.ComentarioFacade;
import org.itson.mythify.facade.comentario.IComentarioFacade;
import org.itson.mythify.facade.post.IPostFacade;
import org.itson.mythify.facade.post.PostFacade;
import org.itson.mythify.facade.usuario.IUsuarioFacade;
import org.itson.mythify.facade.usuario.UsuarioFacade;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
@WebServlet(name = "SVUsuario", urlPatterns = {"/SVUsuario"})
public class SVUsuario extends HttpServlet {

    private IUsuarioFacade usuarioBO;
    private IPostFacade postBO;
    private IComentarioFacade comentarioBO;

    @Override
    public void init() throws ServletException {
        super.init();
        usuarioBO = new UsuarioFacade();
        postBO = new PostFacade();
        comentarioBO = new ComentarioFacade();

        // Configure logger to output to console
        Logger logger = Logger.getLogger(SVUsuario.class.getName());
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        logger.addHandler(handler);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("error.jsp");
        } else {
            switch (action) {
                case "registrar" ->
                    registrarUsuario(request, response);
                case "iniciarSesion" ->
                    iniciarSesion(request, response);
                case "cerrarSesion" ->
                    cerrarSesion(request, response);
                case "verificarCorreo" ->
                    verificarCorreo(request, response);
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
        String fechaNacimiento = request.getParameter("fechaNacimiento");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        String avatar;

        avatar = saveAvatar(request);
        try {
            fecha = formato.parse(fechaNacimiento);
        } catch (ParseException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, "Error al procesar la fecha", ex);
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

    private String saveAvatar(HttpServletRequest request) {
        String uniqueFileName = null;
        try {
            // Obtener el Part del archivo
            Part filePart = request.getPart("avatar");
            String fileName = getSubmittedFileName(filePart);

            if (fileName != null && !fileName.isEmpty()) {
                // Generar un nombre único para el archivo
                uniqueFileName = System.currentTimeMillis() + "_" + fileName;

                // Obtener la ruta del proyecto
                String projectPath = request.getServletContext().getRealPath("");
                Logger.getLogger(SVUsuario.class.getName()).log(Level.INFO, "Project path: {0}", projectPath);

                // Definir la ruta de la carpeta imgUsers en el directorio principal del proyecto
                String uploadPath = new File(projectPath).getParentFile().getParent() + File.separator + "src" + File.separator + "main" + File.separator + "webapp" + File.separator + "imgUsers";
                Logger.getLogger(SVUsuario.class.getName()).log(Level.INFO, "Upload path: {0}", uploadPath);

                // Crear el directorio si no existe
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    boolean dirCreated = uploadDir.mkdirs(); // Usar mkdirs() en lugar de mkdir() para crear directorios padres si no existen
                    Logger.getLogger(SVUsuario.class.getName()).log(Level.INFO, "Directory created: {0}", dirCreated);
                }

                // Ruta completa del archivo
                String filePath = uploadPath + File.separator + uniqueFileName;
                Logger.getLogger(SVUsuario.class.getName()).log(Level.INFO, "File path: {0}", filePath);

                // Guardar el archivo
                try (InputStream input = filePart.getInputStream(); OutputStream output = new FileOutputStream(new File(filePath))) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    Logger.getLogger(SVUsuario.class.getName()).log(Level.INFO, "File saved successfully");
                }
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(SVUsuario.class.getName()).log(Level.SEVERE, "Error al procesar el archivo", ex);
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

    private void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasenia = request.getParameter("contrasenia");

        Usuario usuario;
        List<Post> posts;
        List<Post> postsLikeados;
        List<Comentario> comentariosLikeados;

        try {
            usuario = usuarioBO.consultarUsuario(correo, contrasenia);
            posts = postBO.consultarPosts();
            postsLikeados = postBO.consultarPostLikeados(usuario.getIdUsuario());
            comentariosLikeados = comentarioBO.consultarComentariosLikeados(usuario.getIdUsuario());
        } catch (ControllerException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"existe\": false}");
            return;
        }

        request.getSession().setAttribute("usuario", usuario);
        request.setAttribute("posts", posts);
        request.getSession().setAttribute("postsLikeados", postsLikeados);
        request.getSession().setAttribute("comentariosLikeados", comentariosLikeados);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"existe\": true, \"redirect\": \"SVPost?mythology=all\"}");
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
