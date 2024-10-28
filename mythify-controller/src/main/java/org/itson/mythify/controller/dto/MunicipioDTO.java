/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.dto;

import java.util.List;

/**
 *
 * @author elimo
 */
public class MunicipioDTO {

    private Long id;
    private String nombre;
    private EstadoDTO estado;
    private List<UsuarioDTO> usuarios;

    public MunicipioDTO() {
    }

    public MunicipioDTO(Long id, String nombre, EstadoDTO estado, List<UsuarioDTO> usuarios) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.usuarios = usuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public EstadoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoDTO estado) {
        this.estado = estado;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

}
