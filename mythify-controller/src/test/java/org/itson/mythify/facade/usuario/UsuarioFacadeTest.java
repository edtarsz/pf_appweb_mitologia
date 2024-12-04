/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.usuario;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.IUsuarioDAO;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.exceptions.ControllerException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class UsuarioFacadeTest {

    @Mock
    private IUsuarioDAO usuarioDAOMock;

    @InjectMocks
    private UsuarioFacade usuarioFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inyectar el mock manualmente ya que la conexión se inicializa directamente.
        usuarioFacade.conexion = mock(IConexion.class);
    }

    @Test
    void testCrearUsuarioExitoso() throws Exception {
        Usuario usuario = new Usuario();
        // No configuramos comportamiento porque no se espera excepción ni retorno.

        assertDoesNotThrow(() -> usuarioFacade.crearUsuario(usuario));
        verify(usuarioDAOMock, times(1)).crearUsuario(usuario);
    }

    @Test
    void testCrearUsuarioLanzaControllerException() throws Exception {
        Usuario usuario = new Usuario();
        doThrow(new ModelException("Error interno")).when(usuarioDAOMock).crearUsuario(usuario);

        ControllerException exception = assertThrows(ControllerException.class,
                () -> usuarioFacade.crearUsuario(usuario));

        assertEquals("Error al crear usuario", exception.getMessage());
        verify(usuarioDAOMock, times(1)).crearUsuario(usuario);
    }

    @Test
    void testConsultarUsuarioExitoso() throws Exception {
        Usuario usuarioEsperado = new Usuario();
        when(usuarioDAOMock.consultarUsuario("correo@test.com", "password123")).thenReturn(usuarioEsperado);

        Usuario usuarioResultado = assertDoesNotThrow(()
                -> usuarioFacade.consultarUsuario("correo@test.com", "password123"));

        assertEquals(usuarioEsperado, usuarioResultado);
        verify(usuarioDAOMock, times(1)).consultarUsuario("correo@test.com", "password123");
    }

    @Test
    void testConsultarUsuarioLanzaControllerException() throws Exception {
        when(usuarioDAOMock.consultarUsuario("correo@test.com", "password123"))
                .thenThrow(new ModelException("Usuario no encontrado"));

        ControllerException exception = assertThrows(ControllerException.class,
                () -> usuarioFacade.consultarUsuario("correo@test.com", "password123"));

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(usuarioDAOMock, times(1)).consultarUsuario("correo@test.com", "password123");
    }

    @Test
    void testVerificarCorreoExistenteExitoso() throws Exception {
        // Simulación de que el correo existe en la base de datos.
        when(usuarioDAOMock.verificarCorreoExistente("correo@test.com")).thenReturn(true);

        boolean resultado = usuarioFacade.verificarCorreoExistente("correo@test.com");

        assertTrue(resultado); // Asegura que el valor devuelto sea true.
        verify(usuarioDAOMock, times(1)).verificarCorreoExistente("correo@test.com");
    }

    @Test
    void testVerificarCorreoNoExistente() throws Exception {
        // Simulación de que el correo no existe en la base de datos.
        when(usuarioDAOMock.verificarCorreoExistente("correo@test.com")).thenReturn(false);

        boolean resultado = usuarioFacade.verificarCorreoExistente("correo@test.com");

        assertFalse(resultado); // Asegura que el valor devuelto sea false.
        verify(usuarioDAOMock, times(1)).verificarCorreoExistente("correo@test.com");
    }

    @Test
    void testVerificarCorreoExistenteLanzaControllerException() throws Exception {
        when(usuarioDAOMock.verificarCorreoExistente("correo@test.com"))
                .thenThrow(new ModelException("Error al verificar correo"));

        ControllerException exception = assertThrows(ControllerException.class,
                () -> usuarioFacade.verificarCorreoExistente("correo@test.com"));

        assertEquals("Error al verificar correo", exception.getMessage());
        verify(usuarioDAOMock, times(1)).verificarCorreoExistente("correo@test.com");
    }

    @Test
    void testActualizarUsuario_Exito() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(50);
        usuario.setNombre("Test User");
        usuario.setCorreo("test@example.com");

        when(usuarioDAOMock.actualizarUsuario(usuario)).thenReturn(true);

        assertDoesNotThrow(() -> usuarioFacade.actualizarUsuario(usuario));

        verify(usuarioDAOMock, times(1)).actualizarUsuario(usuario);
    }

    @Test
    void testActualizarUsuarioLanzaControllerException() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(50);
        usuario.setNombre("Test User");
        usuario.setCorreo("test@example.com");

        doThrow(new ModelException("Error al actualizar usuario")).when(usuarioDAOMock).actualizarUsuario(usuario);

        ControllerException exception = assertThrows(ControllerException.class,
                () -> usuarioFacade.actualizarUsuario(usuario));

        assertEquals("Error al actualizar usuario", exception.getMessage());
        verify(usuarioDAOMock, times(1)).actualizarUsuario(usuario);
    }
}
