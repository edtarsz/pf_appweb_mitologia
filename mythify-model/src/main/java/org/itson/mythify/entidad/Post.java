/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.entidad;

import static com.google.protobuf.JavaFeaturesProto.java;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@Entity
@Table(name = "Post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPost", nullable = false)
    private int idPost;

    @Column(name = "likes", nullable = true)
    private int cantLikes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "contenido", nullable = false, length = 5_000)
    private String contenido;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "link", nullable = true)
    private String link;

    @Column(name = "fechaHoraCreacion", nullable = false, updatable = false)
    private LocalDateTime fechaHoraCreacion;

    @Column(name = "fechaHoraEdicion", nullable = true)
    private LocalDateTime fechaHoraEdicion;

    @Column(name = "anclado")
    private boolean anclado;

    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    public Post() {
    }

    public Post(String titulo, String contenido, String categoria, String link, LocalDateTime fechaHoraCreacion, boolean anclado, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.categoria = categoria;
        this.link = link;
        this.fechaHoraCreacion = fechaHoraCreacion;
        this.anclado = anclado;
        this.usuario = usuario;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getCantLikes() {
        return cantLikes;
    }

    public void setCantLikes(int cantLikes) {
        this.cantLikes = cantLikes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.idPost;
        hash = 89 * hash + this.cantLikes;
        hash = 89 * hash + Objects.hashCode(this.comentarios);
        hash = 89 * hash + Objects.hashCode(this.titulo);
        hash = 89 * hash + Objects.hashCode(this.contenido);
        hash = 89 * hash + Objects.hashCode(this.categoria);
        hash = 89 * hash + Objects.hashCode(this.link);
        hash = 89 * hash + Objects.hashCode(this.fechaHoraCreacion);
        hash = 89 * hash + Objects.hashCode(this.fechaHoraEdicion);
        hash = 89 * hash + (this.anclado ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.usuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.idPost != other.idPost) {
            return false;
        }
        if (this.cantLikes != other.cantLikes) {
            return false;
        }
        if (this.anclado != other.anclado) {
            return false;
        }
        if (!Objects.equals(this.titulo, other.titulo)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        if (!Objects.equals(this.comentarios, other.comentarios)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraCreacion, other.fechaHoraCreacion)) {
            return false;
        }
        if (!Objects.equals(this.fechaHoraEdicion, other.fechaHoraEdicion)) {
            return false;
        }
        return Objects.equals(this.usuario, other.usuario);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Post{");
        sb.append("idPost=").append(idPost);
        sb.append(", cantLikes=").append(cantLikes);
        sb.append(", comentarios=").append(comentarios);
        sb.append(", titulo=").append(titulo);
        sb.append(", contenido=").append(contenido);
        sb.append(", categoria=").append(categoria);
        sb.append(", link=").append(link);
        sb.append(", fechaHoraCreacion=").append(fechaHoraCreacion);
        sb.append(", fechaHoraEdicion=").append(fechaHoraEdicion);
        sb.append(", anclado=").append(anclado);
        sb.append(", usuario=").append(usuario);
        sb.append('}');
        return sb.toString();
    }

}
