/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.post;

import java.util.List;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.controller.ConversorBO;
import org.itson.mythify.controller.IConversorBO;
import org.itson.mythify.dao.post.IPostFacade;
import org.itson.mythify.dao.post.PostFacade;
import org.itson.mythify.entidad.Post;

/**
 *
 * @author user
 */
public class FacadePostBO implements IFacadePostBO {

    IPostFacade postFacade;
    IConexion conexion;
    IConversorBO conversorBO;

    public FacadePostBO() {
        conexion = new Conexion();
        conversorBO = new ConversorBO();
        postFacade = new PostFacade(conexion);
    }

    @Override
    public Post crearPostDTO(PostDTO postDTO) throws ControllerException {
        Post post = conversorBO.postDTOAEntidad(postDTO);
        return postFacade.crearPost(post);
    }

    @Override
    public boolean eliminarPost(PostDTO postDTO) throws ControllerException {
        Post post = conversorBO.postDTOAEntidad(postDTO);
        return postFacade.eliminarPost(post);
    }

    @Override
    public Post actualizarPost(Post post) throws ControllerException {
        return postFacade.actualizarPost(post);
    }

    @Override
    public List<Post> consultarPosts() throws ControllerException {
        return postFacade.consultarPosts();
    }

    @Override
    public List<Post> consultarPostsCategoria(String categoria) throws ControllerException {
        return postFacade.consultarPostsCategoria(categoria);
    }

    @Override
    public Post consultarPostPorID(int id) throws ControllerException {
        return postFacade.consultarPostPorID(id);
    }

}
