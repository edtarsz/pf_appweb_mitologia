/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.FacadeUsuario;

import org.itson.mythify.Controller.ControllerException;
import org.itson.mythify.Controller.UsuarioDTO;
import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.dao.IUsuarioDAO;
import org.itson.mythify.dao.ModelException;
import org.itson.mythify.entidad.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class RedSocialFacade implements IRedSocialFacade {

    private final IUsuarioDAO usuarioDAO;

    public RedSocialFacade() {
        IConexion conexion = new Conexion();
        this.usuarioDAO = DAOFactory.instanciaUsuarioDAO(conexion);
    }

    @Override
    public UsuarioDTO crearUsuarioDTO(UsuarioDTO usuarioDTO) throws ControllerException {
        try {
            return EntidadAUsuarioDTO(usuarioDAO.crearUsuario(UsuarioDTOAEntidad(usuarioDTO)));
        } catch (ModelException ex) {
            Logger.getLogger(RedSocialFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void eliminarUsuario() throws ControllerException {
        try {
            usuarioDAO.eliminarUsuario();
        } catch (ModelException ex) {
            Logger.getLogger(RedSocialFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actualizarUsuario() throws ControllerException {
        try {
            usuarioDAO.actualizarUsuario();
        } catch (ModelException ex) {
            Logger.getLogger(RedSocialFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public UsuarioDTO consultarUsuario(String correo, String password) throws ControllerException {
        try {
            return EntidadAUsuarioDTO(usuarioDAO.consultarUsuario(correo, password));
        } catch (ModelException ex) {
            Logger.getLogger(RedSocialFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Usuario consultarUsuarioSession(String correo, String password) throws ControllerException {
        try {
            return usuarioDAO.consultarUsuario(correo, password);
        } catch (ModelException ex) {
            Logger.getLogger(RedSocialFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public UsuarioDTO EntidadAUsuarioDTO(Usuario usuario) throws ControllerException {
        return new UsuarioDTO(
                usuario.getNombre(),
                usuario.getApellidoPaterno(),
                usuario.getApellidoMaterno(),
                usuario.getCorreo(),
                usuario.getContrasenia(),
                usuario.getTelefono(),
                usuario.getAvatar(),
                usuario.getCiudad(),
                usuario.getGenero(),
                usuario.getFechaNacimiento(),
                usuario.getMunicipio());
    }

    @Override
    public Usuario UsuarioDTOAEntidad(UsuarioDTO usuarioDTO) throws ControllerException {
        return new Usuario(
                usuarioDTO.getNombre(),
                usuarioDTO.getApellidoPaterno(),
                usuarioDTO.getApellidoMaterno(),
                usuarioDTO.getCorreo(),
                usuarioDTO.getEncryptedPassword(),
                usuarioDTO.getTelefono(),
                usuarioDTO.getAvatar(),
                usuarioDTO.getCiudad(),
                usuarioDTO.getGenero(),
                usuarioDTO.getFechaNacimiento(),
                usuarioDTO.getMunicipio());
    }

}
