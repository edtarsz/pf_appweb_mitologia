/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.facade.usuario;

import org.itson.mythify.exceptions.ControllerException;
import org.itson.mythify.entidad.Usuario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IUsuarioFacade {

    public void crearUsuario(Usuario usuario) throws ControllerException;

    public void eliminarUsuario() throws ControllerException;

    public void actualizarUsuario() throws ControllerException;

    public Usuario consultarUsuario(String correo, String password) throws ControllerException;

}
