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

    private final IComentarioDAO comentarioDAO;
    IConexion conexion;

    public ComentarioFacade() {
        conexion = new Conexion();
        this.comentarioDAO = DAOFactory.instanciaComentarioDAO(conexion);
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
    public void eliminarComentario() throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarComentario() throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

}
