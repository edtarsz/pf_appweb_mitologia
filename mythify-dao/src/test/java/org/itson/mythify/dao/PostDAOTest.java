/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author crist
 */
public class PostDAOTest {
    @Mock
    private IConexion mockConexion;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @Mock
    private CriteriaBuilder mockCriteriaBuilder;

    @Mock
    private CriteriaQuery<Post> mockCriteriaQuery;

    @Mock
    private Root<Usuario> mockRoot;

    @Mock
    private Join<Usuario, Post> mockJoin;

    @Mock
    private TypedQuery<Post> mockTypedQuery;

    @InjectMocks
    private PostDAO postDAO;

    private Comentario comentario;
    private Usuario usuario;
    private Post post;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar mocks básicos
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);

        postDAO = new PostDAO(mockConexion);

        // Inicializar datos de prueba
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Usuario Test");

        post = new Post();
        post.setIdPost(1);
        post.setTitulo("Post de prueba");
        post.setCategoria("Prueba");
        post.setComentarios(new ArrayList<>());
    }


    @Test
    public void testCrearPost_Exito() throws ModelException {
        // Given
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        Post resultado = postDAO.crearPost(post);

        // Then
        assertNotNull(resultado);
        assertEquals(post.getTitulo(), resultado.getTitulo());
        verify(mockEntityManager).persist(post);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testCrearPost_ErrorPersistencia() {
        // Given
        doThrow(new PersistenceException("Error de persistencia simulado"))
                .when(mockEntityManager).persist(any(Post.class));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.crearPost(post);
        });

        // Verificar comportamiento
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
        assertTrue(exception.getMessage().contains("Error creating post"));
    }

    @Test
    public void testCrearPost_PostNull() {
        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.crearPost(null);
        });

        // Verificar que el mensaje sea correcto
        assertTrue(exception.getMessage().contains("El post no puede ser null"));
    }


    @Test
    public void testActualizarPost_Exito() throws ModelException {
        // Given
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();
        when(mockEntityManager.merge(post)).thenReturn(post);

        // When
        Post resultado = postDAO.actualizarPost(post);

        // Then
        assertNotNull(resultado);
        assertEquals(post.getTitulo(), resultado.getTitulo());
        verify(mockEntityManager).merge(post);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testActualizarPost_ErrorPersistencia() {
        // Given
        doThrow(new PersistenceException("Error de persistencia simulado"))
                .when(mockEntityManager).merge(any(Post.class));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.actualizarPost(post);
        });

        verify(mockTransaction).rollback();
        assertTrue(exception.getMessage().contains("Error updating post"));
    }

    @Test
    public void testActualizarPost_PostNull() {
        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.actualizarPost(null);
        });

        // Verificar que el mensaje sea correcto
        assertTrue(exception.getMessage().contains("El post no puede ser null"));
    }


    @Test
    public void testConsultarPosts_Exito() throws ModelException {
        // Given
        List<Post> posts = List.of(post);
        TypedQuery<Post> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT p FROM Post p", Post.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(posts);

        // When
        List<Post> resultado = postDAO.consultarPosts();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(post, resultado.get(0));
    }


    @Test
    public void testConsultarPosts_ErrorPersistencia() {
        // Given
        when(mockEntityManager.createQuery("SELECT p FROM Post p", Post.class))
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarPosts();
        });

        assertTrue(exception.getMessage().contains("Error al consultar todos los posts"));
    }

    @Test
    public void testConsultarPostsCategoria_Exito() throws ModelException {
        // Given
        List<Post> posts = List.of(post);
        TypedQuery<Post> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT p FROM Post p WHERE p.categoria = :categoria", Post.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("categoria", "Prueba")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(posts);

        // When
        List<Post> resultado = postDAO.consultarPostsCategoria("Prueba");

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(post, resultado.get(0));
    }


    @Test
    public void testConsultarPostsCategoria_ErrorPersistencia() {
        // Given
        when(mockEntityManager.createQuery("SELECT p FROM Post p WHERE p.categoria = :categoria", Post.class))
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarPostsCategoria("Tecnología");
        });

        assertTrue(exception.getMessage().contains("Error al consultar los posts por categoría"));
    }

    @Test
    public void testConsultarPostsCategoria_CategoriaNull() {
        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarPostsCategoria(null);
        });
        assertTrue(exception.getMessage().contains("Error al consultar los posts por categoría"));
    }

    @Test
    public void testConsultarPostsCategoria_SinResultados() throws ModelException {
        // Given
        List<Post> posts = new ArrayList<>();
        TypedQuery<Post> mockQuery = mock(TypedQuery.class);

        when(mockEntityManager.createQuery("SELECT p FROM Post p WHERE p.categoria = :categoria", Post.class))
                .thenReturn(mockQuery);
        when(mockQuery.setParameter("categoria", "Inexistente")).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(posts);

        // When
        List<Post> resultado = postDAO.consultarPostsCategoria("Inexistente");

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testConsultarPostPorID_Exito() throws ModelException {
        // Given
        when(mockEntityManager.find(Post.class, 1)).thenReturn(post);

        // When
        Post resultado = postDAO.consultarPostPorID(1);

        // Then
        assertNotNull(resultado);
        assertEquals(post.getTitulo(), resultado.getTitulo());
    }

    @Test
    public void testConsultarPostPorID_ErrorPersistencia() {
        // Given
        when(mockEntityManager.find(Post.class, 1))
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarPostPorID(1);
        });

        assertTrue(exception.getMessage().contains("Error al consultar el post por ID"));
    }

    @Test
    public void testEliminarPost_Exito() throws ModelException {
        // Given
        post.getComentarios().add(new Comentario()); // Agregar un comentario para probar eliminaciones

        when(mockEntityManager.find(Post.class, 1)).thenReturn(post);

        Query mockQuery = mock(Query.class);
        when(mockEntityManager.createNativeQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyInt(), any())).thenReturn(mockQuery);
        when(mockQuery.executeUpdate()).thenReturn(1);

        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        boolean resultado = postDAO.eliminarPost(1);

        // Then
        assertTrue(resultado);
        verify(mockEntityManager).remove(post);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testEliminarPost_ErrorPersistencia() {
        // Given
        when(mockEntityManager.find(Post.class, 1)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.eliminarPost(1);
        });

        verify(mockTransaction).rollback();
        assertTrue(exception.getMessage().contains("Error deleting post"));
    }

    @Test
    public void testEliminarPost_NoExiste() throws ModelException {
        // Given
        when(mockEntityManager.find(Post.class, 1)).thenReturn(null);

        // When
        boolean resultado = postDAO.eliminarPost(1);

        // Then
        assertFalse(resultado);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testEliminarPost_ErrorEnComentarios() {
        // Given
        post.getComentarios().add(new Comentario());
        when(mockEntityManager.find(Post.class, 1)).thenReturn(post);

        Query mockQuery = mock(Query.class);
        when(mockEntityManager.createNativeQuery(anyString())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyInt(), any())).thenThrow(new PersistenceException("Error de persistencia simulado"));

        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).rollback();

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.eliminarPost(1);
        });

        verify(mockTransaction).rollback();
        assertTrue(exception.getMessage().contains("Error deleting post"));
    }

    @Test
    public void testConsultarCantLikes_PostNoExiste() throws ModelException {
        // Given
        when(mockEntityManager.find(Post.class, 1)).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarCantLikes(1);
        });
        assertTrue(exception.getMessage().contains("No se encontró el post con ID"));
    }

    @Test
    public void testConsultarCantLikes_ErrorPersistencia() {
        // Given
        when(mockEntityManager.find(Post.class, 1)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarCantLikes(1);
        });

        assertTrue(exception.getMessage().contains("Error al consultar la cantidad de likes"));
    }

    @Test
    public void testAnclarPost_Exito() throws ModelException {
        // Given
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();
        when(mockEntityManager.merge(post)).thenReturn(post);

        // When
        Post resultado = postDAO.anclarPost(post);

        // Then
        assertNotNull(resultado);
        assertEquals(post.getTitulo(), resultado.getTitulo());
        verify(mockEntityManager).merge(post);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testAnclarPost_ErrorPersistencia() {
        // Given
        doThrow(new PersistenceException("Error de persistencia simulado"))
                .when(mockEntityManager).merge(any(Post.class));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.anclarPost(post);
        });

        verify(mockTransaction).rollback();
        assertTrue(exception.getMessage().contains("Error pinning post"));
    }

    @Test
    public void testLikearPost_Exito() throws ModelException {
        // Given
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(usuario);
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(post);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        postDAO.likearPost(usuario.getIdUsuario(), post.getIdPost());

        // Then
        assertTrue(usuario.getPostsLikeados().contains(post));
        verify(mockEntityManager).merge(usuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testLikearPost_UsuarioNoEncontrado() {
        // Given
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.likearPost(usuario.getIdUsuario(), post.getIdPost());
        });

        assertTrue(exception.getMessage().contains("No se encontró al usuario con ID"));
    }

    @Test
    public void testLikearPost_PostNoEncontrado() {
        // Given
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(usuario);
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.likearPost(usuario.getIdUsuario(), post.getIdPost());
        });

        assertTrue(exception.getMessage().contains("No se encontró el post con ID"));
    }


    @Test
    public void testDesLikearPost_Exito() throws ModelException {
        // Given
        usuario.getPostsLikeados().add(post);
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(usuario);
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(post);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        postDAO.desLikearPost(usuario.getIdUsuario(), post.getIdPost());

        // Then
        assertFalse(usuario.getPostsLikeados().contains(post));
        verify(mockEntityManager).merge(usuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testDesLikearPost_UsuarioNoEncontrado() {
        // Given
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.desLikearPost(usuario.getIdUsuario(), post.getIdPost());
        });

        assertTrue(exception.getMessage().contains("No se encontró al usuario con ID"));
    }

    @Test
    public void testDesLikearPost_PostNoEncontrado() {
        // Given
        when(mockEntityManager.find(Usuario.class, usuario.getIdUsuario())).thenReturn(usuario);
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.desLikearPost(usuario.getIdUsuario(), post.getIdPost());
        });

        assertTrue(exception.getMessage().contains("No se encontró el post con ID"));
    }

    @Test
    public void testOperacionContadorLike_Exito() throws ModelException {
        // Given
        post.setCantLikes(5);
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(post);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        postDAO.operacionContadorLike(post.getIdPost(), 3);

        // Then
        assertEquals(8, post.getCantLikes());
        verify(mockEntityManager).merge(post);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testOperacionContadorLike_PostNoEncontrado() {
        // Given
        when(mockEntityManager.find(Post.class, post.getIdPost())).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.operacionContadorLike(post.getIdPost(), 3);
        });

        assertTrue(exception.getMessage().contains("No se encontró el post con ID"));
    }

    @Test
    public void testConsultarHotPosts_Exito() throws ModelException {
        // Given
        Post post1 = new Post();
        post1.setIdPost(1);
        post1.setTitulo("Post 1");
        post1.setCantLikes(100);

        Post post2 = new Post();
        post2.setIdPost(2);
        post2.setTitulo("Post 2");
        post2.setCantLikes(90);

        List<Post> hotPosts = List.of(post1, post2);

        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Post> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Post> mockRoot = mock(Root.class);
        Order mockOrder = mock(Order.class); // Mock explícito para "orderBy"
        TypedQuery<Post> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Post.class)).thenReturn(mockRoot);
        when(mockCriteriaBuilder.desc(mockRoot.get("cantLikes"))).thenReturn(mockOrder); // Orden descendente
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.orderBy(mockOrder)).thenReturn(mockCriteriaQuery);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setMaxResults(2)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(hotPosts);

        // When
        List<Post> resultado = postDAO.consultarHotPosts();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Post 1", resultado.get(0).getTitulo());
        assertEquals("Post 2", resultado.get(1).getTitulo());
    }

    @Test
    public void testConsultarHotPosts_ErrorPersistencia() {
        // Given
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class))
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarHotPosts();
        });

        assertTrue(exception.getMessage().contains("Error al consultar los 2 posts con más likes"));
    }


    @Test
    public void testConsultarHotPosts_SinResultados() throws ModelException {
        // Given
        List<Post> hotPosts = new ArrayList<>();

        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Post> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Post> mockRoot = mock(Root.class);
        Order mockOrder = mock(Order.class); // Mock explícito para "orderBy"
        TypedQuery<Post> mockTypedQuery = mock(TypedQuery.class);

        // Mock CriteriaBuilder y CriteriaQuery
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Post.class)).thenReturn(mockRoot);
        when(mockCriteriaBuilder.desc(mockRoot.get("cantLikes"))).thenReturn(mockOrder);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.orderBy(mockOrder)).thenReturn(mockCriteriaQuery);

        // Mock TypedQuery para devolver una lista vacía
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.setMaxResults(2)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(hotPosts);

        // When
        List<Post> resultado = postDAO.consultarHotPosts();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testConsultarPostPropios_Exito() throws ModelException {
        // Given
        List<Post> userPosts = List.of(post);

        // Mocks necesarios
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Post> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Post> mockRoot = mock(Root.class);
        Path<Object> mockPathUsuario = mock(Path.class);
        Path<Object> mockPathIdUsuario = mock(Path.class);
        Predicate mockPredicate = mock(Predicate.class);
        TypedQuery<Post> mockTypedQuery = mock(TypedQuery.class);

        // Configuración del mock de CriteriaBuilder y CriteriaQuery
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Post.class)).thenReturn(mockRoot);

        // Configuración para los métodos de "Path"
        when(mockRoot.get("usuario")).thenReturn(mockPathUsuario);
        when(mockPathUsuario.get("idUsuario")).thenReturn(mockPathIdUsuario);

        // Configuración para Predicate y CriteriaQuery
        when(mockCriteriaBuilder.equal(mockPathIdUsuario, usuario.getIdUsuario())).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Configuración del TypedQuery
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(userPosts);

        // When
        List<Post> resultado = postDAO.consultarPostPropios(usuario.getIdUsuario());

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(post, resultado.get(0));
    }


    @Test
    public void testConsultarPostPropios_ErrorPersistencia() {
        // Given
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class))
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            postDAO.consultarPostPropios(usuario.getIdUsuario());
        });

        assertTrue(exception.getMessage().contains("Error al consultar los posts creados por el usuario"));
    }

    @Test
    public void testConsultarPostPropios_SinResultados() throws ModelException {
        // Given
        List<Post> userPosts = new ArrayList<>();

        // Mocks necesarios
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Post> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Post> mockRoot = mock(Root.class);
        Path<Object> mockPathUsuario = mock(Path.class);
        Path<Object> mockPathIdUsuario = mock(Path.class);
        Predicate mockPredicate = mock(Predicate.class);
        TypedQuery<Post> mockTypedQuery = mock(TypedQuery.class);

        // Configuración del mock de CriteriaBuilder y CriteriaQuery
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Post.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Post.class)).thenReturn(mockRoot);

        // Configuración para los métodos de "Path"
        when(mockRoot.get("usuario")).thenReturn(mockPathUsuario);
        when(mockPathUsuario.get("idUsuario")).thenReturn(mockPathIdUsuario);

        // Configuración para Predicate y CriteriaQuery
        when(mockCriteriaBuilder.equal(mockPathIdUsuario, usuario.getIdUsuario())).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Configuración del TypedQuery
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(userPosts);

        // When
        List<Post> resultado = postDAO.consultarPostPropios(usuario.getIdUsuario());

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

}
