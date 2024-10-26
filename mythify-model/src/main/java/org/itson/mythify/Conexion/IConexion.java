package org.itson.mythify.Conexion;

import javax.persistence.EntityManager;

/**
 * Interfaz para crear conexiones a la base de datos utilizando JPA.
 *
/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IConexion {

    /**
     * Crea y devuelve una nueva conexión a la base de datos utilizando JPA.
     *
     * @return Una instancia de {@link EntityManager} que representa la conexión a la base de datos.
     */
    public EntityManager crearConexion();

    /**
     * Método para cerrar el EntityManagerFactory al finalizar la aplicación
     */
    public void cerrarConexion();
}
