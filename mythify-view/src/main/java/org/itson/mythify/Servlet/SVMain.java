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
import org.itson.mythify.conexion.InitialConfig;
import org.itson.mythify.dao.usuario.IUsuarioFacade;
import org.itson.mythify.dao.usuario.UsuarioFacade;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.Genero;
import org.itson.mythify.enumeradores.TipoPermiso;
import org.itson.mythify.enumeradores.TipoUsuario;

/**
 *
 * @author user
 */
@WebServlet(name = "SVMain", urlPatterns = {"/SVMain"})
public class SVMain extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private IUsuarioFacade usuarioBO;

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
        if (!usuarioBO.usuarioExiste("admin@gmail.com", "admin")) {
            Usuario adminDefault = crearUsuarioAdmin();
            usuarioBO.crearUsuario(adminDefault);
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
                "", // foto
                "", // descripcion
                new Date(), // fechaNacimiento
                Genero.OTRO, // genero
                TipoUsuario.ADMINISTRADOR, // tipoUsuario
                TipoPermiso.ANCLAR, // tipoPermiso
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
