/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.itson.mythify.auxiliar;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author user
 */
public class CalcularTiempo {

    public static String tiempoTranscurridoDesde(Date fecha) {
        long diffMillis = new Date().getTime() - fecha.getTime();
        long diffHoras = TimeUnit.MILLISECONDS.toHours(diffMillis);
        long diffMinutos = TimeUnit.MILLISECONDS.toMinutes(diffMillis) % 60;
        long diffDias = TimeUnit.MILLISECONDS.toDays(diffMillis);

        if (diffDias > 0) {
            return "Hace " + diffDias + " día" + (diffDias == 1 ? "" : "(s)");
        } else if (diffHoras > 0) {
            return "Hace " + diffHoras + " hora" + (diffHoras == 1 ? "" : "(s)");
        } else if (diffMinutos > 0) {
            return "Hace " + diffMinutos + " minuto" + (diffMinutos == 1 ? "" : "(s)");
        } else {
            return "Hace un momento";
        }
    }

}
