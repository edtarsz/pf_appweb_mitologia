/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.post;

import java.util.Date;

/**
 *
 * @author user
 */
public class PostDTO {

    private int idPost;
    private String titulo;
    private String contenido;
    private Date fechaHoraCreacion;
    private Date fechaHoraEdicion;
    private boolean anclado;

    public PostDTO(int idPost, String titulo, String contenido, Date fechaHoraCreacion, Date fechaHoraEdicion, boolean anclado) {
        this.idPost = idPost;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraEdicion = fechaHoraEdicion;
        this.anclado = anclado;
    }

    public PostDTO(String titulo, String contenido, Date fechaHoraCreacion, Date fechaHoraEdicion, boolean anclado) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraEdicion = fechaHoraEdicion;
        this.anclado = anclado;
    }

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

    public Date getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(Date fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public Date getFechaHoraEdicion() {
        return fechaHoraEdicion;
    }

    public void setFechaHoraEdicion(Date fechaHoraEdicion) {
        this.fechaHoraEdicion = fechaHoraEdicion;
    }

    public boolean isAnclado() {
        return anclado;
    }

    public void setAnclado(boolean anclado) {
        this.anclado = anclado;
    }

}
