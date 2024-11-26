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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

        Usuario usuarioResultado = assertDoesNotThrow(() ->
                usuarioFacade.consultarUsuario("correo@test.com", "password123"));

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
}

