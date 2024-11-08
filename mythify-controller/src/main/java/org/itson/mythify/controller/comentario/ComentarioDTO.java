/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.comentario;

import java.util.Date;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public class ComentarioDTO {

    private int idComentario;
    private String contenido;
    private Date fechaHora;
    private Usuario usuario; // Usuario que comentó
    private Comentario comentarioPadre;
    private Post post; // Post al que se realizó el comentario

    public ComentarioDTO(String contenido, Date fechaHora, Usuario usuario, Post post) {
        this.contenido = contenido;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.post = post;
    }

    public ComentarioDTO(String contenido, Date fechaHora, Usuario usuario, Comentario comentarioPadre, Post post) {
        this.contenido = contenido;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.comentarioPadre = comentarioPadre;
        this.post = post;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}
