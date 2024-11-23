/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import org.itson.mythify.conexion.ModelException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.entidad.Comentario;

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
        try {
            logger.log(Level.INFO, "Attempting to create post: {0}", comentario.getContenido());
            entityManager.getTransaction().begin();
            entityManager.persist(comentario);
            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Post created successfully: {0}", comentario.getContenido());
            return comentario;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error creating user: " + comentario.getContenido(), ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            return null;
        }
    }

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

            int deletedCount = entityManager.createQuery(deletePadreQuery).executeUpdate();

            if (deletedCount == 0) {
                logger.log(Level.WARNING, "Comment with ID {0} not found for deletion", idComentario);
                throw new ModelException("No se encontró el comentario con ID: " + idComentario);
            }

            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "Comment with ID {0} successfully deleted", idComentario);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error deleting comment with ID: " + idComentario, ex);

            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error al eliminar el comentario: " + ex.getMessage(), ex);
        }
    }

    @Override
    public void actualizarComentario() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
            logger.log(Level.INFO, "Attempting to query post by ID: {0}", idComentario);

            Comentario comentario = entityManager.find(Comentario.class, idComentario);

            if (comentario != null) {
                logger.log(Level.INFO, "Post found: {0}", comentario.getIdComentario());
            } else {
                logger.log(Level.WARNING, "No post found with ID: {0}", idComentario);
            }

            return comentario;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error querying post by ID: " + idComentario, ex);
            throw new ModelException("Error al consultar el post por ID: " + ex.getMessage(), ex);
        }
    }
}
