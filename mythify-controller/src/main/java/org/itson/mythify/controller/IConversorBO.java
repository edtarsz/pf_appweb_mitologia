/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public interface IConversorBO {

    public UsuarioDTO entidadAUsuarioDTO(Usuario usuario) throws ControllerException;

    public Usuario usuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException;

    public PostDTO entidadAPostDTO(Post post) throws ControllerException;

    public Post postDTOAEntidad(PostDTO postDTO) throws ControllerException;
    
    public ComentarioDTO entidadAComentarioDTO(Comentario comentario) throws ControllerException;

    public Comentario comentarioDTOAEntidad(ComentarioDTO comentarioDTO) throws ControllerException;
}
