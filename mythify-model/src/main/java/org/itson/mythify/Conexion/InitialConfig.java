package org.itson.mythify.Conexion;

import org.itson.mythify.Conexion.Conexion;
import org.itson.mythify.Conexion.IConexion;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class InitialConfig {

    public static boolean iniciarConexion() {
        try {
            IConexion conexion = new Conexion();
            conexion.crearConexion();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
