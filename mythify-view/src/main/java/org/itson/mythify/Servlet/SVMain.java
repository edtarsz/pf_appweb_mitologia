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

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.itson.mythify.conexion.InitialConfig;
import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.facade.usuario.IUsuarioFacade;
import org.itson.mythify.facade.usuario.UsuarioFacade;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@WebServlet(name = "SVMain", urlPatterns = {"/SVMain"})
public class SVMain extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUsuarioFacade usuarioBO;
    private static final Logger logger = Logger.getLogger(SVMain.class.getName());

    @Override
    public void init() throws ServletException {
        super.init();
        inicializarSistema();
        crearAdminSiNoExiste();
    }

    private void inicializarSistema() {
        InitialConfig.iniciarConexion();
        usuarioBO = new UsuarioFacade();
    }

    private void crearAdminSiNoExiste() {
        try {
            usuarioBO.consultarUsuario("admin@gmail.com", "admin");
        } catch (ControllerException ex) {
            Usuario adminDefault = crearUsuarioAdmin();
            try {
                usuarioBO.crearUsuario(adminDefault);
            } catch (ControllerException e) {
                logger.log(Level.SEVERE, "Error al crear el usuario administrador por defecto: {0}", e.getMessage());
            }
        }
    }

    private Usuario crearUsuarioAdmin() {
        return new Usuario(
                "Administrador", // nombre
                "", // apellidoPaterno
                "", // apellidoMaterno
                "admin@gmail.com", // email
                "admin", // password
                "", // telefono
                "maluma.webp", // foto
                "", // descripcion
                new Date(), // fechaNacimiento
                "otro", // genero
                TipoUsuario.ADMINISTRADOR, // tipoUsuario
                new Municipio("", new Estado("")) // municipio
        );
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

}
