/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.itson.mythify.dao;

import org.itson.mythify.conexion.ModelException;
import java.util.List;
import org.itson.mythify.entidad.Comentario;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public interface IComentarioDAO {

    public Comentario crearComentario(Comentario comentario) throws ModelException;

    public void eliminarComentario(int idComentario) throws ModelException;

    public void actualizarComentario() throws ModelException;

    public List<Comentario> consultarComentarios(int idPost) throws ModelException;

    public Comentario consultarComentarioPorID(int idComentario) throws ModelException;

}
