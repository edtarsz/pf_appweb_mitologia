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
import org.itson.mythify.dao.ModelException;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

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
    public void crearPost(Post post) {
        try {
            postDAO.crearPost(post);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void eliminarPost() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void actualizarPost() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Post> consultarPosts(Usuario usuario) {
        try {
            return postDAO.consultarPosts(usuario);
        } catch (ModelException ex) {
            Logger.getLogger(PostFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
