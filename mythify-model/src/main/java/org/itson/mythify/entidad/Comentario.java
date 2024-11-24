/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.entidad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    @Column(name = "likes", nullable = true)
    private int cantLikes;

    @Column(name = "contenido", nullable = false, length = 1_000)
    private String contenido;

    @Column(name = "fechaHora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario; // Usuario que comentó

    @ManyToOne
    @JoinColumn(name = "idComentarioPadre", referencedColumnName = "idComentario", foreignKey = @ForeignKey(name = "FK_Comentario_idComentarioPadre"))
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> respuestas = new ArrayList<>();

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

    public List<Comentario> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Comentario> respuestas) {
        this.respuestas = respuestas;
    }

    public int getCantLikes() {
        return cantLikes;
    }

    public void setCantLikes(int cantLikes) {
        this.cantLikes = cantLikes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comentario comentario = (Comentario) o;
        return Objects.equals(idComentario, comentario.idComentario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idComentario);
    }
}
