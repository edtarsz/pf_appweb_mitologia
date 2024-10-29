/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.usuario;

import org.itson.mythify.enumeradores.TipoPermiso;

/**
 *
 * @author elimo
 */
public class PermisoDTO {

    private int idPermiso;
    private TipoPermiso tipoPermiso;

    public PermisoDTO() {
    }

    public PermisoDTO(TipoPermiso tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public PermisoDTO(int idPermiso, TipoPermiso tipoPermiso) {
        this.idPermiso = idPermiso; // Inicialización del ID
        this.tipoPermiso = tipoPermiso; // Inicialización del tipo de permiso
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public TipoPermiso getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(TipoPermiso tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }
}
