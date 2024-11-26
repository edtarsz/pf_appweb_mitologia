/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.post;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Persistence;

import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.DAOFactory;
import org.itson.mythify.dao.IPostDAO;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.exceptions.ControllerException;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class PostFacade implements IPostFacade {

    private final IConexion conexion;
    IPostDAO postDAO;

    public PostFacade() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        try {
            this.postDAO = DAOFactory.instanciaPostDAO(conexion);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Post crearPost(Post post) throws ControllerException {
        try {
            return postDAO.crearPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error creating post", ex);
        }
    }

    @Override
    public boolean eliminarPost(int idPost) throws ControllerException {
        try {
            return postDAO.eliminarPost(idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error deleting post", ex);
        }
    }

    @Override
    public Post actualizarPost(Post post) throws ControllerException {
        try {
            return postDAO.actualizarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error updating post", ex);
        }
    }

    @Override
    public Post anclarPost(Post post) throws ControllerException {
        try {
            return postDAO.anclarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error pinning post", ex);
        }
    }

    @Override
    public List<Post> consultarPosts() throws ControllerException {
        try {
            return postDAO.consultarPosts();
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching posts", ex);
        }
    }

    @Override
    public List<Post> consultarPostsCategoria(String categoria) throws ControllerException {
        try {
            return postDAO.consultarPostsCategoria(categoria);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching posts by category", ex);
        }
    }

    @Override
    public Post consultarPostPorID(int id) throws ControllerException {
        try {
            return postDAO.consultarPostPorID(id);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching post by ID", ex);
        }
    }

    @Override
    public void likearPost(int idUsuario, int idPost) throws ControllerException {
        try {
            postDAO.likearPost(idUsuario, idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error liking post", ex);
        }
    }

    @Override
    public int consultarCantLikes(int idPost) throws ControllerException {
        try {
            return postDAO.consultarCantLikes(idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching like count", ex);
        }
    }

    @Override
    public void desLikearPost(int idUsuario, int idPost) throws ControllerException {
        try {
            postDAO.desLikearPost(idUsuario, idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error unliking post", ex);
        }
    }

    @Override
    public void operacionContadorLike(int idPost, int cantidad) throws ControllerException {
        try {
            postDAO.operacionContadorLike(idPost, cantidad);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error updating like count", ex);
        }
    }

    @Override
    public List<Post> consultarPostLikeados(int idUsuario) throws ControllerException {
        try {
            return postDAO.consultarPostLikeados(idUsuario);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching liked posts", ex);
        }
    }

    @Override
    public List<Post> consultarHotPosts() throws ControllerException {
        try {
            return postDAO.consultarHotPosts();
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching hot posts", ex);
        }
    }

    @Override
    public List<Post> consultarPostPropios(int idUsuario) throws ControllerException {
        try {
            return postDAO.consultarPostPropios(idUsuario);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw new ControllerException("Error fetching own posts", ex);
        }
    }

}
