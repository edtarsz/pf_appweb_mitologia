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
import javax.persistence.EntityTransaction;

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
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                logger.warning("Transaction rolled back.");
            }
            throw new ModelException("Error creating user: " + ex.getMessage());
        }
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
                throw new ModelException("Incorrect password for user: " + correo);
            }

        } catch (NoResultException e) {
            throw new ModelException("User not found: " + correo);
        } catch (Exception ex) {
            throw new ModelException("Error querying user: " + ex.getMessage());
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
            throw new ModelException("Error verifying email: " + ex.getMessage());
        }
    }

    public boolean actualizarUsuario(Usuario usuario) throws ModelException {
        try {
            if (usuario == null) {
                logger.warning("Intento de actualizar usuario nulo");
                return false;
            }

            entityManager.getTransaction().begin();

            Usuario usuarioExistente = entityManager.find(Usuario.class, usuario.getIdUsuario());

            if (usuarioExistente == null) {
                entityManager.getTransaction().rollback();
                logger.warning("Usuario no encontrado para actualización");
                return false;
            }

            if (usuario.getNombre() != null) {
                usuarioExistente.setNombre(usuario.getNombre());
            }

            if (usuario.getApellidoPaterno() != null) {
                usuarioExistente.setApellidoPaterno(usuario.getApellidoPaterno());
            }

            if (usuario.getApellidoMaterno() != null) {
                usuarioExistente.setApellidoMaterno(usuario.getApellidoMaterno());
            }

            if (usuario.getCorreo() != null) {
                usuarioExistente.setCorreo(usuario.getCorreo());
            }

            if (usuario.getContrasenia() != null) {
                usuarioExistente.setContrasenia(
                        passwordEncryptor.encryptPassword(usuario.getContrasenia())
                );
            }

            if (usuario.getTelefono() != null) {
                usuarioExistente.setTelefono(usuario.getTelefono());
            }

            if (usuario.getAvatar() != null) {
                usuarioExistente.setAvatar(usuario.getAvatar());
            }

            if (usuario.getCiudad() != null) {
                usuarioExistente.setCiudad(usuario.getCiudad());
            }

            if (usuario.getFechaNacimiento() != null) {
                usuarioExistente.setFechaNacimiento(usuario.getFechaNacimiento());
            }

            if (usuario.getGenero() != null) {
                usuarioExistente.setGenero(usuario.getGenero());
            }

            if (usuario.getTipoUsuario() != null) {
                usuarioExistente.setTipoUsuario(usuario.getTipoUsuario());
            }

            if (usuario.getMunicipio() != null) {
                usuarioExistente.setMunicipio(usuario.getMunicipio());
            }

            entityManager.merge(usuarioExistente);

            entityManager.getTransaction().commit();

            logger.info("Usuario actualizado exitosamente");
            return true;

        } catch (Exception ex) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            logger.log(Level.SEVERE, "Error al actualizar usuario", ex);
            throw new ModelException("Error en actualización de usuario: " + ex.getMessage());
        }
    }
}
