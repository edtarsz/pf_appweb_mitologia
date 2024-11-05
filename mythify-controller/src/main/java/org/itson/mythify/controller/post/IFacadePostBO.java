/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.controller.post;

import java.util.List;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.entidad.Post;

/**
 *
 * @author user
 */
public interface IFacadePostBO {

    public Post crearPostDTO(PostDTO postDTO) throws ControllerException;

    public void eliminarPost() throws ControllerException;

    public void actualizarPost() throws ControllerException;

    public List<Post> consultarPosts() throws ControllerException;

    public List<Post> consultarPostsCategoria(String categoria) throws ControllerException;

}
