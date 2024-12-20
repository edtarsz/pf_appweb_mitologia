package org.itson.mythify.dao;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.itson.mythify.conexion.IConexion;
import org.itson.mythify.conexion.ModelException;
import org.itson.mythify.entidad.Usuario;
import org.jasypt.util.password.StrongPasswordEncryptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class UsuarioDAOTest {

    @Mock
    private IConexion mockConexion;

    @Mock
    private EntityManager mockEntityManager;

    @Mock
    private EntityTransaction mockTransaction;

    @Mock
    private CriteriaBuilder mockCriteriaBuilder; // Agrega estos mocks para los objetos de Criteria API

    @Mock
    private CriteriaQuery<Usuario> mockCriteriaQuery;

    @Mock
    private Root<Usuario> mockRoot;

    @InjectMocks
    private UsuarioDAO usuarioDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración del mock de la conexión para devolver un EntityManager simulado
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);

        // Configuración del EntityManager para devolver una transacción simulada
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);

        // Crear instancia del UsuarioDAO con el mock de la conexión
        usuarioDAO = new UsuarioDAO(mockConexion);

    }

    @Test
    public void testCrearUsuario_Exito() throws ModelException {
        // Datos de prueba
        Usuario mockUsuario = new Usuario();
        mockUsuario.setCorreo("test@example.com");
        mockUsuario.setContrasenia("password123");

        // Configuración para la prueba
        doNothing().when(mockEntityManager).persist(any(Usuario.class));
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        // Invocación del método a probar
        Usuario resultado = usuarioDAO.crearUsuario(mockUsuario);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(mockUsuario.getCorreo(), resultado.getCorreo());
        assertNotEquals("password123", resultado.getContrasenia()); // La contraseña debe estar encriptada

        // Verificar que los métodos se llamaron correctamente
        verify(mockEntityManager).persist(mockUsuario);
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    void testCrearUsuario_Fallo() {
        // Configuración del mock para simular la excepción
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(true);
        doThrow(new RuntimeException("Error persistiendo usuario"))
                .when(mockEntityManager).persist(any(Usuario.class));

        // Inicializa el DAO
        UsuarioDAO dao = new UsuarioDAO(mockConexion);

        // Act: Intenta crear un usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setContrasenia("password");

        Usuario resultado = null;
        try {
            resultado = dao.crearUsuario(usuario);
            fail("Debería haber lanzado una excepción");
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioDAOTest.class.getName()).info("Excepción esperada: " + ex.getMessage());
        }

        // Assert
        assertNull(resultado, "El usuario debería ser nulo al fallar la creación");
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback(); // Aquí verificamos que el rollback ocurrió
        verify(mockEntityManager).persist(any(Usuario.class));
    }

    @Test
    void testCrearUsuario_FalloSinTransaccionActiva() {
        // Configuración del mock para simular la excepción y una transacción inactiva
        when(mockConexion.crearConexion()).thenReturn(mockEntityManager);
        when(mockEntityManager.getTransaction()).thenReturn(mockTransaction);
        when(mockTransaction.isActive()).thenReturn(false); // Simular transacción inactiva
        doThrow(new RuntimeException("Error persistiendo usuario"))
                .when(mockEntityManager).persist(any(Usuario.class));

        // Inicializa el DAO
        UsuarioDAO dao = new UsuarioDAO(mockConexion);

        // Act: Intenta crear un usuario
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setContrasenia("password");

        Usuario resultado = null;
        try {
            resultado = dao.crearUsuario(usuario);
            fail("Debería haber lanzado una excepción");
        } catch (ModelException ex) {
            Logger.getLogger(UsuarioDAOTest.class.getName()).info("Excepción esperada: " + ex.getMessage());
        }

        // Assert
        assertNull(resultado, "El usuario debería ser nulo al fallar la creación");
        verify(mockTransaction).begin();
        verify(mockTransaction, never()).rollback(); // Asegurarse de que no se llama a rollback
        verify(mockEntityManager).persist(any(Usuario.class));
    }

    @Test
    public void testConsultarUsuario_Exito() throws ModelException {
        // Datos de prueba
        String correo = "test@example.com";
        String password = "password123";
        // Agrega este mock
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String contraseniaEncriptada = passwordEncryptor.encryptPassword(password);
        // Crea un usuario mock
        Usuario mockUsuario = new Usuario();
        mockUsuario.setCorreo(correo);
        mockUsuario.setContrasenia(contraseniaEncriptada); // Encripta la contraseña

        // Mock del EntityManager
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Usuario.class)).thenReturn(mockCriteriaQuery);

        // Mock del Root y CriteriaQuery
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);  // Aseguramos que select() devuelve criteriaQuery

        // Mock del Predicate para el where
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockRoot.get("correo"), correo)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);  // Aseguramos que where() devuelve criteriaQuery

        // Configuramos el mock de la consulta
        TypedQuery<Usuario> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockQuery);
        when(mockEntityManager.createQuery(mockCriteriaQuery).getSingleResult()).thenReturn(mockUsuario); // El mock devuelve el usuario

        // Invoca el método a probar
        Usuario resultado = usuarioDAO.consultarUsuario(correo, password);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(correo, resultado.getCorreo());
        assertEquals(contraseniaEncriptada, resultado.getContrasenia()); // Asegúrate que la contraseña encriptada es correcta
        assertNotNull(resultado.getContrasenia()); // Asegúrate que la contraseña no es null

    }

    @Test
    public void testConsultarUsuario_Fallo() throws ModelException {
        // Datos de prueba
        String correo = "test@example.com";
        String password = "password123";

        // Mock del EntityManager
        TypedQuery<Usuario> mockQuery = mock(TypedQuery.class); // Mock de TypedQuery
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Usuario.class)).thenReturn(mockCriteriaQuery);

        // Mock del Root y CriteriaQuery
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);

        // Mock del Predicate para el where
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockRoot.get("correo"), correo)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Hacer que createQuery devuelva el mock del TypedQuery
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockQuery);

        // Simular NoResultException para el test de "Usuario no encontrado"
        when(mockQuery.getSingleResult()).thenThrow(new NoResultException("No user found"));

        // Invoca el método a probar y espera que se lance la ModelException
        try {
            usuarioDAO.consultarUsuario(correo, password);
            fail("Expected ModelException to be thrown");
        } catch (ModelException e) {
            // Verificar que la ModelException contiene el mensaje adecuado
            assertEquals("User not found: " + correo, e.getMessage());
        }

    }

    @Test
    public void testConsultarUsuario_ContrasenaIncorrecta() throws ModelException {
        // Datos de prueba
        String correo = "test@example.com";
        String passwordIncorrecta = "wrongpassword";
        String passwordCorrecta = "correctpassword";

        // Mock del encryptor para verificar contraseñas
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String contraseniaEncriptada = passwordEncryptor.encryptPassword(passwordCorrecta);

        // Mock de un usuario con correo válido pero contraseña encriptada
        Usuario mockUsuario = new Usuario();
        mockUsuario.setCorreo(correo);
        mockUsuario.setContrasenia(contraseniaEncriptada);

        // Configuración del mock para la consulta
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        when(mockCriteriaBuilder.createQuery(Usuario.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);
        when(mockCriteriaQuery.select(mockRoot)).thenReturn(mockCriteriaQuery);

        // Mock del Predicate para el where
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockRoot.get("correo"), correo)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Configuramos el mock de la consulta
        TypedQuery<Usuario> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(mockUsuario);

        assertThrows(ModelException.class, () -> {
            usuarioDAO.consultarUsuario(correo, passwordIncorrecta);
        });
        verify(mockEntityManager).getCriteriaBuilder(); // Asegurarse de que se consultó el usuario

    }

    @Test
    public void testConsultarUsuario_ErrorInesperado() {
        // Datos de prueba
        String correo = "test@example.com";
        String password = "password123";

        // Configuración del mock para provocar una excepción
        when(mockEntityManager.getCriteriaBuilder()).thenThrow(new RuntimeException("Unexpected error"));

        // Invoca el método a probar y verifica que lanza una ModelException
        try {
            usuarioDAO.consultarUsuario(correo, password);
            fail("Expected ModelException to be thrown");
        } catch (ModelException e) {
            // Verificar que la ModelException contiene el mensaje adecuado
            assertTrue(e.getMessage().contains("Error querying user"));
            assertTrue(e.getMessage().contains("Unexpected error"));
            Logger.getLogger(UsuarioDAOTest.class.getName()).info("Excepción inesperada correctamente manejada.");
        }

        // Verificación de que se intentó construir la consulta antes del fallo
        verify(mockEntityManager).getCriteriaBuilder();
    }

    @Test
    public void testVerificarCorreoExistente_Exito() throws ModelException {
        // Datos de prueba
        String correo = "test@example.com";
        Long count = 1L; // Simulamos que hay un usuario con el correo en la base de datos

        // Mock del EntityManager y Criteria API
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        CriteriaQuery<Long> mockCriteriaQuery = mock(CriteriaQuery.class); // Crear mock para CriteriaQuery<Long>
        Root<Usuario> mockRoot = mock(Root.class);

        when(mockCriteriaBuilder.createQuery(Long.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);

        // Mock del Predicate y count
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockRoot.get("correo"), correo)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockCriteriaBuilder.count(mockRoot))).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Mock de TypedQuery y el resultado del conteo
        TypedQuery<Long> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(count);

        // Invoca el método a probar
        boolean resultado = usuarioDAO.verificarCorreoExistente(correo);

        // Verificaciones
        assertTrue(resultado, "El correo debería existir en la base de datos");

        // Verificar interacciones con los mocks
        verify(mockEntityManager).getCriteriaBuilder();
        verify(mockCriteriaBuilder).createQuery(Long.class);
        verify(mockCriteriaQuery).from(Usuario.class);
        verify(mockCriteriaBuilder).equal(mockRoot.get("correo"), correo);
        verify(mockCriteriaQuery).where(mockPredicate);
        verify(mockCriteriaQuery).select(mockCriteriaBuilder.count(mockRoot));
        verify(mockEntityManager).createQuery(mockCriteriaQuery);
        verify(mockQuery).getSingleResult();
    }

    @Test
    public void testVerificarCorreoExistente_Fallo() throws ModelException {
        // Datos de prueba
        String correo = "nonexistent@example.com";
        Long count = 0L; // Simulamos que no hay usuarios con este correo

        // Mock del EntityManager y Criteria API
        when(mockEntityManager.getCriteriaBuilder()).thenReturn(mockCriteriaBuilder);
        CriteriaQuery<Long> mockCriteriaQuery = mock(CriteriaQuery.class); // Crear mock para CriteriaQuery<Long>
        Root<Usuario> mockRoot = mock(Root.class);

        when(mockCriteriaBuilder.createQuery(Long.class)).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.from(Usuario.class)).thenReturn(mockRoot);

        // Mock del Predicate y count
        Predicate mockPredicate = mock(Predicate.class);
        when(mockCriteriaBuilder.equal(mockRoot.get("correo"), correo)).thenReturn(mockPredicate);
        when(mockCriteriaQuery.select(mockCriteriaBuilder.count(mockRoot))).thenReturn(mockCriteriaQuery);
        when(mockCriteriaQuery.where(mockPredicate)).thenReturn(mockCriteriaQuery);

        // Mock de TypedQuery y el resultado del conteo
        TypedQuery<Long> mockQuery = mock(TypedQuery.class);
        when(mockEntityManager.createQuery(mockCriteriaQuery)).thenReturn(mockQuery);
        when(mockQuery.getSingleResult()).thenReturn(count);

        // Invoca el método a probar
        boolean resultado = usuarioDAO.verificarCorreoExistente(correo);

        // Verificaciones
        assertFalse(resultado, "El correo no debería existir en la base de datos");

        // Verificar interacciones con los mocks
        verify(mockEntityManager).getCriteriaBuilder();
        verify(mockCriteriaBuilder).createQuery(Long.class);
        verify(mockCriteriaQuery).from(Usuario.class);
        verify(mockCriteriaBuilder).equal(mockRoot.get("correo"), correo);
        verify(mockCriteriaQuery).where(mockPredicate);
        verify(mockCriteriaQuery).select(mockCriteriaBuilder.count(mockRoot));
        verify(mockEntityManager).createQuery(mockCriteriaQuery);
        verify(mockQuery).getSingleResult();
    }

    @Test
    public void testVerificarCorreoExistente_ErrorInesperado() {
        // Datos de prueba
        String correo = "error@example.com";

        // Configuración del mock para provocar una excepción
        when(mockEntityManager.getCriteriaBuilder()).thenThrow(new RuntimeException("Unexpected error"));

        // Invoca el método a probar y verifica que lanza una ModelException
        try {
            usuarioDAO.verificarCorreoExistente(correo);
            fail("Expected ModelException to be thrown");
        } catch (ModelException e) {
            // Verificar que la ModelException contiene el mensaje adecuado
            assertTrue(e.getMessage().contains("Error verifying email"));
            assertTrue(e.getMessage().contains("Unexpected error"));
            Logger.getLogger(UsuarioDAOTest.class.getName()).info("Excepción inesperada correctamente manejada.");
        }

        // Verificar que se intentó obtener el CriteriaBuilder antes del fallo
        verify(mockEntityManager).getCriteriaBuilder();
    }

    @Test
    public void testActualizarUsuario_Exitoso() throws ModelException {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(50);
        usuarioExistente.setCorreo("test@gmail.com");
        usuarioExistente.setContrasenia("Password123");

        Usuario usuarioActualizacion = new Usuario();
        usuarioActualizacion.setIdUsuario(50);
        usuarioActualizacion.setCorreo("nuevoCorreo@gmail.com");
        usuarioActualizacion.setContrasenia("NewPassword123");

        when(mockEntityManager.find(Usuario.class, usuarioExistente.getIdUsuario()))
                .thenReturn(usuarioExistente);
        when(mockTransaction.isActive()).thenReturn(true);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).commit();

        boolean resultado = usuarioDAO.actualizarUsuario(usuarioActualizacion);

        assertTrue(resultado);
        verify(mockEntityManager).merge(any(Usuario.class));
        verify(mockTransaction).begin();
        verify(mockTransaction).commit();
    }

    @Test
    public void testActualizarUsuario_FalloUsuarioNoEncontrado() throws ModelException {
        Usuario usuarioActualizacion = new Usuario();
        usuarioActualizacion.setIdUsuario(50);
        usuarioActualizacion.setCorreo("nuevoCorreo@gmail.com");
        usuarioActualizacion.setContrasenia("NewPassword123");

        when(mockEntityManager.find(Usuario.class, usuarioActualizacion.getIdUsuario()))
                .thenReturn(null);
        when(mockTransaction.isActive()).thenReturn(true);
        doNothing().when(mockTransaction).begin();
        doNothing().when(mockTransaction).rollback();

        boolean resultado = usuarioDAO.actualizarUsuario(usuarioActualizacion);

        assertFalse(resultado);
        verify(mockEntityManager, never()).merge(any(Usuario.class));
        verify(mockTransaction).begin();
        verify(mockTransaction).rollback();
    }
}
