package org.itson.mythify.dao.integracion;

import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.dao.PostDAO;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Post;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;

import static org.junit.jupiter.api.Assertions.*;
import org.itson.mythify.entidad.Estado;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class PostDAOIntegracionTest {

    private PostDAO postDAO;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        Conexion conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        entityManager = conexion.crearConexion();
        postDAO = new PostDAO(conexion);
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Test
    void testCrearPost_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());

        Post result = postDAO.crearPost(post);

        assertNotNull(result);
        assertEquals("Test Post", result.getTitulo());
        entityManager.getTransaction().commit();
    }

    @Test
    void testCrearPost_NullPost_ThrowsException() {
        assertThrows(ModelException.class, () -> postDAO.crearPost(null));
    }

    @Test
    void testActualizarPost_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Initial Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        post.setTitulo("Updated Post");
        Post result = postDAO.actualizarPost(post);
        entityManager.getTransaction().commit();

        assertNotNull(result);
        assertEquals("Updated Post", result.getTitulo());
    }

    @Test
    void testAnclarPost_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setAnclado(false);
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        post.setAnclado(true);
        Post result = postDAO.anclarPost(post);
        entityManager.getTransaction().commit();

        assertNotNull(result);
        assertTrue(result.isAnclado());
    }

    @Test
    void testConsultarPosts_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postDAO.consultarPosts();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testConsultarPostsCategoria_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("TestCategoria");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postDAO.consultarPostsCategoria("TestCategoria");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("TestCategoria", result.get(0).getCategoria());
    }

    @Test
    void testEliminarPost_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        boolean result = postDAO.eliminarPost(post.getIdPost());
        entityManager.getTransaction().commit();

        assertTrue(result);
    }

    @Test
    void testEliminarPost_NotFound() throws ModelException {
        entityManager.getTransaction().begin();
        boolean result = postDAO.eliminarPost(9999);
        entityManager.getTransaction().commit();

        assertFalse(result);
    }

    @Test
    void testConsultarPostPorID_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post = new Post();
        post.setTitulo("Test Post");
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        Post result = postDAO.consultarPostPorID(post.getIdPost());

        assertNotNull(result);
        assertEquals(post.getIdPost(), result.getIdPost());
    }

    @Test
    void testConsultarPostPorID_NotFound() throws ModelException {
        Post result = postDAO.consultarPostPorID(9999);

        assertNull(result);
    }

    @Test
    void testLikearPost_Success() throws ModelException {
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
        usuario.setTipoUsuario(TipoUsuario.NORMAL); Estado estado = new Estado();
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
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postDAO.likearPost(usuario.getIdUsuario(), post.getIdPost());
        entityManager.merge(usuario);
        entityManager.merge(post);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.refresh(usuario);
        entityManager.getTransaction().commit();

        Usuario resultUsuario = entityManager.find(Usuario.class, usuario.getIdUsuario());
        assertTrue(resultUsuario.getPostsLikeados().contains(post));
    }

    @Test
    void testDesLikearPost_Success() throws ModelException {
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
        usuario.setTipoUsuario(TipoUsuario.NORMAL); Estado estado = new Estado();
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
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        usuario.getPostsLikeados().add(post);
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.merge(usuario);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        postDAO.desLikearPost(usuario.getIdUsuario(), post.getIdPost());
        entityManager.merge(usuario);
        entityManager.merge(post);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.refresh(usuario);
        entityManager.getTransaction().commit();

        Usuario resultUsuario = entityManager.find(Usuario.class, usuario.getIdUsuario());
        assertFalse(resultUsuario.getPostsLikeados().contains(post));
    }

    @Test
    void testConsultarHotPosts_Success() throws ModelException {
        entityManager.getTransaction().begin();
        Post post1 = new Post();
        post1.setTitulo("Hot Post 1");
        post1.setCategoria("Categoria Hot");
        post1.setContenido("Contenido del hot post 1");
        post1.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post1.setCantLikes(10);
        entityManager.persist(post1);

        Post post2 = new Post();
        post2.setTitulo("Hot Post 2");
        post2.setCategoria("Categoria Hot");
        post2.setContenido("Contenido del hot post 2");
        post2.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post2.setCantLikes(15);
        entityManager.persist(post2);
        entityManager.getTransaction().commit();

        List<Post> result = postDAO.consultarHotPosts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hot Post 2", result.get(0).getTitulo());
    }

    @Test
    void testConsultarPostPropios_Success() throws ModelException {
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
        usuario.setTipoUsuario(TipoUsuario.NORMAL); Estado estado = new Estado();
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
        post.setCategoria("Categoria Usuario");
        post.setContenido("Contenido del post del usuario");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        post.setUsuario(usuario);
        post.setCategoria("Test Categoria");
        post.setContenido("Contenido de prueba");
        post.setFechaHoraCreacion(java.time.LocalDateTime.now());
        entityManager.persist(post);
        entityManager.getTransaction().commit();

        List<Post> result = postDAO.consultarPostPropios(usuario.getIdUsuario());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("User's Post", result.get(0).getTitulo());
    }
}
