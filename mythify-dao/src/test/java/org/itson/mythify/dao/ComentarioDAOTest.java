/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author user
 */
public class ComentarioDAOTest {

    @Mock
    private IConexion mockConexion;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @InjectMocks
    private ComentarioDAO comentarioDAO;

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

        comentarioDAO = new ComentarioDAO(mockConexion);

        // Inicializar datos de prueba
        usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Usuario Test");

        post = new Post();
        post.setIdPost(1);
        post.setTitulo("Post Test");

        comentario = new Comentario();
        comentario.setContenido("Contenido de prueba");
        comentario.setUsuario(usuario);
        comentario.setPost(post);
    }

    @Test
    public void testCrearComentario_Exito() throws ModelException {
        // Given
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        Comentario resultado = comentarioDAO.crearComentario(comentario);

        // Then
        assertNotNull(resultado);
        assertEquals(comentario.getContenido(), resultado.getContenido());
        assertEquals(comentario.getUsuario(), resultado.getUsuario());
        assertEquals(comentario.getPost(), resultado.getPost());

        verify(mockEntityManager).persist(comentario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testCrearComentario_ContenidoVacio() {
        // Given
        comentario.setContenido("");

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.crearComentario(comentario);
        });

        assertEquals("El contenido del comentario no puede estar vacío", exception.getMessage());
    }

    @Test
    public void testCrearComentario_ComentarioNull() {
        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.crearComentario(null);
        });
        assertNotNull(exception);
    }

    @Test
    public void testCrearComentario_SinUsuario() {
        // Given
        comentario.setUsuario(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.crearComentario(comentario);
        });

        assertEquals("El usuario del comentario no puede ser nulo", exception.getMessage());
    }

    @Test
    public void testCrearComentario_ErrorPersistencia() {
        // Given
        doThrow(new PersistenceException("Error de persistencia simulado"))
                .when(mockEntityManager).persist(any(Comentario.class));

        // When/Then
        Exception exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.crearComentario(comentario);
        });

        // Verify
        verify(mockTransaction).begin();
        verify(mockTransaction).isActive();
        verify(mockTransaction).rollback();
        verify(mockEntityManager).persist(any(Comentario.class));

        assertTrue(exception.getMessage().contains("Error al persistir"));
    }

    @Test
    public void testEliminarComentario_Exito() throws ModelException {
        // Given
        int idComentario = 1;
        Comentario comentarioPadre = new Comentario();
        comentarioPadre.setIdComentario(idComentario);

        // Mockear la obtención del comentario
        when(mockEntityManager.getReference(Comentario.class, idComentario)).thenReturn(comentarioPadre);

        // Crear mocks para CriteriaBuilder y CriteriaDelete
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaDelete<Comentario> mockDeleteHijosQuery = mock(CriteriaDelete.class);
        CriteriaDelete<Comentario> mockDeletePadreQuery = mock(CriteriaDelete.class);
        Root<Comentario> mockHijosRoot = mock(Root.class);
        Root<Comentario> mockPadreRoot = mock(Root.class);

        // Mock Path objects
        javax.persistence.criteria.Path<Object> mockComentarioPadrePath = mock(javax.persistence.criteria.Path.class);
        javax.persistence.criteria.Path<Object> mockIdComentarioPath = mock(javax.persistence.criteria.Path.class);

        // Configurar comportamiento para eliminar hijos
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createCriteriaDelete(Comentario.class))
                .thenReturn(mockDeleteHijosQuery)
                .thenReturn(mockDeletePadreQuery);
        when(mockDeleteHijosQuery.from(Comentario.class)).thenReturn(mockHijosRoot);
        when(mockDeletePadreQuery.from(Comentario.class)).thenReturn(mockPadreRoot);

        // Configurar Path objects correctamente
        when(mockHijosRoot.get("comentarioPadre")).thenReturn(mockComentarioPadrePath);
        when(mockPadreRoot.get("idComentario")).thenReturn(mockIdComentarioPath);

        // Mock para el where clause
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockComentarioPadrePath, comentarioPadre)).thenReturn(mockPredicate);
        when(mockCriteriaBuilder.equal(mockIdComentarioPath, idComentario)).thenReturn(mockPredicate);
        when(mockDeleteHijosQuery.where(any(Predicate.class))).thenReturn(mockDeleteHijosQuery);
        when(mockDeletePadreQuery.where(any(Predicate.class))).thenReturn(mockDeletePadreQuery);

        // Mock de los queries
        Query mockHijosQuery = mock(Query.class);
        Query mockPadreQuery = mock(Query.class);
        when(mockEntityManager.createQuery(mockDeleteHijosQuery)).thenReturn(mockHijosQuery);
        when(mockEntityManager.createQuery(mockDeletePadreQuery)).thenReturn(mockPadreQuery);

        // Configurar resultados de las eliminaciones
        when(mockHijosQuery.executeUpdate()).thenReturn(2); // Simular que se eliminaron 2 comentarios hijos
        when(mockPadreQuery.executeUpdate()).thenReturn(1); // Simular que se eliminó 1 comentario padre

        // When
        comentarioDAO.eliminarComentario(idComentario);

        // Then
        verify(mockTransaction).begin();
        verify(mockHijosQuery).executeUpdate(); // Verificar eliminación de hijos
        verify(mockPadreQuery).executeUpdate(); // Verificar eliminación del padre
        verify(mockTransaction).commit();
    }

    @Test
    public void testEliminarComentario_ComentarioNoEncontrado() {
        // Given
        int idComentario = 1;
        when(mockEntityManager.getReference(Comentario.class, idComentario)).thenThrow(new IllegalArgumentException());

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.eliminarComentario(idComentario);
        });

        assertEquals("No se encontró el comentario con ID: " + idComentario, exception.getMessage());
    }

    @Test
    public void testEliminarComentario_ErrorPersistencia() {
        // Given
        int idComentario = 1;
        Comentario comentarioPadre = new Comentario();
        comentarioPadre.setIdComentario(idComentario);

        // Mock del CriteriaBuilder y objetos relacionados
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaDelete<Comentario> mockCriteriaDelete = mock(CriteriaDelete.class);
        Root<Comentario> mockRoot = mock(Root.class);

        // Configurar comportamiento de los mocks
        when(mockEntityManager.getReference(Comentario.class, idComentario)).thenReturn(comentarioPadre);
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createCriteriaDelete(Comentario.class)).thenReturn(mockCriteriaDelete);
        when(mockCriteriaDelete.from(Comentario.class)).thenReturn(mockRoot);

        // Simular el error de persistencia en la ejecución del query
        Query mockQuery = mock(Query.class);
        when(mockEntityManager.createQuery(any(CriteriaDelete.class)))
                .thenReturn(mockQuery);
        when(mockQuery.executeUpdate())
                .thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.eliminarComentario(idComentario);
        });

        // Verify
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
        verify(mockEntityManager).createQuery(any(CriteriaDelete.class));

        // Verificar el mensaje de error
        assertTrue(exception.getMessage().contains("Error de persistencia al eliminar el comentario"));
        assertTrue(exception.getCause() instanceof PersistenceException);
    }

    @Test
    public void testConsultarComentarios_Exito() throws ModelException {
        // Given
        int idPost = 1;
        Comentario comentario1 = new Comentario();
        comentario1.setContenido("Comentario 1");
        Comentario comentario2 = new Comentario();
        comentario2.setContenido("Comentario 2");
        List<Comentario> comentarios = List.of(comentario1, comentario2);

        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Comentario> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Comentario> mockRoot = mock(Root.class);
        Predicate mockPredicate = mock(Predicate.class);
        TypedQuery<Comentario> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Comentario.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Comentario.class)).thenReturn(mockRoot);
        Path<Object> mockPath = mock(Path.class);
        when(mockRoot.get("post")).thenReturn(mockPath);
        when(mockPath.get("idPost")).thenReturn(mockPath);
        when(mockCriteriaBuilder.equal(mockPath, idPost)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(comentarios);

        // When
        List<Comentario> resultado = comentarioDAO.consultarComentarios(idPost);

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Comentario 1", resultado.get(0).getContenido());
        assertEquals("Comentario 2", resultado.get(1).getContenido());
    }

    @Test
    public void testConsultarComentarios_ErrorPersistencia() {
        // Given
        int idPost = 1;
        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Comentario> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Comentario> mockRoot = mock(Root.class);
        Predicate mockPredicate = mock(Predicate.class);

        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Comentario.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Comentario.class)).thenReturn(mockRoot);
        Path<Object> mockPath = mock(Path.class);
        when(mockRoot.get("post")).thenReturn(mockPath);
        when(mockPath.get("idPost")).thenReturn(mockPath);
        when(mockCriteriaBuilder.equal(mockPath, idPost)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.consultarComentarios(idPost);
        });

        assertTrue(exception.getMessage().contains("Error fetching comments for post with id: " + idPost));
    }

    @Test
    public void testConsultarComentarioPorID_Exito() throws ModelException {
        // Given
        int idComentario = 1;
        Comentario comentarioEsperado = new Comentario();
        comentarioEsperado.setIdComentario(idComentario);
        comentarioEsperado.setContenido("Comentario de prueba");

        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentarioEsperado);

        // When
        Comentario resultado = comentarioDAO.consultarComentarioPorID(idComentario);

        // Then
        assertNotNull(resultado);
        assertEquals(idComentario, resultado.getIdComentario());
        assertEquals("Comentario de prueba", resultado.getContenido());
    }

    @Test
    public void testConsultarComentarioPorID_ComentarioNoEncontrado() throws ModelException {
        // Given
        int idComentario = 1;

        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(null);

        // When
        Comentario resultado = comentarioDAO.consultarComentarioPorID(idComentario);

        // Then
        assertNull(resultado);
    }

    @Test
    public void testConsultarComentarioPorID_ErrorPersistencia() {
        // Given
        int idComentario = 1;

        when(mockEntityManager.find(Comentario.class, idComentario)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.consultarComentarioPorID(idComentario);
        });

        assertTrue(exception.getMessage().contains("Error al consultar el comentario por ID"));
    }

    @Test
    public void testLikearComentario_Exito() throws ModelException {
        // Given
        int idUsuario = 1;
        int idComentario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);

        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        comentarioDAO.likearComentario(idUsuario, idComentario);

        // Then
        assertTrue(usuario.getComentariosLikeados().contains(comentario));
        verify(mockEntityManager).merge(usuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testLikearComentario_ErrorPersistencia() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        when(mockEntityManager.find(Usuario.class, idUsuario)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.likearComentario(idUsuario, idComentario);
        });

        assertTrue(exception.getMessage().contains("Error al dar like al comentario"));
    }

    @Test
    public void testLikearComentario_UsuarioNoEncontrado() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        // Simula que el EntityManager no encuentra al usuario
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.likearComentario(idUsuario, idComentario);
        });

        // Verifica que el mensaje de la excepción es el esperado
        assertEquals("No se encontró al usuario con ID: " + idUsuario, exception.getMessage());
    }

    @Test
    public void testLikearComentario_ComentarioNoEncontrado() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        // Configurar mocks
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.likearComentario(idUsuario, idComentario);
        });

        // Verifica que el mensaje de la excepción es el esperado
        assertEquals("No se encontró el comentario con ID: " + idComentario, exception.getMessage());

    }

    @Test
    public void testLikearComentario_ComentarioYaLikeado() throws ModelException {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);

        // Simula que el comentario ya fue likeado por el usuario
        usuario.getComentariosLikeados().add(comentario);

        // Configurar mocks
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);

        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        comentarioDAO.likearComentario(idUsuario, idComentario);

        // Then
        // Verifica que no se agregó el comentario nuevamente
        assertEquals(1, usuario.getComentariosLikeados().size());

    }

    @Test
    public void testLikearComentario_TransaccionNoActiva() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        // Configurar mocks
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenThrow(new PersistenceException("Error simulado"));
        when(mockTransaction.isActive()).thenReturn(false);

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.likearComentario(idUsuario, idComentario);
        });

        // Verifica que el mensaje de la excepción es el esperado
        assertTrue(exception.getMessage().contains("Error al dar like al comentario"));
    }

    @Test
    public void testDesLikearComentario_Exito() throws ModelException {
        // Given
        int idUsuario = 1;
        int idComentario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);
        usuario.getComentariosLikeados().add(comentario);

        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        comentarioDAO.desLikearComentario(idUsuario, idComentario);

        // Then
        assertFalse(usuario.getComentariosLikeados().contains(comentario));
        verify(mockEntityManager).merge(usuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testDesLikearComentario_ErrorPersistencia() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        when(mockEntityManager.find(Usuario.class, idUsuario)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.desLikearComentario(idUsuario, idComentario);
        });

        assertTrue(exception.getMessage().contains("Error al quitar el like del comentario"));
    }

    @Test
    public void testDesLikearComentario_UsuarioNoEncontrado() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        // Simular que el usuario no existe en la base de datos
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.desLikearComentario(idUsuario, idComentario);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("No se encontró al usuario con ID: " + idUsuario, exception.getMessage());
    }

    @Test
    public void testDesLikearComentario_ComentarioNoEncontrado() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);

        // Simular que el usuario existe pero el comentario no
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(null);

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.desLikearComentario(idUsuario, idComentario);
        });

        // Verificar que el mensaje de la excepción es el esperado
        assertEquals("No se encontró el comentario con ID: " + idComentario, exception.getMessage());
    }

    @Test
    public void testDesLikearComentario_UsuarioNoHaDadoLike() throws ModelException {
        // Given
        int idUsuario = 1;
        int idComentario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);

        // Simular que el usuario y el comentario existen, pero el usuario no ha dado like al comentario
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenReturn(usuario);
        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        comentarioDAO.desLikearComentario(idUsuario, idComentario);

        // Then
        // Verificar que no se modificó la lista de comentarios likeados
        assertFalse(usuario.getComentariosLikeados().contains(comentario));
        verify(mockEntityManager).merge(usuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testDesLikearComentario_TransaccionNoActiva() {
        // Given
        int idUsuario = 1;
        int idComentario = 1;

        // Simular que ocurre una excepción de persistencia
        when(mockEntityManager.find(Usuario.class, idUsuario)).thenThrow(new PersistenceException("Error de persistencia simulado"));
        when(mockTransaction.isActive()).thenReturn(false);

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.desLikearComentario(idUsuario, idComentario);
        });

        // Verificar que no se intentó hacer rollback porque la transacción no estaba activa
        verify(mockTransaction, never()).rollback();
    }


    @Test
    public void testOperacionContadorComentario_Exito() throws ModelException {
        // Given
        int idComentario = 1;
        int cantidad = 5;
        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);
        comentario.setCantLikes(0);

        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // When
        comentarioDAO.operacionContadorComentario(idComentario, cantidad);

        // Then
        assertEquals(5, comentario.getCantLikes());
        verify(mockEntityManager).merge(comentario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testOperacionContadorComentario_ErrorPersistencia() {
        // Given
        int idComentario = 1;
        int cantidad = 5;

        when(mockEntityManager.find(Comentario.class, idComentario)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.operacionContadorComentario(idComentario, cantidad);
        });

        assertTrue(exception.getMessage().contains("Error al aumentar el contador de likes"));
    }

    @Test
    public void testConsultarCantLikes_Exito() throws ModelException {
        // Given
        int idComentario = 1;
        Comentario comentario = new Comentario();
        comentario.setIdComentario(idComentario);
        comentario.setCantLikes(10);

        when(mockEntityManager.find(Comentario.class, idComentario)).thenReturn(comentario);

        // When
        int cantLikes = comentarioDAO.consultarCantLikes(idComentario);

        // Then
        assertEquals(10, cantLikes);
    }

    @Test
    public void testConsultarCantLikes_ErrorPersistencia() {
        // Given
        int idComentario = 1;

        when(mockEntityManager.find(Comentario.class, idComentario)).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            comentarioDAO.consultarCantLikes(idComentario);
        });

        assertTrue(exception.getMessage().contains("Error al consultar la cantidad de likes"));
    }

    @Test
    public void testConsultarComentariosLikeados_Exito() throws ModelException {
        // Given
        int idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        Comentario comentario1 = new Comentario();
        comentario1.setIdComentario(1);
        Comentario comentario2 = new Comentario();
        comentario2.setIdComentario(2);
        usuario.getComentariosLikeados().add(comentario1);
        usuario.getComentariosLikeados().add(comentario2);

        CriteriaBuilder mockCriteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Comentario> mockCriteriaQuery = mock(CriteriaQuery.class);
        Root<Usuario> mockRoot = mock(Root.class);
        Join<Usuario, Comentario> mockJoin = mock(Join.class);
        Predicate mockPredicate = mock(Predicate.class);
        TypedQuery<Comentario> mockTypedQuery = mock(TypedQuery.class);

        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Comentario.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);
        when(mockRoot.<Usuario, Comentario>join("comentariosLikeados")).thenReturn(mockJoin);
        when(mockCriteriaBuilder.equal(mockRoot.get("idUsuario"), idUsuario)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockJoin)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockTypedQuery);
        when(mockTypedQuery.getResultList()).thenReturn(List.of(comentario1, comentario2));

        // When
        List<Comentario> comentariosLikeados = comentarioDAO.consultarComentariosLikeados(idUsuario);

        // Then
        assertNotNull(comentariosLikeados);
        assertEquals(2, comentariosLikeados.size());
        assertTrue(comentariosLikeados.contains(comentario1));
        assertTrue(comentariosLikeados.contains(comentario2));
    }

    @Test
    public void testConsultarComentariosLikeados_ErrorPersistencia() {
        // Given
        int idUsuario = 1;

        when(mockEntityManager.getCriteriaBuilder()).thenThrow(new PersistenceException("Error de persistencia simulado"));

        // When/Then
        ModelException exception = assertThrows(ModelException.class, () -> {
            comentarioDAO.consultarComentariosLikeados(idUsuario);
        });

        assertTrue(exception.getMessage().contains("Error al consultar los comentarios likeados"));
    }
}
