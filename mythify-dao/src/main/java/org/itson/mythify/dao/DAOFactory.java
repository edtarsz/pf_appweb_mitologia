/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class DAOFactory {

    public static UsuarioDAO instanciaUsuarioDAO(IConexion conexion) throws ModelException {
        return new UsuarioDAO(conexion);
    }

    public static PostDAO instanciaPostDAO(IConexion conexion) throws ModelException {
        return new PostDAO(conexion);
    }

    public static ComentarioDAO instanciaComentarioDAO(IConexion conexion) throws ModelException {
        return new ComentarioDAO(conexion);
    }
}
