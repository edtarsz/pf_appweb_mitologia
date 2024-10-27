/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller;

import org.itson.mythify.controller.usuario.UsuarioDTO;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public interface IConversorBO {

    public UsuarioDTO EntidadAUsuarioDTO(Usuario usuario) throws ControllerException;

    public Usuario UsuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException;
}
