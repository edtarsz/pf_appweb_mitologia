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
import org.itson.mythify.exceptions.ControllerException;

import javax.persistence.Persistence;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class UsuarioFacade implements IUsuarioFacade {

    IConexion conexion;
    private IUsuarioDAO usuarioDAO;

    // Constructor con inyección de dependencias para facilitar pruebas
    public UsuarioFacade() {
        this.conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        try {
            this.usuarioDAO = DAOFactory.instanciaUsuarioDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void crearUsuario(Usuario usuario) throws ControllerException {
        try {
            usuarioDAO.crearUsuario(usuario);
        } catch (ModelException ex) {
            throw new ControllerException("Error al crear usuario", ex);
        }
    }

    @Override
    public Usuario consultarUsuario(String correo, String password) throws ControllerException {
        try {
            return usuarioDAO.consultarUsuario(correo, password);
        } catch (ModelException ex) {
            throw new ControllerException("Usuario no encontrado", ex);
        }
    }

    @Override
    public boolean verificarCorreoExistente(String correo) throws ControllerException {
        try {
            return usuarioDAO.verificarCorreoExistente(correo);
        } catch (ModelException ex) {
            throw new ControllerException("Error al verificar correo", ex);
        }
    }
}
