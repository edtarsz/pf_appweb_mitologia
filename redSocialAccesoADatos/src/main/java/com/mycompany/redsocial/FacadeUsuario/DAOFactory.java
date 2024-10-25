/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsocial.FacadeUsuario;

import com.mycompany.redsocial.Conexion.IConexion;
import com.mycompany.redsocial.DAO.UsuarioDAO;

/**
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class DAOFactory {

    public static UsuarioDAO instanciaUsuarioDAO(IConexion conexion) {
        return new UsuarioDAO(conexion);
    }
}
