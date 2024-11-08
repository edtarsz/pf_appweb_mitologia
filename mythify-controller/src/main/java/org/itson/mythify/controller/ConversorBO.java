/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller;

import org.itson.mythify.controller.comentario.ComentarioDTO;
import org.itson.mythify.controller.post.PostDTO;
import org.itson.mythify.controller.usuario.UsuarioDTO;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
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
                usuario.getNombre(),
                usuario.getApellidoPaterno(),
                usuario.getApellidoMaterno(),
                usuario.getCorreo(),
                usuario.getContrasenia(),
                usuario.getTelefono(),
                usuario.getAvatar(),
                usuario.getCiudad(),
                usuario.getFechaNacimiento(),
                usuario.getGenero(),
                usuario.getTipoUsuario(),
                usuario.getPermiso(),
                usuario.getMunicipio());
    }

    @Override
    public Usuario usuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException {
        if (usuarioDTO == null) {
            throw new ControllerException("El UsuarioDTO no puede ser nulo");
        }

        return new Usuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellidoPaterno(),
                usuarioDTO.getApellidoMaterno(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getContrasenia(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getAvatar(),
                usuarioDTO.getCiudad(),
                usuarioDTO.getFechaNacimiento(),
                usuarioDTO.getGenero(),
                usuarioDTO.getTipoUsuario(),
                usuarioDTO.getPermiso(),
                usuarioDTO.getMunicipio());
    }

    @Override
    public PostDTO entidadAPostDTO(Post post) throws ControllerException {
        if (post == null) {
            throw new ControllerException("El post no puede ser nulo");
        }

        return new PostDTO(
                post.getTitulo(),
                post.getContenido(),
                post.getCategoria(),
                post.getFechaHoraCreacion(),
                post.getFechaHoraEdicion(),
                post.isAnclado(),
                post.getUsuario()
        );
    }

    @Override
    public Post postDTOAEntidad(PostDTO postDTO) throws ControllerException {
        if (postDTO == null) {
            throw new ControllerException("El PostDTO no puede ser nulo");
        }

        Post post = new Post();
        post.setTitulo(postDTO.getTitulo());
        post.setContenido(postDTO.getContenido());
        post.setCategoria(postDTO.getCategoria());
        post.setFechaHoraCreacion(postDTO.getFechaHoraCreacion());
        post.setFechaHoraEdicion(postDTO.getFechaHoraEdicion());
        post.setAnclado(postDTO.isAnclado());
        post.setUsuario(postDTO.getUsuario());

        return post;
    }

    @Override
    public ComentarioDTO entidadAComentarioDTO(Comentario comentario) throws ControllerException {
        if (comentario == null) {
            throw new ControllerException("El comentario no puede ser nulo");
        }

        ComentarioDTO comentarioDTO = new ComentarioDTO(
                comentario.getContenido(),
                comentario.getFechaHora(),
                comentario.getUsuario(),
                comentario.getComentarioPadre(),
                comentario.getPost());

        return comentarioDTO;
    }

    @Override
    public Comentario comentarioDTOAEntidad(ComentarioDTO comentarioDTO) throws ControllerException {
        if (comentarioDTO == null) {
            throw new ControllerException("El comentarioDTO no puede ser nulo");
        }

        Comentario comentario = new Comentario(
                comentarioDTO.getContenido(),
                comentarioDTO.getFechaHora(),
                comentarioDTO.getUsuario(),
                comentarioDTO.getComentarioPadre(),
                comentarioDTO.getPost());

        return comentario;
    }

}
