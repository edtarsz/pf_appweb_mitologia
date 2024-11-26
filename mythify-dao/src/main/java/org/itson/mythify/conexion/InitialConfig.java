package org.itson.mythify.conexion;

import javax.persistence.Persistence;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class InitialConfig {

    public static boolean iniciarConexion() {
        try {
            IConexion conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
            conexion.crearConexion();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
