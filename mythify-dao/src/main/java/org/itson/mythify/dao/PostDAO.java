package org.itson.mythify.dao;

import java.util.ArrayList;
import org.itson.mythify.conexion.ModelException;
import java.util.List;
import java.util.logging.Level;
import org.itson.mythify.conexion.IConexion;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
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
    public Post anclarPost(Post post) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to pin post: {0}", post.getTitulo());
            entityManager.getTransaction().begin();
            Post updatedPost = entityManager.merge(post);
            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Post pinned successfully: {0}", updatedPost.getTitulo());
            return updatedPost;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error pinnig post: " + post.getTitulo(), ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error pinning post", ex);
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
    public boolean eliminarPost(int idPost) throws ModelException {
        try {
            entityManager.getTransaction().begin();

            Post post = entityManager.find(Post.class, idPost);
            if (post != null) {
                // First remove all likes from the join table using native query with correct parameter binding
                entityManager.createNativeQuery("DELETE FROM Usuario_PostLikes WHERE idPost = ?")
                        .setParameter(1, idPost)
                        .executeUpdate();

                // Then handle comments
                for (Comentario comentario : new ArrayList<>(post.getComentarios())) {
                    // Remove comment likes with correct parameter binding
                    entityManager.createNativeQuery("DELETE FROM Usuario_ComentarioLikes WHERE idComentario = ?")
                            .setParameter(1, comentario.getIdComentario())
                            .executeUpdate();

                    // Reset the likes count
                    comentario.setCantLikes(0);
                }

                // Reset post likes count
                post.setCantLikes(0);

                // Finally remove the post
                entityManager.remove(post);
            }

            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new ModelException("Error deleting post: " + e.getMessage(), e);
        }
    }

    @Override
    public void likearPost(int idUsuario, int idPost) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to like post {0} by user {1}", new Object[]{idPost, idUsuario});
            entityManager.getTransaction().begin();

            // Buscar al usuario por ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new ModelException("No se encontró al usuario con ID: " + idUsuario);
            }

            // Buscar el post por ID
            Post post = entityManager.find(Post.class, idPost);
            if (post == null) {
                throw new ModelException("No se encontró el post con ID: " + idPost);
            }

            // Agregar el post a la lista de posts likeados del usuario
            if (!usuario.getPostsLikeados().contains(post)) {
                usuario.getPostsLikeados().add(post);
                logger.log(Level.INFO, "Post {0} liked by user {1}", new Object[]{idPost, idUsuario});
            } else {
                logger.log(Level.WARNING, "User {0} already liked post {1}", new Object[]{idUsuario, idPost});
            }

            // Actualizar el usuario en la base de datos
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();

        } catch (ModelException ex) {
            logger.log(Level.SEVERE, "Error liking post: " + idPost + " by user: " + idUsuario, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error al dar like al post: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void desLikearPost(int idUsuario, int idPost) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to unlike post {0} by user {1}", new Object[]{idPost, idUsuario});
            entityManager.getTransaction().begin();

            // Buscar al usuario por ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new ModelException("No se encontró al usuario con ID: " + idUsuario);
            }

            // Buscar el post por ID
            Post post = entityManager.find(Post.class, idPost);
            if (post == null) {
                throw new ModelException("No se encontró el post con ID: " + idPost);
            }

            // Verificar si el usuario ya había dado like al post
            if (usuario.getPostsLikeados().contains(post)) {
                // Eliminar el post de la lista de posts likeados
                usuario.getPostsLikeados().remove(post);
                logger.log(Level.INFO, "Post {0} unliked by user {1}", new Object[]{idPost, idUsuario});
            } else {
                // Si el usuario no ha dado like al post
                logger.log(Level.WARNING, "User {0} has not liked post {1}", new Object[]{idUsuario, idPost});
            }

            // Actualizar el usuario en la base de datos
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();

        } catch (ModelException ex) {
            logger.log(Level.SEVERE, "Error unliking post: " + idPost + " by user: " + idUsuario, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error al quitar el like del post: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void operacionContadorLike(int idPost, int cantidad) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to increment like count for post {0}", idPost);
            entityManager.getTransaction().begin();

            // Buscar el post por ID
            Post post = entityManager.find(Post.class, idPost);
            if (post == null) {
                throw new ModelException("No se encontró el post con ID: " + idPost);
            }

            // Incrementar el contador de likes
            post.setCantLikes(post.getCantLikes() + cantidad);
            logger.log(Level.INFO, "Incremented like count for post {0} to {1}", new Object[]{idPost, post.getCantLikes()});

            // Actualizar el post en la base de datos
            entityManager.merge(post);
            entityManager.getTransaction().commit();

        } catch (ModelException ex) {
            logger.log(Level.SEVERE, "Error incrementing like count for post: " + idPost, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error al aumentar el contador de likes: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int consultarCantLikes(int idPost) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to get like count for post {0}", idPost);

            // Buscar el post por ID
            Post post = entityManager.find(Post.class, idPost);
            if (post == null) {
                throw new ModelException("No se encontró el post con ID: " + idPost);
            }

            logger.log(Level.INFO, "Like count for post {0} is {1}", new Object[]{idPost, post.getCantLikes()});

            // Retornar la cantidad de likes
            return post.getCantLikes();
        } catch (ModelException ex) {
            logger.log(Level.SEVERE, "Error retrieving like count for post: " + idPost, ex);
            throw new ModelException("Error al consultar la cantidad de likes: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Post> consultarPostLikeados(int idUsuario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query liked posts by user {0}", idUsuario);

            // Buscar al usuario por ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new ModelException("No se encontró al usuario con ID: " + idUsuario);
            }

            // Obtener la lista de posts likeados desde el objeto Usuario
            List<Post> postsLikeados = usuario.getPostsLikeados();

            // Log del resultado
            logger.log(Level.INFO, "User {0} has liked {1} posts.", new Object[]{idUsuario, postsLikeados.size()});

            return postsLikeados;
        } catch (ModelException ex) {
            logger.log(Level.SEVERE, "Error querying liked posts by user: " + idUsuario, ex);
            throw new ModelException("Error al consultar los posts likeados: " + ex.getMessage(), ex);
        }
    }

}
