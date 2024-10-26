package org.itson.mythify.DAO;

import org.itson.mythify.Conexion.IConexion;
import org.itson.mythify.Entidad.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    EntityManager entityManager;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar el `EntityManager`.
     */
    public UsuarioDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        logger.info("UsuarioDAO initialized with a new EntityManager.");
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to create user: {0}", usuario.getCorreo());
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            logger.log(Level.INFO, "User created successfully: {0}", usuario.getCorreo());
            return usuario;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error creating user: " + usuario.getCorreo(), ex);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            return null;
        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
                logger.info("EntityManager closed.");
            }
        }
    }

    @Override
    public Usuario eliminarUsuario() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario actualizarUsuario() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario consultarUsuario(String correo, String password) throws ModelException {
        try {
            logger.log(Level.INFO, "Consulting user with email: {0}", correo);
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            Predicate correoPredicate = criteriaBuilder.equal(root.get("correo"), correo);
            Predicate passwordPredicate = criteriaBuilder.equal(root.get("contrasenia"), password);

            criteriaQuery.select(root).where(criteriaBuilder.and(correoPredicate, passwordPredicate));
            Usuario usuario = entityManager.createQuery(criteriaQuery).getSingleResult();
            logger.log(Level.INFO, "User found: {0}", usuario.getCorreo());
            return usuario;
        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No user found with email: {0}", correo);
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error consulting user: " + correo, ex);
            return null;
        }
    }
}
