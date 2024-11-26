/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.facade.post;

import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;
import org.itson.mythify.exceptions.ControllerException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class PostFacadeIntegracionTest {

    private PostFacade postFacade;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() throws ControllerException {
        Conexion conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        entityManager = conexion.crearConexion();
        postFacade = new PostFacade();
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Test
    void testOperacionContadorLike_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Post to Like");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postFacade.operacionContadorLike(post.getIdPost(), 1);
        entityManager.flush();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.refresh(post);
        entityManager.getTransaction().commit();

        Post result = entityManager.find(Post.class, post.getIdPost());
        assertEquals(1, result.getCantLikes());
        entityManager.getTransaction().begin();
        postFacade.operacionContadorLike(post.getIdPost(), 1);
        entityManager.flush();
        entityManager.getTransaction().commit();

        assertEquals(1, result.getCantLikes());
    }

    @Test
    void testConsultarCantLikes_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Post to Count Likes");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCantLikes(5);
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        int result = postFacade.consultarCantLikes(post.getIdPost());

        assertEquals(5, result);
    }

    @Test
    void testConsultarPostLikeados_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setApellidoPaterno("Apellido Paterno");
        usuario.setApellidoMaterno("Apellido Materno");
        usuario.setAvatar("avatar.png");
        usuario.setCiudad("Test City");
        usuario.setCorreo("test" + System.currentTimeMillis() + "@example.com");
        usuario.setContrasenia("password");
        usuario.setTelefono("1234567890");
        usuario.setFechaNacimiento(new java.util.Date());
        usuario.setGenero("Masculino");
        usuario.setTipoUsuario(TipoUsuario.NORMAL);
        Estado estado = new Estado();
        estado.setNombre("Test Estado");
        entityManager.persist(estado);

        Municipio municipio = new Municipio();
        municipio.setNombre("Test Municipio");
        municipio.setEstado(estado);
        entityManager.persist(municipio);

        usuario.setMunicipio(municipio);
        entityManager.persist(usuario);

        Post post = new Post();
        post.setTitulo("Liked Post");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postFacade.likearPost(usuario.getIdUsuario(), post.getIdPost());
        entityManager.flush();
        entityManager.getTransaction().commit();

        List<Post> result = postFacade.consultarPostLikeados(usuario.getIdUsuario());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(post.getIdPost(), result.get(0).getIdPost());
    }

    @Test
    void testConsultarHotPosts_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post1 = new Post();
        post1.setTitulo("Hot Post 1");
        post1.setCategoria("Categoria");
        post1.setContenido("Contenido");
        post1.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post1.setCantLikes(10);
        entityManager.persist(post1);

        Post post2 = new Post();
        post2.setTitulo("Hot Post 2");
        post2.setCategoria("Categoria");
        post2.setContenido("Contenido");
        post2.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post2.setCantLikes(15);
        entityManager.persist(post2);
        entityManager.getTransaction().commit();

        List<Post> result = postFacade.consultarHotPosts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hot Post 2", result.get(0).getTitulo());
    }

    @Test
    void testConsultarPostPropios_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setApellidoPaterno("Apellido Paterno");
        usuario.setApellidoMaterno("Apellido Materno");
        usuario.setAvatar("avatar.png");
        usuario.setCiudad("Test City");
        usuario.setCorreo("test" + System.currentTimeMillis() + "@example.com");
        usuario.setContrasenia("password");
        usuario.setTelefono("1234567890");
        usuario.setFechaNacimiento(new java.util.Date());
        usuario.setGenero("Masculino");
        usuario.setTipoUsuario(TipoUsuario.NORMAL);
        Estado estado = new Estado();
        estado.setNombre("Test Estado");
        entityManager.persist(estado);

        Municipio municipio = new Municipio();
        municipio.setNombre("Test Municipio");
        municipio.setEstado(estado);
        entityManager.persist(municipio);

        usuario.setMunicipio(municipio);
        entityManager.persist(usuario);

        Post post = new Post();
        post.setTitulo("User's Post");
        post.setCategoria("Categoria Usuario");
        post.setContenido("Contenido del post del usuario");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setUsuario(usuario);
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postFacade.consultarPostPropios(usuario.getIdUsuario());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(post.getIdPost(), result.get(0).getIdPost());
    }

    @Test
    void testCrearPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        Post result = postFacade.crearPost(post);
        entityManager.getTransaction().commit();

        assertNotNull(result);
        assertEquals("Test Post", result.getTitulo());
    }

    @Test
    void testEliminarPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Post to Delete");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        boolean result = postFacade.eliminarPost(post.getIdPost());
        entityManager.getTransaction().commit();

        assertTrue(result);
    }

    @Test
    void testActualizarPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Initial Post");
        post.setCategoria("Categoria");
        post.setContenido("Contenido inicial");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        post.setTitulo("Updated Post");
        Post result = postFacade.actualizarPost(post);
        entityManager.getTransaction().commit();

        assertNotNull(result);
        assertEquals("Updated Post", result.getTitulo());
    }

    @Test
    void testAnclarPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Post to Pin");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setAnclado(false);
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        post.setAnclado(true);
        Post result = postFacade.anclarPost(post);
        entityManager.getTransaction().commit();

        assertNotNull(result);
        assertTrue(result.isAnclado());
    }

    @Test
    void testConsultarPosts_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postFacade.consultarPosts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testConsultarPostsCategoria_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Categoria Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postFacade.consultarPostsCategoria("Test Categoria");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Test Categoria", result.get(0).getCategoria());
    }

    @Test
    void testConsultarPostPorID_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Post by ID");
        post.setCategoria("Categoria");
        post.setContenido("Contenido");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        Post result = postFacade.consultarPostPorID(post.getIdPost());

        assertNotNull(result);
        assertEquals(post.getIdPost(), result.getIdPost());
    }

    @Test
    void testLikearPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setApellidoPaterno("Apellido Paterno");
        usuario.setApellidoMaterno("Apellido Materno");
        usuario.setAvatar("avatar.png");
        usuario.setCiudad("Test City");
        usuario.setCorreo("test" + System.currentTimeMillis() + "@example.com");
        usuario.setContrasenia("password");
        usuario.setTelefono("1234567890");
        usuario.setFechaNacimiento(new java.util.Date());
        usuario.setGenero("Masculino");
        usuario.setTipoUsuario(TipoUsuario.NORMAL);
        Estado estado = new Estado();
        estado.setNombre("Test Estado");
        entityManager.persist(estado);

        Municipio municipio = new Municipio();
        municipio.setNombre("Test Municipio");
        municipio.setEstado(estado);
        entityManager.persist(municipio);

        usuario.setMunicipio(municipio);
        entityManager.persist(usuario);

        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postFacade.likearPost(usuario.getIdUsuario(), post.getIdPost());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.refresh(usuario);
        entityManager.getTransaction().commit();

        Usuario resultUsuario = entityManager.find(Usuario.class, usuario.getIdUsuario());
        assertTrue(resultUsuario.getPostsLikeados().contains(post));
    }

    @Test
    void testDesLikearPost_Success() throws ControllerException {
        entityManager.getTransaction().begin();
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");
        usuario.setApellidoPaterno("Apellido Paterno");
        usuario.setApellidoMaterno("Apellido Materno");
        usuario.setAvatar("avatar.png");
        usuario.setCiudad("Test City");
        usuario.setCorreo("test" + System.currentTimeMillis() + "@example.com");
        usuario.setContrasenia("password");
        usuario.setTelefono("1234567890");
        usuario.setFechaNacimiento(new java.util.Date());
        usuario.setGenero("Masculino");
        usuario.setTipoUsuario(TipoUsuario.NORMAL);
        Estado estado = new Estado();
        estado.setNombre("Test Estado");
        entityManager.persist(estado);

        Municipio municipio = new Municipio();
        municipio.setNombre("Test Municipio");
        municipio.setEstado(estado);
        entityManager.persist(municipio);

        usuario.setMunicipio(municipio);
        entityManager.persist(usuario);

        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        usuario.getPostsLikeados().add(post);
        entityManager.persist(post);
        entityManager.merge(usuario);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postFacade.desLikearPost(usuario.getIdUsuario(), post.getIdPost());
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.refresh(usuario);
        entityManager.getTransaction().commit();

        Usuario resultUsuario = entityManager.find(Usuario.class, usuario.getIdUsuario());
        assertFalse(resultUsuario.getPostsLikeados().contains(post));
    }
}
