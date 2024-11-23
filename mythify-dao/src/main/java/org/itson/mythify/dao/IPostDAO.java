/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import org.itson.mythify.conexion.ModelException;
import java.util.List;
import org.itson.mythify.entidad.Post;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IPostDAO {

    public Post crearPost(Post post) throws ModelException;

    public boolean eliminarPost(int idPost) throws ModelException;

    public Post anclarPost(Post post) throws ModelException;

    public Post actualizarPost(Post post) throws ModelException;

    public List<Post> consultarPosts() throws ModelException;

    public List<Post> consultarPostsCategoria(String categoria) throws ModelException;

    public Post consultarPostPorID(int id) throws ModelException;

    public void likearPost(int idUsuario, int idPost) throws ModelException;

    public void desLikearPost(int idUsuario, int idPost) throws ModelException;

    public void operacionContadorLike(int idPost, int cantidad) throws ModelException;

    public int consultarCantLikes(int idPost) throws ModelException;

    public List<Post> consultarPostLikeados(int idUsuario) throws ModelException;

}
