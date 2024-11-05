/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller.usuario;

import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public interface IFacadeUsuarioBO {

    public void crearUsuarioDTO(UsuarioDTO usuarioDTO) throws ControllerException;

    public void eliminarUsuario() throws ControllerException;

    public void actualizarUsuario() throws ControllerException;

    public UsuarioDTO consultarUsuario(String correo, String password) throws ControllerException;

    public Usuario consultarUsuarioSession(String correo, String password) throws ControllerException;

    public boolean usuarioExiste(String correo, String password) throws ControllerException;

}
