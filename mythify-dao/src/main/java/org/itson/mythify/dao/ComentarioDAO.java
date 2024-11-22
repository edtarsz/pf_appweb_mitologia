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

    @Override
    public void eliminarComentario() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
}
