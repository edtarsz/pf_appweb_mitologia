/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.facade.comentario;

import java.util.List;
import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.entidad.Comentario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IComentarioFacade {

    public Comentario crearComentario(Comentario comentario) throws ControllerException;

    public void eliminarComentario() throws ControllerException;

    public void actualizarComentario() throws ControllerException;

    public List<Comentario> consultarComentarios() throws ControllerException;

}
