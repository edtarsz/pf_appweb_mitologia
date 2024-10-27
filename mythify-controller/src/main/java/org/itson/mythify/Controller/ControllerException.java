package org.itson.mythify.Controller;

/**
 * Excepción personalizada para errores del modelo.
 *
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class ControllerException extends Exception {

    /**
     * Crea una nueva {@code ModelException} sin mensaje.
     */
    public ControllerException() {
        super();
    }

    /**
     * Crea una {@code ModelException} con el mensaje especificado.
     *
     * @param message Mensaje que describe el error.
     */
    public ControllerException(String message) {
        super(message);
    }

    /**
     * Crea una {@code ModelException} con el mensaje y causa especificados.
     *
     * @param message Mensaje que describe el error.
     * @param cause La causa de la excepción.
     */
    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Crea una {@code ModelException} con la causa especificada.
     *
     * @param cause La causa de la excepción.
     */
    public ControllerException(Throwable cause) {
        super(cause);
    }
}