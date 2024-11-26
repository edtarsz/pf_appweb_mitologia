package org.itson.mythify.facade.comentario;

import java.util.List;

import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.IComentarioDAO;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.exceptions.ControllerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class ComentarioFacadeTest {

    @Mock
    private IComentarioDAO comentarioDAO;

    @InjectMocks
    private ComentarioFacade comentarioFacade;

    private Comentario comentario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        comentario = new Comentario();
        comentario.setContenido("Contenido de prueba");
    }

    @Test
    public void testCrearComentario_Exito() throws ModelException, ControllerException {
        when(comentarioDAO.crearComentario(comentario)).thenReturn(comentario);

        Comentario resultado = comentarioFacade.crearComentario(comentario);

        assertNotNull(resultado);
        assertEquals(comentario.getContenido(), resultado.getContenido());
        verify(comentarioDAO).crearComentario(comentario);
    }

    @Test
    public void testCrearComentario_Error() throws ModelException {
        when(comentarioDAO.crearComentario(comentario)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.crearComentario(comentario);
        });
    }

    @Test
    public void testEliminarComentario_Exito() throws ModelException, ControllerException {
        doNothing().when(comentarioDAO).eliminarComentario(1);

        comentarioFacade.eliminarComentario(1);

        verify(comentarioDAO).eliminarComentario(1);
    }

    @Test
    public void testEliminarComentario_Error() throws ModelException {
        doThrow(new ModelException("Error")).when(comentarioDAO).eliminarComentario(1);

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.eliminarComentario(1);
        });
    }

    @Test
    public void testConsultarComentarios_Exito() throws ModelException, ControllerException {
        List<Comentario> comentarios = List.of(comentario);
        when(comentarioDAO.consultarComentarios(1)).thenReturn(comentarios);

        List<Comentario> resultado = comentarioFacade.consultarComentarios(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(comentarioDAO).consultarComentarios(1);
    }

    @Test
    public void testConsultarComentarios_Error() throws ModelException {
        when(comentarioDAO.consultarComentarios(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.consultarComentarios(1);
        });
    }

    @Test
    public void testConsultarComentarioPorID_Exito() throws ModelException, ControllerException {
        when(comentarioDAO.consultarComentarioPorID(1)).thenReturn(comentario);

        Comentario resultado = comentarioFacade.consultarComentarioPorID(1);

        assertNotNull(resultado);
        assertEquals(comentario.getContenido(), resultado.getContenido());
        verify(comentarioDAO).consultarComentarioPorID(1);
    }

    @Test
    public void testConsultarComentarioPorID_Error() throws ModelException {
        when(comentarioDAO.consultarComentarioPorID(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.consultarComentarioPorID(1);
        });
    }

    @Test
    public void testLikearComentario_Exito() throws ModelException, ControllerException {
        doNothing().when(comentarioDAO).likearComentario(1, 1);

        comentarioFacade.likearComentario(1, 1);

        verify(comentarioDAO).likearComentario(1, 1);
    }

    @Test
    public void testLikearComentario_Error() throws ModelException {
        doThrow(new ModelException("Error")).when(comentarioDAO).likearComentario(1, 1);

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.likearComentario(1, 1);
        });
    }

    @Test
    public void testDesLikearComentario_Exito() throws ModelException, ControllerException {
        doNothing().when(comentarioDAO).desLikearComentario(1, 1);

        comentarioFacade.desLikearComentario(1, 1);

        verify(comentarioDAO).desLikearComentario(1, 1);
    }

    @Test
    public void testDesLikearComentario_Error() throws ModelException {
        doThrow(new ModelException("Error")).when(comentarioDAO).desLikearComentario(1, 1);

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.desLikearComentario(1, 1);
        });
    }

    @Test
    public void testOperacionContadorComentario_Exito() throws ModelException, ControllerException {
        doNothing().when(comentarioDAO).operacionContadorComentario(1, 5);

        comentarioFacade.operacionContadorComentario(1, 5);

        verify(comentarioDAO).operacionContadorComentario(1, 5);
    }

    @Test
    public void testOperacionContadorComentario_Error() throws ModelException {
        doThrow(new ModelException("Error")).when(comentarioDAO).operacionContadorComentario(1, 5);

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.operacionContadorComentario(1, 5);
        });
    }

    @Test
    public void testConsultarCantLikes_Exito() throws ModelException, ControllerException {
        when(comentarioDAO.consultarCantLikes(1)).thenReturn(10);

        int cantLikes = comentarioFacade.consultarCantLikes(1);

        assertEquals(10, cantLikes);
        verify(comentarioDAO).consultarCantLikes(1);
    }

    @Test
    public void testConsultarCantLikes_Error() throws ModelException {
        when(comentarioDAO.consultarCantLikes(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.consultarCantLikes(1);
        });
    }

    @Test
    public void testConsultarComentariosLikeados_Exito() throws ModelException, ControllerException {
        List<Comentario> comentarios = List.of(comentario);
        when(comentarioDAO.consultarComentariosLikeados(1)).thenReturn(comentarios);

        List<Comentario> resultado = comentarioFacade.consultarComentariosLikeados(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(comentarioDAO).consultarComentariosLikeados(1);
    }

    @Test
    public void testConsultarComentariosLikeados_Error() throws ModelException {
        when(comentarioDAO.consultarComentariosLikeados(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> {
            comentarioFacade.consultarComentariosLikeados(1);
        });
    }
}
