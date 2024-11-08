/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao.comentario;

import java.util.List;
import org.itson.mythify.entidad.Comentario;
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
public interface IComentarioFacade {

    public Comentario crearComentario(Comentario comentario);

    public void eliminarComentario();

    public void actualizarComentario();

    public List<Comentario> consultarComentarios();

}