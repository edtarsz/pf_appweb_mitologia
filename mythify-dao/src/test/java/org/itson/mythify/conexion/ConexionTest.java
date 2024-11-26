package org.itson.mythify.conexion;

import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConexionTest {
    @Mock
    private EntityManagerFactory emFactoryMock;

    @Mock
    private EntityManager entityManagerMock;

    private Conexion conexion;

    private static final String ENTITY_MANAGER_FACTORY_FIELD = "emFactory";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conexion = new Conexion();

        // Usamos reflexión para inyectar el mock en la clase
        try {
            var emFactoryField = Conexion.class.getDeclaredField("emFactory");
            emFactoryField.setAccessible(true);
            emFactoryField.set(null, emFactoryMock); // Cambiamos el campo estático
        } catch (Exception e) {
            throw new RuntimeException("Error al configurar el mock de EntityManagerFactory", e);
        }

    }

    @Test
    @DisplayName("crearConexion: Debe devolver un EntityManager cuando se llama")
    void testCrearConexion() {
        when(emFactoryMock.createEntityManager()).thenReturn(entityManagerMock);

        EntityManager entityManager = conexion.crearConexion();

        Assertions.assertNotNull(entityManager, "El EntityManager no debe ser nulo");
        verify(emFactoryMock, times(1)).createEntityManager();
    }

    @Test
    @DisplayName("cerrarConexion: Debe cerrar el EntityManagerFactory si está abierto")
    void testCerrarConexion() {
        when(emFactoryMock.isOpen()).thenReturn(true);

        conexion.cerrarConexion();

        verify(emFactoryMock, times(1)).isOpen();
        verify(emFactoryMock, times(1)).close();
    }

    @Test
    @DisplayName("cerrarConexion: No debe intentar cerrar si EntityManagerFactory ya está cerrado")
    void testCerrarConexionYaCerrado() {
        when(emFactoryMock.isOpen()).thenReturn(false);

        conexion.cerrarConexion();

        verify(emFactoryMock, times(1)).isOpen();
        verify(emFactoryMock, never()).close();
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