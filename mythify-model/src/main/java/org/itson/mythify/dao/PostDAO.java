package org.itson.mythify.dao;

import org.itson.mythify.conexion.ModelException;
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
    public Post actualizarPost(Post post) throws ModelException {
         try {
        logger.log(Level.INFO, "Attempting to update post: {0}", post.getTitulo());
        entityManager.getTransaction().begin();
        Post updatedPost = entityManager.merge(post); 
        entityManager.getTransaction().commit();
        logger.log(Level.INFO, "Post updated successfully: {0}", updatedPost.getTitulo());
        return updatedPost;
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error updating post: " + post.getTitulo(), ex);
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            logger.warning("Transaction rolled back.");
        }
        throw new ModelException("Error updating post", ex);
    }
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

    @Override
    public Post consultarPostPorID(int id) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query post by ID: {0}", id);

            Post post = entityManager.find(Post.class, id);

            if (post != null) {
                logger.log(Level.INFO, "Post found: {0}", post.getTitulo());
            } else {
                logger.log(Level.WARNING, "No post found with ID: {0}", id);
            }

            return post;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error querying post by ID: " + id, ex);
            throw new ModelException("Error al consultar el post por ID: " + ex.getMessage(), ex);
        }
    }

    @Override
    public boolean eliminarPost(Post post) throws ModelException {
       try {
        logger.log(Level.INFO, "Attempting to delete post: {0}", post.getTitulo());
        entityManager.getTransaction().begin();
        Post postToDelete = entityManager.find(Post.class, post.getIdPost()); // Se busca el post por su ID
        if (postToDelete != null) {
            entityManager.remove(postToDelete); // Se elimina el post
            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Post deleted successfully: {0}", post.getTitulo());
            return true;
        } else {
            logger.log(Level.WARNING, "Post not found: {0}", post.getTitulo());
            entityManager.getTransaction().rollback();
            return false;
        }
    } catch (Exception ex) {
        logger.log(Level.SEVERE, "Error deleting post: " + post.getTitulo(), ex);
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
            logger.warning("Transaction rolled back.");
        }
        throw new ModelException("Error deleting post", ex);
    }
    }

}
