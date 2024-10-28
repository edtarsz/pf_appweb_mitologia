/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
public class ConversorBO implements IConversorBO {

    public ConversorBO() {
    }

    @Override
    public UsuarioDTO entidadAUsuarioDTO(Usuario usuario) throws ControllerException {
        if (usuario == null) {
            throw new ControllerException("El usuario no puede ser nulo");
        }

        return new UsuarioDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellidoPaterno(),
                usuario.getApellidoMaterno(),
                usuario.getCorreo(),
                usuario.getContrasenia(),
                usuario.getTelefono(),
                usuario.getAvatar(),
                usuario.getCiudad(),
                usuario.getGenero(),
                usuario.getFechaNacimiento(),
                usuario.getMunicipio(),
                usuario.getTipoUsuario(),
                entidadAPermisoDTO(usuario.getPermiso())
        );
    }

    @Override
    public Usuario usuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException {
        if (usuarioDTO == null) {
            throw new ControllerException("El UsuarioDTO no puede ser nulo");
        }

        return new Usuario(
                usuarioDTO.getId(),
                usuarioDTO.getNombre(),
                usuarioDTO.getApellidoPaterno(),
                usuarioDTO.getApellidoMaterno(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getEncryptedPassword(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getAvatar(),
                usuarioDTO.getCiudad(),
                usuarioDTO.getFechaNacimiento(),
                usuarioDTO.getGenero(),
                usuarioDTO.getTipoUsuario(),
                usuarioDTO.getMunicipio(),
                permisoDTOAEntidad(usuarioDTO.getPermiso())
        );
    }

    @Override
    public PermisoDTO entidadAPermisoDTO(Permiso permiso) throws ControllerException{
        if (permiso == null) {
            return null;
        }
        return new PermisoDTO(
                permiso.getIdPermiso(),
                permiso.getTipoPermiso()
        );
    }

    @Override
    public Permiso permisoDTOAEntidad(PermisoDTO permisoDTO) throws ControllerException{
        if (permisoDTO == null) {
            return null;
        }
        Permiso permiso = new Permiso();
        permiso.setIdPermiso(permisoDTO.getIdPermiso());
        permiso.setTipoPermiso(permisoDTO.getTipoPermiso());
        return permiso;
    }
}
