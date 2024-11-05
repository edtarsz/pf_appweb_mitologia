package org.itson.mythify.dao;

import java.util.List;
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

    @Override
    public List<Post> consultarPosts() throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query all posts");
            return entityManager.createQuery("SELECT p FROM Post p", Post.class)
                    .getResultList();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error querying all posts", ex);
            throw new ModelException("Error al consultar todos los posts: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Post> consultarPostsCategoria(String categoria) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query posts for category: {0}", categoria);
            return entityManager.createQuery("SELECT p FROM Post p WHERE p.categoria = :categoria", Post.class)
                    .setParameter("categoria", categoria)
                    .getResultList();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error querying posts for category: " + categoria, ex);
            throw new ModelException("Error al consultar los posts por categoría: " + ex.getMessage(), ex);
        }
    }

}
