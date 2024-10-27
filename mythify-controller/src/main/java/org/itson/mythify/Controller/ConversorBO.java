/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.Controller;

import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public class ConversorBO implements IConversorBO {

    public ConversorBO() {
    }

    @Override
    public UsuarioDTO EntidadAUsuarioDTO(Usuario usuario) throws ControllerException {
        return new UsuarioDTO(
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
                usuario.getMunicipio());
    }

    @Override
    public Usuario UsuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException {
        return new Usuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellidoPaterno(),
                usuarioDTO.getApellidoMaterno(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getEncryptedPassword(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getAvatar(),
                usuarioDTO.getCiudad(),
                usuarioDTO.getGenero(),
                usuarioDTO.getFechaNacimiento(),
                usuarioDTO.getMunicipio());
    }
}
