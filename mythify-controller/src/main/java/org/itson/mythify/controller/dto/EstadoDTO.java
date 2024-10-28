/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.dto;

/**
 *
 * @author elimo
 */
public class EstadoDTO {

    private Long id;
    private String nombre;
    private List<MunicipioDTO> municipios;

    public EstadoDTO() {
    }

    public EstadoDTO(Long id, String nombre, List<MunicipioDTO> municipios) {
        this.id = id;
        this.nombre = nombre;
        this.municipios = municipios;
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

    public List<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }

}
