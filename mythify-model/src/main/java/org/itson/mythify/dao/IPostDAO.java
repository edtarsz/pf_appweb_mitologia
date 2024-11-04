/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import java.util.List;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IPostDAO {

    public Post crearPost(Post post) throws ModelException;

    public Post eliminarPost() throws ModelException;

    public Post actualizarPost() throws ModelException;

    public List<Post> consultarPosts(Usuario usuario) throws ModelException;

}
