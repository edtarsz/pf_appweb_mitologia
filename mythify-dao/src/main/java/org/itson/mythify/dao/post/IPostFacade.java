/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao.post;

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

    public void crearPost(Post post);

    public void eliminarPost();

    public void actualizarPost();

}
