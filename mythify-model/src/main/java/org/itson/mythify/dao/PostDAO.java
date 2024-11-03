package org.itson.mythify.dao;

import org.itson.mythify.conexion.IConexion;
import javax.persistence.EntityManager;
import java.util.logging.Logger;
import org.itson.mythify.entidad.Usuario;

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
        logger.info("UsuarioDAO initialized with a new EntityManager.");
    }

    @Override
    public Usuario crearPost(Usuario usuario) throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario eliminarPost() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Usuario actualizarPost() throws ModelException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
