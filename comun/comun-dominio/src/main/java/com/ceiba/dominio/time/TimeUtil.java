package com.ceiba.dominio.time;

import java.time.LocalTime;

public class TimeUtil {

    public TimeUtil() {
    }

    public static int calcularDiferenciaEntreHoras(LocalTime horaInicio, LocalTime horaFin) {
        return horaFin.getHour() - horaInicio.getHour();
    }
}
