/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.entidad;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author elimo
 */
@Entity
@Table(name = "Comentario")
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComentario;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fechaHora", nullable = false)
    private java.sql.Timestamp fechaHora;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario; // Usuario que comentó

    @ManyToOne
    @JoinColumn(name = "idPost", nullable = false)
    private Post post; // Post al que pertenece el comentario

    // Getters y Setters
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

    public java.sql.Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(java.sql.Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
