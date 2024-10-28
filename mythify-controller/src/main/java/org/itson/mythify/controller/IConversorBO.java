/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller;

import org.itson.mythify.controller.dto.PermisoDTO;
import org.itson.mythify.controller.dto.UsuarioDTO;
import org.itson.mythify.entidad.Permiso;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public interface IConversorBO {

    public UsuarioDTO entidadAUsuarioDTO(Usuario usuario) throws ControllerException;

    public Usuario usuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException;
    
    public PermisoDTO entidadAPermisoDTO(Permiso permiso) throws ControllerException;
    
    public  Permiso permisoDTOAEntidad(PermisoDTO permisoDTO) throws ControllerException;
}
