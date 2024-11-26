package org.itson.mythify.conexion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InitialConfigTest {
    @Mock
    private IConexion conexionMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("iniciarConexion: Retorna true cuando la conexión se establece exitosamente")
    void testIniciarConexionSuccess() {
        // Usamos un mock estático para reemplazar el constructor de Conexion
        try (MockedConstruction<Conexion> mocked = mockConstruction(Conexion.class, (mock, context) -> {
            // Simulamos que crearConexion devuelve null (puedes devolver un mock de EntityManager si lo necesitas)
            when(mock.crearConexion()).thenReturn(null);
        })) {
            assertTrue(InitialConfig.iniciarConexion(), "Debería retornar true cuando la conexión es exitosa");
        }
    }

    @Test
    @DisplayName("iniciarConexion: Retorna false cuando ocurre una excepción al crear la conexión")
    void testIniciarConexionFailure() {
        // Simular que lanzar una excepción en crearConexion
        doThrow(new RuntimeException("Error al crear conexión")).when(conexionMock).crearConexion();

        // Usamos un mock estático para reemplazar el constructor de Conexion
        try (MockedConstruction<Conexion> mocked = mockConstruction(Conexion.class, (mock, context) -> {
            when(mock.crearConexion()).thenThrow(new RuntimeException("Error al crear conexión"));
        })) {
            InitialConfig conexion = new InitialConfig();
            ;
            assertFalse(conexion.iniciarConexion(), "Debería retornar false cuando ocurre una excepción");
        }
    }
}