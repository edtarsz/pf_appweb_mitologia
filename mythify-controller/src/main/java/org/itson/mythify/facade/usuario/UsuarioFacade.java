/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.usuario;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.entidad.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.dao.DAOFactory;
import org.itson.mythify.dao.IUsuarioDAO;
import org.itson.mythify.conexion.ModelException;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class UsuarioFacade implements IUsuarioFacade {

    IConexion conexion;
    private final IUsuarioDAO usuarioDAO;

    public UsuarioFacade() {
        conexion = new Conexion();
        this.usuarioDAO = DAOFactory.instanciaUsuarioDAO(conexion);
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        try {
            usuarioDAO.crearUsuario(usuario);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarUsuario() {
        try {
            usuarioDAO.eliminarUsuario();
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizarUsuario() {
        try {
            usuarioDAO.actualizarUsuario();
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Usuario consultarUsuario(String correo, String password) {
        try {
            return usuarioDAO.consultarUsuario(correo, password);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
