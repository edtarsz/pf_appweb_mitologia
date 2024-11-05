/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao.usuario;

import org.itson.mythify.entidad.Usuario;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
/**
 *
 * @author user
 */
public interface IUsuarioFacade {

    public void crearUsuario(Usuario usuario);

    public void eliminarUsuario();

    public void actualizarUsuario();

    public Usuario consultarUsuario(String correo, String password);

    public Usuario consultarUsuarioSession(String correo, String password);

    public boolean usuarioExiste(String correo, String password);
}
