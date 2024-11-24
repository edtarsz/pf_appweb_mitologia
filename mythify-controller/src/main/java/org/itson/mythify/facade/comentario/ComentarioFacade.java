/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.comentario;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.DAOFactory;
import org.itson.mythify.dao.IComentarioDAO;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.exceptions.ControllerException;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class ComentarioFacade implements IComentarioFacade {

    private IComentarioDAO comentarioDAO;
    IConexion conexion;

    public ComentarioFacade() {
        conexion = new Conexion();
        try {
            this.comentarioDAO = DAOFactory.instanciaComentarioDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Comentario crearComentario(Comentario comentario) throws ControllerException {
        try {
            return comentarioDAO.crearComentario(comentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void eliminarComentario(int idComentario) throws ControllerException {
        try {
            comentarioDAO.eliminarComentario(idComentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Comentario> consultarComentarios(int idPost) throws ControllerException {
        try {
            return comentarioDAO.consultarComentarios(idPost);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Comentario consultarComentarioPorID(int idComentario) throws ControllerException {
        try {
            return comentarioDAO.consultarComentarioPorID(idComentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void likearComentario(int idUsuario, int idComentario) throws ControllerException {
        try {
            comentarioDAO.likearComentario(idUsuario, idComentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void desLikearComentario(int idUsuario, int idComentario) throws ControllerException {
        try {
            comentarioDAO.desLikearComentario(idUsuario, idComentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void operacionContadorComentario(int idComentario, int cantidad) throws ControllerException {
        try {
            comentarioDAO.operacionContadorComentario(idComentario, cantidad);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int consultarCantLikes(int idComentario) throws ControllerException {
        try {
            return comentarioDAO.consultarCantLikes(idComentario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public List<Comentario> consultarComentariosLikeados(int idUsuario) throws ControllerException {
        try {
            return comentarioDAO.consultarComentariosLikeados(idUsuario);
        } catch (ModelException ex) {
            Logger.getLogger(ComentarioFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
