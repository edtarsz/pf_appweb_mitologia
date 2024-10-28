/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.controller.dto;

import org.itson.mythify.controller.dto.PermisoDTO;
import java.util.Date;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.enumeradores.Genero;
import org.itson.mythify.enumeradores.TipoUsuario;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class UsuarioDTO {

    private int id; // Atributo de ID
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String encryptedPassword;
    private String telefono;
    private String avatar;
    private String ciudad;
    private Genero genero;
    private Date fechaNacimiento;
    private Municipio municipio;
    private TipoUsuario tipoUsuario;
    private PermisoDTO permiso; // Cambiado a PermisoDTO

    public UsuarioDTO() {
    }

    public UsuarioDTO(int id, String nombre, String apellidoPaterno, String apellidoMaterno, String correo,
                      String encryptedPassword, String telefono, String avatar, String ciudad,
                      Genero genero, Date fechaNacimiento, Municipio municipio,
                      TipoUsuario tipoUsuario, PermisoDTO permiso) {
        this.id = id; // Inicialización del ID
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.encryptedPassword = encryptedPassword;
        this.telefono = telefono;
        this.avatar = avatar;
        this.ciudad = ciudad;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.municipio = municipio;
        this.tipoUsuario = tipoUsuario;
        this.permiso = permiso; // Inicialización del atributo permiso
    }

    public int getId() { // Método getter para id
        return id;
    }

    public void setId(int id) { // Método setter para id
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public PermisoDTO getPermiso() { // Método getter para permiso
        return permiso;
    }

    public void setPermiso(PermisoDTO permiso) { // Método setter para permiso
        this.permiso = permiso;
    }
}
