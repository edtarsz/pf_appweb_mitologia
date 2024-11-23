/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
@Entity
@Table(name = "Comentario")
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario", nullable = false)
    private int idComentario;

    @Column(name = "contenido", nullable = false, length = 1_000)
    private String contenido;

    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario; // Usuario que comentó

    @ManyToOne
    @JoinColumn(name = "idComentarioPadre", referencedColumnName = "idComentario")
    private Comentario comentarioPadre;

    @ManyToOne
    @JoinColumn(name = "idPost", nullable = false)
    private Post post; // Post al que se realizó el comentario

    public Comentario() {
    }

    public Comentario(String contenido, LocalDateTime fechaHora, Usuario usuario, Comentario comentarioPadre, Post post) {
        this.contenido = contenido;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
        this.comentarioPadre = comentarioPadre;
        this.post = post;
    }

    public Comentario(String contenido, LocalDateTime fechaHora, Usuario usuario, Post post) {
        this.contenido = contenido;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.idComentario;
        hash = 97 * hash + Objects.hashCode(this.contenido);
        hash = 97 * hash + Objects.hashCode(this.fechaHora);
        hash = 97 * hash + Objects.hashCode(this.usuario);
        hash = 97 * hash + Objects.hashCode(this.comentarioPadre);
        hash = 97 * hash + Objects.hashCode(this.post);
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
        final Comentario other = (Comentario) obj;
        if (this.idComentario != other.idComentario) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.fechaHora, other.fechaHora)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.comentarioPadre, other.comentarioPadre)) {
            return false;
        }
        return Objects.equals(this.post, other.post);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comentario{");
        sb.append("idComentario=").append(idComentario);
        sb.append(", contenido=").append(contenido);
        sb.append(", fechaHora=").append(fechaHora);
        sb.append(", usuario=").append(usuario);
        sb.append(", comentarioPadre=").append(comentarioPadre);
        sb.append(", post=").append(post);
        sb.append('}');
        return sb.toString();
    }

}
