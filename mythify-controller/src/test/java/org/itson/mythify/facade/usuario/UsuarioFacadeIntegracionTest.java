package org.itson.mythify.facade.usuario;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.itson.mythify.conexion.Conexion;
import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.entidad.Estado;
import org.itson.mythify.entidad.Municipio;
import org.itson.mythify.entidad.Usuario;
import org.itson.mythify.enumeradores.TipoUsuario;
import org.itson.mythify.exceptions.ControllerException;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioFacadeIntegracionTest {

    private IConexion conexion;
    private UsuarioFacade usuarioFacade;
    private EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        conexion = new Conexion(Persistence.createEntityManagerFactory("mythifyPU"));
        entityManager = conexion.crearConexion();
        usuarioFacade = new UsuarioFacade();
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @Test
    public void testCrearUsuario() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Cajeme");
        Estado estado = new Estado();
        estado.setNombre("Sonora");
        municipio.setEstado(estado);
        Usuario usuario = new Usuario("Jorge", "Avitia", "Gutierez", "jorge@example.com", "password123", "1234567890", "avatar.png", "Pueblo Yaqui", new Date(), "Masculino", TipoUsuario.NORMAL, municipio);
        try {
            if (!usuarioFacade.verificarCorreoExistente(usuario.getCorreo())) {
                usuarioFacade.crearUsuario(usuario);
                Usuario createdUser = usuarioFacade.consultarUsuario("jorge@example.com", "password123");
                assertNotNull(createdUser);
                assertEquals("jorge@example.com", createdUser.getCorreo());
            } else {
                System.out.println("User with email " + usuario.getCorreo() + " already exists.");
            }
        } catch (ControllerException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testConsultarUsuario() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Cajeme");
        Estado estado = new Estado();
        estado.setNombre("Sonora");
        municipio.setEstado(estado);
        Usuario usuario = new Usuario("María", "Gonzales", "Gutierez", "maria@example.com", "password123", "1234567890", "avatar.png", "Pueblo Yaqui", new Date(), "Masculino", TipoUsuario.NORMAL, municipio);
        try {
            if (!usuarioFacade.verificarCorreoExistente(usuario.getCorreo())) {
                usuarioFacade.crearUsuario(usuario);
            }
            Usuario queriedUser = usuarioFacade.consultarUsuario("maria@example.com", "password123");
            assertNotNull(queriedUser);
            assertEquals("maria@example.com", queriedUser.getCorreo());
        } catch (ControllerException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testVerificarCorreoExistente() {
        Municipio municipio = new Municipio();
        municipio.setNombre("Cajeme");
        Estado estado = new Estado();
        estado.setNombre("Sonora");
        municipio.setEstado(estado);
        Usuario usuario = new Usuario("Carlos", "Perez", "Lopez", "carlos@example.com", "password123", "1234567890", "avatar.png", "Pueblo Yaqui", new Date(), "Masculino", TipoUsuario.NORMAL, municipio);
        try {
            if (!usuarioFacade.verificarCorreoExistente(usuario.getCorreo())) {
                usuarioFacade.crearUsuario(usuario);
            }
            boolean exists = usuarioFacade.verificarCorreoExistente("carlos@example.com");
            assertTrue(exists);
        } catch (ControllerException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
}

