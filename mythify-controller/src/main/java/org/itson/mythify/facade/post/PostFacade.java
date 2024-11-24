/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.post;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.dao.DAOFactory;
import org.itson.mythify.dao.IPostDAO;
import org.itson.mythify.conexion.ModelException;
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
    private IPostDAO postDAO;

    public PostFacade() {
        conexion = new Conexion();
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
        }
        return null;
    }

    @Override
    public boolean eliminarPost(int idPost) throws ControllerException {
        try {
            return postDAO.eliminarPost(idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Post actualizarPost(Post post) throws ControllerException {
        try {
            return postDAO.actualizarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Post anclarPost(Post post) throws ControllerException {
        try {
            return postDAO.anclarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> consultarPosts() throws ControllerException {
        try {
            return postDAO.consultarPosts();
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> consultarPostsCategoria(String categoria) throws ControllerException {
        try {
            return postDAO.consultarPostsCategoria(categoria);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Post consultarPostPorID(int id) throws ControllerException {
        try {
            return postDAO.consultarPostPorID(id);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void likearPost(int idUsuario, int idPost) throws ControllerException {
        try {
            postDAO.likearPost(idUsuario, idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int consultarCantLikes(int idPost) throws ControllerException {
        try {
            postDAO.consultarCantLikes(idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public void desLikearPost(int idUsuario, int idPost) throws ControllerException {
        try {
            postDAO.desLikearPost(idUsuario, idPost);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void operacionContadorLike(int idPost, int cantidad) throws ControllerException {
        try {
            postDAO.operacionContadorLike(idPost, cantidad);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Post> consultarPostLikeados(int idUsuario) throws ControllerException {
        try {
            return postDAO.consultarPostLikeados(idUsuario);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> consultarHotPosts() throws ControllerException {
        try {
            return postDAO.consultarHotPosts();
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> consultarPostPropios(int idUsuario) throws ControllerException {
        try {
            return postDAO.consultarPostPropios(idUsuario);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
