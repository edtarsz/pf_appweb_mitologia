/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.usuario;

import org.itson.mythify.dao.usuario.IUsuarioFacade;
import org.itson.mythify.dao.usuario.UsuarioFacade;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.controller.ConversorBO;
import org.itson.mythify.controller.IConversorBO;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public class FacadeUsuarioBO implements IFacadeUsuarioBO {

    IUsuarioFacade usuarioFacade;
    IConexion conexion;
    IConversorBO conversorBO;

    public FacadeUsuarioBO() {
        conexion = new Conexion();
        conversorBO = new ConversorBO();
        usuarioFacade = new UsuarioFacade(conexion);
    }

    @Override
    public void crearUsuarioDTO(UsuarioDTO usuarioDTO) throws ControllerException {
        Usuario usuario = conversorBO.usuarioDTOAEntidad(usuarioDTO);
        usuarioFacade.crearUsuario(usuario);
    }

    @Override
    public void eliminarUsuario() throws ControllerException {
        usuarioFacade.eliminarUsuario();
    }

    @Override
    public void actualizarUsuario() throws ControllerException {
        usuarioFacade.actualizarUsuario();
    }

    @Override
    public UsuarioDTO consultarUsuario(String correo, String password) throws ControllerException {
        Usuario usuario = usuarioFacade.consultarUsuario(correo, password);
        UsuarioDTO usuarioNuevo = conversorBO.entidadAUsuarioDTO(usuario);
        return usuarioNuevo;
    }

    @Override
    public Usuario consultarUsuarioSession(String correo, String password) throws ControllerException {
        Usuario usuario = usuarioFacade.consultarUsuarioSession(correo, password);
        return usuario;
    }
}