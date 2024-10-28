/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.entidad;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPost;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "contenido", nullable = false)
    private String contenido;

    @Column(name = "fechaHoraCreacion", nullable = false, updatable = false)
    private Timestamp fechaHoraCreacion;

    @Column(name = "fechaHoraEdicion", nullable = false)
    private Timestamp fechaHoraEdicion;

    @Column(name = "anclado")
    private boolean anclado;

    @ManyToOne
    @JoinColumn(name = "idUsuarioAnclado")
    private Usuario usuarioAnclado;

    // Getters y Setters
    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Timestamp fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Timestamp getFechaHoraEdicion() {
        return fechaHoraEdicion;
    }

    public void setFechaHoraEdicion(Timestamp fechaHoraEdicion) {
        this.fechaHoraEdicion = fechaHoraEdicion;
    }

    public boolean isAnclado() {
        return anclado;
    }

    public void setAnclado(boolean anclado) {
        this.anclado = anclado;
    }

    public Usuario getUsuarioAnclado() {
        return usuarioAnclado;
    }

    public void setUsuarioAnclado(Usuario usuarioAnclado) {
        this.usuarioAnclado = usuarioAnclado;
    }

}
