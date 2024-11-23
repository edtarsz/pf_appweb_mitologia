package org.itson.mythify.dao;

import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.entidad.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class UsuarioDAO implements IUsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());
    private final EntityManager entityManager;
    private final StrongPasswordEncryptor passwordEncryptor;

    /**
     * Constructor para inicializar la clase `UsuarioDAO`.
     *
     * @param conexion La conexión a la base de datos utilizada para inicializar el `EntityManager`.
     */
    public UsuarioDAO(IConexion conexion) {
        this.entityManager = conexion.crearConexion();
        this.passwordEncryptor = new StrongPasswordEncryptor();
        logger.info("UsuarioDAO initialized with a new EntityManager and PasswordEncryptor.");
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) throws ModelException {
        try {
            logger.log(Level.INFO, "Attempting to create user: {0}", usuario.getCorreo());

            // Encripta la contraseña antes de persistirla
            String encryptedPassword = passwordEncryptor.encryptPassword(usuario.getContrasenia());
            usuario.setContrasenia(encryptedPassword);

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
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            Predicate correoPredicate = criteriaBuilder.equal(root.get("correo"), correo);
            criteriaQuery.select(root).where(correoPredicate);

            Usuario usuario = entityManager.createQuery(criteriaQuery).getSingleResult();

            // Comprueba la contraseña usando una comparación encriptada
            if (passwordEncryptor.checkPassword(password, usuario.getContrasenia())) {
                logger.log(Level.INFO, "Successful login for user: {0}", usuario.getCorreo());
                return usuario;
            } else {
                logger.log(Level.WARNING, "Incorrect password for user: {0}", correo);
                return null;
            }

        } catch (NoResultException e) {
            logger.log(Level.WARNING, "No user found with email: {0}", correo);
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error during user login: {0}", ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean verificarCorreoExistente(String correo) throws ModelException {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Usuario> root = criteriaQuery.from(Usuario.class);

            criteriaQuery.select(criteriaBuilder.count(root));
            criteriaQuery.where(criteriaBuilder.equal(root.get("correo"), correo));

            Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
            return count > 0;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error verifying email: {0}", ex.getMessage());
            return false;
        }
    }
}
