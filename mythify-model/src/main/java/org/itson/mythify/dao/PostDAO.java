package org.itson.mythify.dao;

import java.util.logging.Level;
import org.itson.mythify.conexion.IConexion;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import org.itson.mythify.entidad.Post;

/**
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class PostDAO implements IPostDAO {

    private static final Logger logger = Logger.getLogger(PostDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar el `EntityManager`.
     */
    public PostDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }

    @Override
    public Post crearPost(Post post) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to create post: {0}", post.getTitulo());
            entityManager.getTransaction().begin();
            entityManager.persist(post);
            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Post created successfully: {0}", post.getTitulo());
            return post;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error creating user: " + post.getTitulo(), ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            return null;
        }
    }

    @Override
    public Post eliminarPost() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Post actualizarPost() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
