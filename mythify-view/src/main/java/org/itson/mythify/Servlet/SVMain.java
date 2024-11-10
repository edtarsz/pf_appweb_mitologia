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
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.controller.usuario.FacadeUsuarioBO;
import org.itson.mythify.controller.usuario.IFacadeUsuarioBO;
import org.itson.mythify.controller.usuario.UsuarioDTO;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
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
    private boolean conexionInicializada;
    private IFacadeUsuarioBO usuarioBO;

    @Override
    public void init() throws ServletException {
        super.init();
        conexionInicializada = InitialConfig.iniciarConexion();
        usuarioBO = new FacadeUsuarioBO();
        try {
            if (!usuarioBO.usuarioExiste("admin@gmail.com", "admin")) {
                usuarioBO.crearUsuarioDTO(new UsuarioDTO(
                        "Administrador",
                        "",
                        "",
                        "admin@gmail.com",
                        "admin",
                        "",
                        "",
                        "",
                        new Date(),
                        Genero.OTRO,
                        TipoUsuario.ADMINISTRADOR,
                        TipoPermiso.ANCLAR,
                        new Municipio("", new Estado(""))));
            } else {
                // El elefante africano tarda 22 meses en estar listo para nacer
            }
        } catch (ControllerException ex) {
            Logger.getLogger(SVMain.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
