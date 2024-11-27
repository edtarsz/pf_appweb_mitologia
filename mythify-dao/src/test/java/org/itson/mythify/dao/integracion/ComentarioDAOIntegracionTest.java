package org.itson.mythify.dao.integracion;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.ComentarioDAO;
import org.itson.mythify.entidad.Comentario;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ComentarioDAOIntegracionTest {

    private static EntityManager entityManager;
    private static ComentarioDAO comentarioDAO;
    private static Usuario testUsuario;
    private static Post testPost;
    private static IConexion conexion;

    @BeforeAll
    public static void setUpClass() {
        // Crear una conexión y un EntityManager
        conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        entityManager = conexion.crearConexion();
        comentarioDAO = new ComentarioDAO(conexion);

        // Crear un usuario y un post de prueba
        entityManager.getTransaction().begin();

        Municipio testMunicipio = new Municipio("Cajeme", new Estado("Sonora"));
        entityManager.persist(testMunicipio);

        testUsuario = new Usuario();
        testUsuario.setNombre("Test User");
        testUsuario.setApellidoPaterno("Test");
        testUsuario.setApellidoMaterno("User");
        testUsuario.setCorreo("testuser@example.com");
        testUsuario.setContrasenia("password");
        testUsuario.setTelefono("1234567890");
        testUsuario.setAvatar("avatar.png");
        testUsuario.setCiudad("Obregón");
        testUsuario.setFechaNacimiento(new Date());
        testUsuario.setGenero("Masculino");
        testUsuario.setTipoUsuario(TipoUsuario.NORMAL);
        testUsuario.setMunicipio(testMunicipio);
        entityManager.persist(testUsuario);

        testPost = new Post("Titulo", "Contenido del post", "Azteca", LocalDateTime.now(), true, testUsuario);
        entityManager.persist(testPost);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    public static void tearDownClass() {
        // Eliminar el usuario y el post de prueba
        entityManager.getTransaction().begin();
        entityManager.remove(testUsuario);
        entityManager.remove(testPost);
        entityManager.getTransaction().commit();
        entityManager.close();
        conexion.cerrarConexion();
    }

    @Test
    public void testCrearComentario() throws ModelException {
        // Probar la creación de un comentario
        Comentario comentario = new Comentario("Comentario de prueba", LocalDateTime.now(), testUsuario, testPost);
        Comentario result = comentarioDAO.crearComentario(comentario);
        assertNotNull(result);
        assertEquals("Comentario de prueba", result.getContenido());
    }

    @Test
    public void testEliminarComentario() {
        // Probar la eliminación de un comentario
        Comentario comentario = new Comentario("Comentario de prueba", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        assertDoesNotThrow(() -> comentarioDAO.eliminarComentario(comentario.getIdComentario()));
    }

    @Test
    public void testConsultarComentarios() throws ModelException {
        // Probar la consulta de comentarios de un post
        Comentario comentario = new Comentario("Comentario de prueba", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        List<Comentario> comentarios = comentarioDAO.consultarComentarios(testPost.getIdPost());
        assertFalse(comentarios.isEmpty());
    }

    @Test
    public void testConsultarComentarioPorID() throws ModelException {
        // Probar la consulta de un comentario por ID
        Comentario comentario = new Comentario("Comentarios de prueba para consulta por ID", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        Comentario result = comentarioDAO.consultarComentarioPorID(comentario.getIdComentario());
        assertNotNull(result);
        assertEquals("Comentarios de prueba para consulta por ID", result.getContenido());
    }

    @Test
    public void testLikearComentario() {
        // Probar la acción de likear un comentario
        Comentario comentario = new Comentario("Comentario de prueba para likes", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        assertDoesNotThrow(() -> comentarioDAO.likearComentario(testUsuario.getIdUsuario(), comentario.getIdComentario()));
    }

    @Test
    public void testDesLikearComentario() throws ModelException {
        // Probar la acción de deslikear un comentario
        Comentario comentario = new Comentario("Comentario de prueba para dislikear", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        comentarioDAO.likearComentario(testUsuario.getIdUsuario(), comentario.getIdComentario());
        assertDoesNotThrow(() -> comentarioDAO.desLikearComentario(testUsuario.getIdUsuario(), comentario.getIdComentario()));
    }

    @Test
    public void testOperacionContadorComentario() throws ModelException {
        // Probar la operación de contador de likes en un comentario
        Comentario comentario = new Comentario("Comentario de prueba para el contador", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        assertDoesNotThrow(() -> comentarioDAO.operacionContadorComentario(comentario.getIdComentario(), 1));
        assertEquals(1, comentarioDAO.consultarCantLikes(comentario.getIdComentario()));
    }

    @Test
    public void testConsultarCantLikes() throws ModelException, ModelException {
        // Probar la consulta de la cantidad de likes de un comentario
        Comentario comentario = new Comentario("Comentario de prueba para los likes", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        comentarioDAO.operacionContadorComentario(comentario.getIdComentario(), 1);
        int likes = comentarioDAO.consultarCantLikes(comentario.getIdComentario());
        assertEquals(1, likes);
    }

    @Test
    public void testConsultarComentariosLikeados() throws ModelException {
        // Probar la consulta de comentarios likeados por un usuario
        Comentario comentario = new Comentario("Comentario de prueba para los comentarios", LocalDateTime.now(), testUsuario, testPost);
        entityManager.getTransaction().begin();
        entityManager.persist(comentario);
        entityManager.getTransaction().commit();

        comentarioDAO.likearComentario(testUsuario.getIdUsuario(), comentario.getIdComentario());
        List<Comentario> comentariosLikeados = comentarioDAO.consultarComentariosLikeados(testUsuario.getIdUsuario());
        assertFalse(comentariosLikeados.isEmpty());
    }
}