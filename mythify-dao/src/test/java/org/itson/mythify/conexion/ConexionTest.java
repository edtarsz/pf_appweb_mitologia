package org.itson.mythify.conexion;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

public class ConexionTest {

    @Mock
    private EntityManagerFactory mockEmFactory;  // Mock de EntityManagerFactory

    @Mock
    private EntityManager mockEntityManager;    // Mock de EntityManager

    private Conexion conexion;  // Objeto bajo prueba

    @BeforeEach
    public void setUp() {
        // Inicializar los mocks antes de cada prueba
        MockitoAnnotations.openMocks(this);
        conexion = new Conexion(mockEmFactory);  // Inyectamos el mock en la clase Conexion
    }

    @Test
    public void testCrearConexion() {
        // Simulamos que createEntityManager() devuelve el mockEntityManager
        when(mockEmFactory.createEntityManager()).thenReturn(mockEntityManager);

        // Llamamos al método bajo prueba
        EntityManager result = conexion.crearConexion();

        // Verificamos que el método createEntityManager() fue llamado
        verify(mockEmFactory).createEntityManager();

        // Verificamos que el resultado no es null y es la instancia esperada
        assertNotNull(result);
        assertEquals(mockEntityManager, result);
    }

    @Test
    public void testCerrarConexion_EntidadManagerFactoryAbierto() {
        // Simulamos que el EntityManagerFactory está abierto
        when(mockEmFactory.isOpen()).thenReturn(true);

        // Llamamos al método bajo prueba
        conexion.cerrarConexion();

        // Verificamos que se haya llamado al método close() en el EntityManagerFactory
        verify(mockEmFactory).close();
    }

    @Test
    public void testCerrarConexion_EntidadManagerFactoryCerrado() {
        // Simulamos que el EntityManagerFactory no está abierto
        when(mockEmFactory.isOpen()).thenReturn(false);

        // Llamamos al método bajo prueba
        conexion.cerrarConexion();

        // Verificamos que no se haya llamado al método close()
        verify(mockEmFactory, never()).close();
    }

    @Test
    public void testCerrarConexion_EntidadManagerFactoryNull() {
        // Simulamos que el EntityManagerFactory es null
        conexion = new Conexion(null);

        // Llamamos al método bajo prueba
        conexion.cerrarConexion();

        // Verificamos que no se llame a ningún método, porque el emFactory es null
        verify(mockEmFactory, never()).close();
    }
}

