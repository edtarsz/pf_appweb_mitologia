package org.itson.mythify.auxiliar;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author Eduardo Talavera Ramos
 * @author Ana Cristina Castro Noriega
 * @author Eliana Monge Camara
 * @author Jesús Roberto García Armenta
 */
public class CalcularTiempo {

    public static String tiempoTranscurridoDesde(LocalDateTime fecha) {
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(fecha, ahora);

        long diffDias = duracion.toDays();
        long diffHoras = duracion.toHours() % 24;
        long diffMinutos = duracion.toMinutes() % 60;

        if (diffDias > 0) {
            return "Hace " + diffDias + " día" + (diffDias == 1 ? "" : "s");
        } else if (diffHoras > 0) {
            return "Hace " + diffHoras + " hora" + (diffHoras == 1 ? "" : "s");
        } else if (diffMinutos > 0) {
            return "Hace " + diffMinutos + " minuto" + (diffMinutos == 1 ? "" : "s");
        } else {
            return "Hace un momento";
        }
    }
}
