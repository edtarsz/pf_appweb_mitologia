/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.post;

import java.time.LocalDateTime;
import org.itson.mythify.entidad.Usuario;

/**
 *
 * @author user
 */
public class PostDTO {

    private int idPost;
    private String titulo;
    private String contenido;
    private String categoria;
    private String link;
    private LocalDateTime fechaHoraCreacion;
    private LocalDateTime fechaHoraEdicion;
    private boolean anclado;
    private Usuario usuario;

    public PostDTO() {
    }

    public PostDTO(int idPost, String titulo, String contenido, String categoria, String link, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraEdicion, boolean anclado, Usuario usuario) {
        this.idPost = idPost;
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.link = link;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraEdicion = fechaHoraEdicion;
        this.anclado = anclado;
        this.usuario = usuario;
    }
    

    public PostDTO(String titulo, String contenido, String categoria, String link, LocalDateTime fechaHoraCreacion, boolean anclado, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.link = link;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.anclado = anclado;
        this.usuario = usuario;
    }

    public PostDTO(String titulo, String contenido, String categoria, String link, LocalDateTime fechaHoraCreacion, LocalDateTime fechaHoraEdicion, boolean anclado, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.link = link;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.fechaHoraEdicion = fechaHoraEdicion;
        this.anclado = anclado;
        this.usuario = usuario;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
