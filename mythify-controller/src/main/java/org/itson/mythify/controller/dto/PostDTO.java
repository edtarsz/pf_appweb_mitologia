/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.dto;

import java.time.LocalDateTime;

/**
 *
 * @author elimo
 */
public class PostDTO {
    
    private int idPost;
    private String titulo;
    private String contenido;
    private LocalDateTime fechaHoraCreacion;
    private LocalDateTime fechaHoraEdicion;
    private boolean anclado;
    private UsuarioDTO usuarioAnclado;

    public PostDTO() {
    }

    public PostDTO(int idPost, String titulo, String contenido, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraEdicion, boolean anclado, UsuarioDTO usuarioAnclado) {
        this.idPost = idPost;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraEdicion = fechaHoraEdicion;
        this.anclado = anclado;
        this.usuarioAnclado = usuarioAnclado;
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

    public LocalDateTime getFechaHoraCreacion() {
        return fechaHoraCreacion;
    }

    public void setFechaHoraCreacion(LocalDateTime fechaHoraCreacion) {
        this.fechaHoraCreacion = fechaHoraCreacion;
    }

    public LocalDateTime getFechaHoraEdicion() {
        return fechaHoraEdicion;
    }

    public void setFechaHoraEdicion(LocalDateTime fechaHoraEdicion) {
        this.fechaHoraEdicion = fechaHoraEdicion;
    }

    public boolean isAnclado() {
        return anclado;
    }

    public void setAnclado(boolean anclado) {
        this.anclado = anclado;
    }

    public UsuarioDTO getUsuarioAnclado() {
        return usuarioAnclado;
    }

    public void setUsuarioAnclado(UsuarioDTO usuarioAnclado) {
        this.usuarioAnclado = usuarioAnclado;
    }
}
