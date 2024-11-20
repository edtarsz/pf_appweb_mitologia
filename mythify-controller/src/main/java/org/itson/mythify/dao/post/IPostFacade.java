/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao.post;

import java.util.List;
import org.itson.mythify.controller.ControllerException;
import org.itson.mythify.entidad.Post;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IPostFacade {

    public Post crearPost(Post post) throws ControllerException;

    public boolean eliminarPost(int idPost) throws ControllerException;

    public Post actualizarPost(Post post) throws ControllerException;

    public Post anclarPost(Post post) throws ControllerException;

    public List<Post> consultarPosts() throws ControllerException;

    public List<Post> consultarPostsCategoria(String categoria) throws ControllerException;

    public Post consultarPostPorID(int id) throws ControllerException;

}
