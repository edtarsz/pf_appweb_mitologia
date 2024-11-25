package org.itson.mythify.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

public class DAOFactoryTest {

    @Test
    public void testConstructorPrivado() throws Exception {
        // Accede al constructor privado
        Constructor<DAOFactory> constructor = DAOFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        // Verifica que arroje la excepción esperada al intentar instanciar la clase
        Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(exception.getCause() instanceof UnsupportedOperationException);
        assertEquals("Esta es una clase utilitaria y no debe ser instanciada.", exception.getCause().getMessage());
    }

    @Test
    public void testInstanciaUsuarioDAO() throws ModelException {
        // Given
        IConexion mockConexion = mock(IConexion.class);

        // When
        UsuarioDAO usuarioDAO = DAOFactory.instanciaUsuarioDAO(mockConexion);

        // Then
        assertNotNull(usuarioDAO);
        assertTrue(usuarioDAO instanceof UsuarioDAO);
    }

    @Test
    public void testInstanciaPostDAO() throws ModelException {
        // Given
        IConexion mockConexion = mock(IConexion.class);

        // When
        PostDAO postDAO = DAOFactory.instanciaPostDAO(mockConexion);

        // Then
        assertNotNull(postDAO);
        assertTrue(postDAO instanceof PostDAO);
    }

    @Test
    public void testInstanciaComentarioDAO() throws ModelException {
        // Given
        IConexion mockConexion = mock(IConexion.class);

        // When
        ComentarioDAO comentarioDAO = DAOFactory.instanciaComentarioDAO(mockConexion);

        // Then
        assertNotNull(comentarioDAO);
        assertTrue(comentarioDAO instanceof ComentarioDAO);
    }
}
