/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.comentario;

import java.util.List;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.controller.ConversorBO;
import org.itson.mythify.controller.IConversorBO;
import org.itson.mythify.dao.comentario.ComentarioFacade;
import org.itson.mythify.dao.comentario.IComentarioFacade;
import org.itson.mythify.entidad.Comentario;

/**
 *
 * @author user
 */
public class FacadeComentarioBO implements IFacadeComentarioBO {

    IComentarioFacade comentarioFacade;
    IConexion conexion;
    IConversorBO conversorBO;

    public FacadeComentarioBO() {
        conexion = new Conexion();
        conversorBO = new ConversorBO();
        comentarioFacade = new ComentarioFacade(conexion);
    }

    @Override
    public Comentario crearComentarioDTO(ComentarioDTO comentarioDTO) throws ControllerException {
        Comentario comentario = conversorBO.comentarioDTOAEntidad(comentarioDTO);
        return comentarioFacade.crearComentario(comentario);
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
    public List<Comentario> consultarComentarios() throws ControllerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
