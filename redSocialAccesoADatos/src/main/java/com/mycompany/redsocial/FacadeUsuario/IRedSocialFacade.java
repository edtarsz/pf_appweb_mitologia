/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.redsocial.FacadeUsuario;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
import com.mycompany.redsocial.Controller.ControllerException;
import com.mycompany.redsocial.Controller.UsuarioDTO;
import com.mycompany.redsocial.Entidad.Usuario;

/**
 *
 * @author user
 */
public interface IRedSocialFacade {

    public UsuarioDTO crearUsuarioDTO(UsuarioDTO usuarioDTO) throws ControllerException;

    public void eliminarUsuario() throws ControllerException;

    public void actualizarUsuario() throws ControllerException;

    public UsuarioDTO consultarUsuario(String correo, String password) throws ControllerException;

    public Usuario consultarUsuarioSession(String correo, String password) throws ControllerException;

    public UsuarioDTO EntidadAUsuarioDTO(Usuario usuario) throws ControllerException;

    public Usuario UsuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException;
}
