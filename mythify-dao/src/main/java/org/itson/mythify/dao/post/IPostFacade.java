/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao.post;

import java.util.List;
import org.itson.mythify.entidad.Post;

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
public interface IPostFacade {

    public Post crearPost(Post post);

    public boolean eliminarPost(int idPost);

    public Post actualizarPost(Post post);
    
     public Post anclarPost(Post post);

    public List<Post> consultarPosts();

    public List<Post> consultarPostsCategoria(String categoria);

    public Post consultarPostPorID(int id);

}
