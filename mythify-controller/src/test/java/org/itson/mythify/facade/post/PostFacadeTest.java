package org.itson.mythify.facade.post;

import java.util.Arrays;
import java.util.List;

import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.IPostDAO;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.exceptions.ControllerException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostFacadeTest {

    private PostFacade postFacade;
    private IPostDAO postDAO;

    @BeforeEach
    public void setUp() {
        postDAO = mock(IPostDAO.class);
        postFacade = new PostFacade();
        postFacade.postDAO = postDAO;
    }

    @Test
    public void testCrearPost() throws ControllerException, ModelException {
        Post post = new Post();
        when(postDAO.crearPost(post)).thenReturn(post);

        Post result = postFacade.crearPost(post);
        assertEquals(post, result);
    }

    @Test
    public void testEliminarPost() throws ControllerException, ModelException {
        when(postDAO.eliminarPost(1)).thenReturn(true);

        boolean result = postFacade.eliminarPost(1);
        assertTrue(result);
    }

    @Test
    public void testActualizarPost() throws ControllerException, ModelException {
        Post post = new Post();
        when(postDAO.actualizarPost(post)).thenReturn(post);

        Post result = postFacade.actualizarPost(post);
        assertEquals(post, result);
    }

    @Test
    public void testAnclarPost() throws ControllerException, ModelException {
        Post post = new Post();
        when(postDAO.anclarPost(post)).thenReturn(post);

        Post result = postFacade.anclarPost(post);
        assertEquals(post, result);
    }

    @Test
    public void testConsultarPosts() throws ControllerException, ModelException {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.consultarPosts()).thenReturn(posts);

        List<Post> result = postFacade.consultarPosts();
        assertEquals(posts, result);
    }

    @Test
    public void testConsultarPostsCategoria() throws ControllerException, ModelException {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.consultarPostsCategoria("categoria")).thenReturn(posts);

        List<Post> result = postFacade.consultarPostsCategoria("categoria");
        assertEquals(posts, result);
    }

    @Test
    public void testConsultarPostPorID() throws ControllerException, ModelException {
        Post post = new Post();
        when(postDAO.consultarPostPorID(1)).thenReturn(post);

        Post result = postFacade.consultarPostPorID(1);
        assertEquals(post, result);
    }

    @Test
    public void testLikearPost() throws ControllerException, ModelException {
        doNothing().when(postDAO).likearPost(1, 1);

        postFacade.likearPost(1, 1);
        verify(postDAO, times(1)).likearPost(1, 1);
    }

    @Test
    public void testConsultarCantLikes() throws ControllerException, ModelException {
        when(postDAO.consultarCantLikes(1)).thenReturn(10);

        int result = postFacade.consultarCantLikes(1);
        assertEquals(10, result);
    }

    @Test
    public void testDesLikearPost() throws ControllerException, ModelException {
        doNothing().when(postDAO).desLikearPost(1, 1);

        postFacade.desLikearPost(1, 1);
        verify(postDAO, times(1)).desLikearPost(1, 1);
    }

    @Test
    public void testOperacionContadorLike() throws ControllerException, ModelException {
        doNothing().when(postDAO).operacionContadorLike(1, 1);

        postFacade.operacionContadorLike(1, 1);
        verify(postDAO, times(1)).operacionContadorLike(1, 1);
    }

    @Test
    public void testConsultarPostLikeados() throws ControllerException, ModelException {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.consultarPostLikeados(1)).thenReturn(posts);

        List<Post> result = postFacade.consultarPostLikeados(1);
        assertEquals(posts, result);
    }

    @Test
    public void testConsultarHotPosts() throws ControllerException, ModelException {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.consultarHotPosts()).thenReturn(posts);

        List<Post> result = postFacade.consultarHotPosts();
        assertEquals(posts, result);
    }

    @Test
    public void testConsultarPostPropios() throws ControllerException, ModelException {
        List<Post> posts = Arrays.asList(new Post(), new Post());
        when(postDAO.consultarPostPropios(1)).thenReturn(posts);

        List<Post> result = postFacade.consultarPostPropios(1);
        assertEquals(posts, result);
    }

    @Test
    public void testCrearPostException() throws ModelException {
        Post post = new Post();
        when(postDAO.crearPost(post)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.crearPost(post));
    }

    @Test
    public void testEliminarPostException() throws ModelException {
        when(postDAO.eliminarPost(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.eliminarPost(1));
    }

    @Test
    public void testActualizarPostException() throws ModelException {
        Post post = new Post();
        when(postDAO.actualizarPost(post)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.actualizarPost(post));
    }

    @Test
    public void testAnclarPostException() throws ModelException {
        Post post = new Post();
        when(postDAO.anclarPost(post)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.anclarPost(post));
    }

    @Test
    public void testConsultarPostsException() throws ModelException {
        when(postDAO.consultarPosts()).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarPosts());
    }

    @Test
    public void testConsultarPostsCategoriaException() throws ModelException {
        when(postDAO.consultarPostsCategoria("categoria")).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarPostsCategoria("categoria"));
    }

    @Test
    public void testConsultarPostPorIDException() throws ModelException {
        when(postDAO.consultarPostPorID(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarPostPorID(1));
    }

    @Test
    public void testLikearPostException() throws ModelException {
        doThrow(new ModelException("Error")).when(postDAO).likearPost(1, 1);

        assertThrows(ControllerException.class, () -> postFacade.likearPost(1, 1));
    }

    @Test
    public void testConsultarCantLikesException() throws ModelException {
        when(postDAO.consultarCantLikes(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarCantLikes(1));
    }

    @Test
    public void testDesLikearPostException() throws ModelException {
        doThrow(new ModelException("Error")).when(postDAO).desLikearPost(1, 1);

        assertThrows(ControllerException.class, () -> postFacade.desLikearPost(1, 1));
    }

    @Test
    public void testOperacionContadorLikeException() throws ModelException {
        doThrow(new ModelException("Error")).when(postDAO).operacionContadorLike(1, 1);

        assertThrows(ControllerException.class, () -> postFacade.operacionContadorLike(1, 1));
    }

    @Test
    public void testConsultarPostLikeadosException() throws ModelException {
        when(postDAO.consultarPostLikeados(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarPostLikeados(1));
    }

    @Test
    public void testConsultarHotPostsException() throws ModelException {
        when(postDAO.consultarHotPosts()).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarHotPosts());
    }

    @Test
    public void testConsultarPostPropiosException() throws ModelException {
        when(postDAO.consultarPostPropios(1)).thenThrow(new ModelException("Error"));

        assertThrows(ControllerException.class, () -> postFacade.consultarPostPropios(1));
    }
}
