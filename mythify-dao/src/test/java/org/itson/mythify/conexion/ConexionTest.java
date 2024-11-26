package org.itson.mythify.conexion;

import org.itson.mythify.conexion.Conexion;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConexionTest {

    private static final String ENTITY_MANAGER_FACTORY_FIELD = "emFactory";

    @BeforeEach
    void setUp() throws Exception {
        // Restablecer emFactory a su estado inicial usando reflexión
        var field = Conexion.class.getDeclaredField(ENTITY_MANAGER_FACTORY_FIELD);
        field.setAccessible(true);
        field.set(null, null);
    }


    @Test
    @DisplayName("cerrarConexion: No realiza ninguna acción si emFactory es null")
    void testCerrarConexionWithNullFactory() throws Exception {
        // Configurar emFactory como null
        var field = Conexion.class.getDeclaredField(ENTITY_MANAGER_FACTORY_FIELD);
        field.setAccessible(true);
        field.set(null, null);

        Conexion conexion = new Conexion();
        assertDoesNotThrow(conexion::cerrarConexion, "No debería lanzar excepciones cuando emFactory es null");
    }


    @Test
    @DisplayName("cerrarConexion: No realiza ninguna acción si emFactory no está abierto")
    void testCerrarConexionWithClosedFactory() throws Exception {
        // Crear un mock de EntityManagerFactory que no está abierto
        EntityManagerFactory emFactoryMock = mock(EntityManagerFactory.class);
        when(emFactoryMock.isOpen()).thenReturn(false);

        // Inyectar el mock en el campo estático
        var field = Conexion.class.getDeclaredField(ENTITY_MANAGER_FACTORY_FIELD);
        field.setAccessible(true);
        field.set(null, emFactoryMock);

        Conexion conexion = new Conexion();
        conexion.cerrarConexion();

        verify(emFactoryMock, times(1)).isOpen();
        verify(emFactoryMock, never()).close();
    }
}
