/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Usuario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class ComentarioDAO implements IComentarioDAO {

    private static final Logger logger = Logger.getLogger(ComentarioDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `ComentarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar el `EntityManager`.
     */
    public ComentarioDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("PostDAO initialized with a new EntityManager.");
    }

    @Override
    public Comentario crearComentario(Comentario comentario) throws ModelException {
        if (comentario == null) {
            throw new ModelException("El comentario no puede ser null");
        }

        if (comentario.getContenido() == null || comentario.getContenido().isEmpty()) {
            throw new ModelException("El contenido del comentario no puede estar vacío");
        }

        if (comentario.getUsuario() == null) {
            throw new ModelException("El usuario del comentario no puede ser nulo");
        }

        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(comentario);
            transaction.commit();
            logger.log(Level.INFO, "Comentario creado exitosamente: {0}", comentario.getContenido());
            return comentario;

        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error de persistencia al crear comentario", e);
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new ModelException("Error al persistir el comentario en la base de datos", e);
        }
    }

    @Override
    public void eliminarComentario(int idComentario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to delete comment with ID: {0}", idComentario);

            entityManager.getTransaction().begin();

            // Obtener referencia al comentario padre
            Comentario comentarioPadre = entityManager.getReference(Comentario.class, idComentario);

            // Eliminar comentarios hijos
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaDelete<Comentario> deleteHijosQuery = criteriaBuilder.createCriteriaDelete(Comentario.class);
            Root<Comentario> hijosRoot = deleteHijosQuery.from(Comentario.class);

            deleteHijosQuery.where(criteriaBuilder.equal(hijosRoot.get("comentarioPadre"), comentarioPadre));

            int deletedHijosCount = entityManager.createQuery(deleteHijosQuery).executeUpdate();
            logger.log(Level.INFO, "Deleted {0} child comments for comment ID: {1}", new Object[]{deletedHijosCount, idComentario});

            // Eliminar comentario padre
            CriteriaDelete<Comentario> deletePadreQuery = criteriaBuilder.createCriteriaDelete(Comentario.class);
            Root<Comentario> padreRoot = deletePadreQuery.from(Comentario.class);

            deletePadreQuery.where(criteriaBuilder.equal(padreRoot.get("idComentario"), idComentario));

            // Aquí está el problema. Necesitas ejecutar este query.
            int deletedPadreCount = entityManager.createQuery(deletePadreQuery).executeUpdate();
            logger.log(Level.INFO, "Deleted {0} parent comment with ID: {1}", new Object[]{deletedPadreCount, idComentario});

            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Comment with ID {0} successfully deleted", idComentario);

        } catch (IllegalArgumentException ex) {
            logger.log(Level.WARNING, "Comment with ID {0} not found for deletion", idComentario);
            throw new ModelException("No se encontró el comentario con ID: " + idComentario, ex);
        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Persistence error deleting comment with ID: " + idComentario, ex);

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error de persistencia al eliminar el comentario: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Comentario> consultarComentarios(int idPost) throws ModelException {
        try {
            logger.log(Level.INFO, "Fetching comments for post with id: {0}", idPost);

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Comentario> criteriaQuery = criteriaBuilder.createQuery(Comentario.class);

            Root<Comentario> root = criteriaQuery.from(Comentario.class);

            Predicate condition = criteriaBuilder.equal(root.get("post").get("idPost"), idPost);

            criteriaQuery.select(root).where(condition);

            List<Comentario> comentarios = entityManager.createQuery(criteriaQuery).getResultList();

            logger.log(Level.INFO, "Found {0} comments for post with id: {1}", new Object[]{comentarios.size(), idPost});

            return comentarios;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error fetching comments for post with id: " + idPost, ex);
            throw new ModelException("Error fetching comments for post with id: " + idPost, ex);
        }
    }

    @Override
    public Comentario consultarComentarioPorID(int idComentario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query comment by ID: {0}", idComentario);

            Comentario comentario = entityManager.find(Comentario.class, idComentario);

            if (comentario != null) {
                logger.log(Level.INFO, "Comment found: {0}", comentario.getIdComentario());
            } else {
                logger.log(Level.WARNING, "No comment found with ID: {0}", idComentario);
            }

            return comentario;
        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error querying comment by ID: " + idComentario, ex);
            throw new ModelException("Error al consultar el comentario por ID: " + idComentario, ex);
        }
    }

    @Override
    public void likearComentario(int idUsuario, int idComentario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to like comment {0} by user {1}", new Object[]{idComentario, idUsuario});
            entityManager.getTransaction().begin();

            // Buscar al usuario por ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new ModelException("No se encontró al usuario con ID: " + idUsuario);
            }

            // Buscar el comentario por ID
            Comentario comentario = entityManager.find(Comentario.class, idComentario);
            if (comentario == null) {
                throw new ModelException("No se encontró el comentario con ID: " + idComentario);
            }

            // Agregar el comentario a la lista de comentarios likeados del usuario
            if (!usuario.getComentariosLikeados().contains(comentario)) {
                usuario.getComentariosLikeados().add(comentario);
                logger.log(Level.INFO, "Comment {0} liked by user {1}", new Object[]{idComentario, idUsuario});
            } else {
                logger.log(Level.WARNING, "User {0} already liked comment {1}", new Object[]{idUsuario, idComentario});
            }

            // Actualizar el usuario en la base de datos
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();

        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error liking comment: " + idComentario + " by user: " + idUsuario, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new PersistenceException("Error al dar like al comentario: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void desLikearComentario(int idUsuario, int idComentario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to unlike comment {0} by user {1}", new Object[]{idComentario, idUsuario});
            entityManager.getTransaction().begin();

            // Buscar al usuario por ID
            Usuario usuario = entityManager.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new ModelException("No se encontró al usuario con ID: " + idUsuario);
            }

            // Buscar el comentario por ID
            Comentario comentario = entityManager.find(Comentario.class, idComentario);
            if (comentario == null) {
                throw new ModelException("No se encontró el comentario con ID: " + idComentario);
            }

            // Verificar si el usuario ya había dado like al comentario
            if (usuario.getComentariosLikeados().contains(comentario)) {
                // Eliminar el comentario de la lista de comentarios likeados
                usuario.getComentariosLikeados().remove(comentario);
                logger.log(Level.INFO, "Comment {0} unliked by user {1}", new Object[]{idComentario, idUsuario});
            } else {
                // Si el usuario no ha dado like al comentario
                logger.log(Level.WARNING, "User {0} has not liked comment {1}", new Object[]{idUsuario, idComentario});
            }

            // Actualizar el usuario en la base de datos
            entityManager.merge(usuario);
            entityManager.getTransaction().commit();

        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error unliking comment: " + idComentario + " by user: " + idUsuario, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new PersistenceException("Error al quitar el like del comentario: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void operacionContadorComentario(int idComentario, int cantidad) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to increment like count for comment {0}", idComentario);
            entityManager.getTransaction().begin();

            // Buscar el comentario por ID
            Comentario comentario = entityManager.find(Comentario.class, idComentario);
            if (comentario == null) {
                throw new ModelException("No se encontró el comentario con ID: " + idComentario);
            }

            // Incrementar el contador de likes
            comentario.setCantLikes(comentario.getCantLikes() + cantidad);
            logger.log(Level.INFO, "Incremented like count for comment {0} to {1}",
                    new Object[]{idComentario, comentario.getCantLikes()});

            // Actualizar el comentario en la base de datos
            entityManager.merge(comentario);
            entityManager.getTransaction().commit();

        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error incrementing like count for comment: " + idComentario, ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new PersistenceException("Error al aumentar el contador de likes: " + ex.getMessage(), ex);
        }
    }

    @Override
    public int consultarCantLikes(int idComentario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to get like count for comment {0}", idComentario);

            // Buscar el comentario por ID
            Comentario comentario = entityManager.find(Comentario.class, idComentario);
            if (comentario == null) {
                throw new ModelException("No se encontró el comentario con ID: " + idComentario);
            }

            logger.log(Level.INFO, "Like count for comment {0} is {1}",
                    new Object[]{idComentario, comentario.getCantLikes()});

            // Retornar la cantidad de likes
            return comentario.getCantLikes();
        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error retrieving like count for comment: " + idComentario, ex);
            throw new PersistenceException("Error al consultar la cantidad de likes: " + ex.getMessage(), ex);
        }
    }

    @Override
    public List<Comentario> consultarComentariosLikeados(int idUsuario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to query liked comments by user {0}", idUsuario);

            if (entityManager == null) {
                throw new ModelException("EntityManager is not initialized.");
            }

            // Use Criteria API to fetch liked comments for the user
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Comentario> cq = cb.createQuery(Comentario.class);
            Root<Usuario> usuarioRoot = cq.from(Usuario.class);
            Join<Usuario, Comentario> comentarioJoin = usuarioRoot.join("comentariosLikeados");
            cq.select(comentarioJoin).where(cb.equal(usuarioRoot.get("idUsuario"), idUsuario));

            return entityManager.createQuery(cq).getResultList();
        } catch (PersistenceException ex) {
            logger.log(Level.SEVERE, "Error querying liked comments by user: " + idUsuario, ex);
            throw new ModelException("Error al consultar los comentarios likeados: " + ex.getMessage(), ex);
        }
    }
}
