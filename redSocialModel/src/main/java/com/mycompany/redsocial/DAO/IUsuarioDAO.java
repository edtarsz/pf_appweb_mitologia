/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.redsocial.DAO;

import com.mycompany.redsocial.Entidad.Usuario;

/**
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IUsuarioDAO {

    public Usuario crearUsuario(Usuario usuario) throws ModelException;

    public Usuario eliminarUsuario() throws ModelException;

    public Usuario actualizarUsuario() throws ModelException;

    public Usuario consultarUsuario(String correo, String password) throws ModelException;

}
