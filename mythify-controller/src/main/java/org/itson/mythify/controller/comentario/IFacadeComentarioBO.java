/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller.comentario;

import java.util.List;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.entidad.Comentario;

/**
 *
 * @author user
 */
public interface IFacadeComentarioBO {

    public Comentario crearComentarioDTO(ComentarioDTO comentarioDTO) throws ControllerException;

    public void eliminarComentario() throws ControllerException;

    public void actualizarComentario() throws ControllerException;

    public List<Comentario> consultarComentarios() throws ControllerException;

}
