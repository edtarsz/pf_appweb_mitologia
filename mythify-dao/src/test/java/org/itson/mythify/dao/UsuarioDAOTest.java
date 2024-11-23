package org.itson.mythify.dao;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioDAOTest {

    @Mock
    private IConexion mockConexion;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @InjectMocks
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración del mock de la conexión para devolver un EntityManager simulado
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);

        // Configuración del EntityManager para devolver una transacción simulada
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        // Crear instancia del UsuarioDAO con el mock de la conexión
        usuarioDAO = new UsuarioDAO(mockConexion);
    }

    @Test
    public void testCrearUsuario_Exito() throws ModelException {
        // Datos de prueba
        Usuario mockUsuario = new Usuario();
        mockUsuario.setCorreo("test@example.com");
        mockUsuario.setContrasenia("password123");

        // Configuración para la prueba
        doNothing().when(mockEntityManager).persist(any(Usuario.class));
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // Invocación del método a probar
        Usuario resultado = usuarioDAO.crearUsuario(mockUsuario);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(mockUsuario.getCorreo(), resultado.getCorreo());
        assertNotEquals("password123", resultado.getContrasenia()); // La contraseña debe estar encriptada

        // Verificar que los métodos se llamaron correctamente
        verify(mockEntityManager).persist(mockUsuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCrearUsuario_Fallo() throws ModelException {
        // Configuración del mock para simular la excepción
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);
        doThrow(new RuntimeException("Error persistiendo usuario"))
                .when(mockEntityManager).persist(any(Usuario.class));

        // Inicializa el DAO
        UsuarioDAO dao = new UsuarioDAO(mockConexion);

        // Act: Intenta crear un usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setContrasenia("password");

        Usuario resultado = dao.crearUsuario(usuario);

        // Assert
        assertNull(resultado, "El usuario debería ser nulo al fallar la creación");
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback(); // Aquí verificamos que el rollback ocurrió
        verify(mockEntityManager).persist(any(Usuario.class));
    }

}

