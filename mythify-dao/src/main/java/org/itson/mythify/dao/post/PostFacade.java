/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao.post;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.dao.DAOFactory;
import org.itson.mythify.dao.IPostDAO;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Post;


/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class PostFacade implements IPostFacade {

    private final IPostDAO postDAO;

    public PostFacade(IConexion conexion) {
        this.postDAO = DAOFactory.instanciaPostDAO(conexion);
    }

    @Override
    public Post crearPost(Post post) {
        try {
            return postDAO.crearPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean eliminarPost(Post post) {
        try {
            return postDAO.eliminarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    @Override
    public Post actualizarPost(Post post) {
       try {
            return postDAO.actualizarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Post anclarPost(Post post) {
       try {
            return postDAO.anclarPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<Post> consultarPosts() {
        try {
            return postDAO.consultarPosts();
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Post> consultarPostsCategoria(String categoria) {
        try {
            return postDAO.consultarPostsCategoria(categoria);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Post consultarPostPorID(int id) {
        try {
            return postDAO.consultarPostPorID(id);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
