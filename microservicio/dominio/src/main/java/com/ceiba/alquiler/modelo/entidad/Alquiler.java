package com.ceiba.alquiler.modelo.entidad;

import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.ceiba.dominio.ValidadorArgumento.validarLongitudMinima;
import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
public class Alquiler {

    private static final String SE_DEBE_INGRESAR_DOCUMENTO_PERSONA = "Se debe ingresar el documento de la persona";
    private static final String SE_DEBE_INGRESAR_DOCUMENTO_CON_MINIMO_CINCO_DIGITOS = "Se debe ingresar documento con minimo cinco digitos.";
    private static final Integer LONGITUD_MINIMA_DOCUMENTO = 5;
    private static final String SE_DEBE_INGRESAR_FECHA_ALQUILER = "Se debe ingresar la fecha de alquiler";
    private static final String SE_DEBE_INGRESAR_FECHA_SOLICITUD = "Se debe ingresar la fecha de solicitud";
    private static final String SE_DEBE_INGRESAR_HORA_INICIO = "Se debe ingresar la hora de inicio del alquiler";
    private static final String SE_DEBE_INGRESAR_HORA_FIN = "Se debe ingresar la hora de inicio del alquiler";

    private static final String EL_DIA_MIERCOLES_NO_HAY_SERVICIO = "El dia miercoles no hay servicio.";
    private static final String EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS = "Se debe ingresar un rango de horas validas.";
    private static final String HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN = "La hora de inciio no debe ser mayor o igual a la hora fin.";

    private static final String NOT_WORKING_WEDNESDAY = "WEDNESDAY";

    private static final String TUESDAY = "TUESDAY";
    private static final String THURSDAY = "THURSDAY";

    private static final Double VALOR_HORA = 90000d;

    private Long id;
    private String documento;
    private LocalDateTime fechaSolicitud;
    private LocalDate fechaAlquiler;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double valorPagado;

    public Alquiler(Long id, String documento, LocalDateTime fechaSolicitud, LocalDate fechaAlquiler,
                    LocalTime horaInicio, LocalTime horaFin, Double valorPagado) {

        validarObligatorio(documento, SE_DEBE_INGRESAR_DOCUMENTO_PERSONA);
        validarLongitudMinima(documento, LONGITUD_MINIMA_DOCUMENTO,SE_DEBE_INGRESAR_DOCUMENTO_CON_MINIMO_CINCO_DIGITOS);
        validarObligatorio(fechaAlquiler, SE_DEBE_INGRESAR_FECHA_ALQUILER);
        validarObligatorio(fechaSolicitud, SE_DEBE_INGRESAR_FECHA_SOLICITUD);
        validarObligatorio(horaInicio, SE_DEBE_INGRESAR_HORA_INICIO);
        validarObligatorio(horaFin, SE_DEBE_INGRESAR_HORA_FIN);
        validarSiHayServicioEnFechaAlquiler(fechaAlquiler);
        validarRangoHorarioAlquiler(horaInicio, horaFin);

        this.id = id;
        this.documento = documento;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAlquiler = fechaAlquiler;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.valorPagado = this.calcularValorPagar(fechaAlquiler, horaInicio, horaFin);
    }

    private Double calcularValorPagar(LocalDate fechaAlquiler, LocalTime horaInicio, LocalTime horaFin) {
        Integer cantidadHoras = this.getCantidadHoras(horaInicio, horaFin);
        Double valorPagado = 0d;
        // los dias martes y jueves se aplica un 25% Descto.
        if (fechaAlquiler.getDayOfWeek().toString().equals(TUESDAY) || fechaAlquiler.getDayOfWeek().toString().equals(THURSDAY)) {
            valorPagado = (cantidadHoras * VALOR_HORA) - ((cantidadHoras * VALOR_HORA) * 0.25);
        } else {
            if (cantidadHoras > 2) {
                Integer horasExtras = cantidadHoras - 2;
                // Las horas extras se les aplicara un descuento del 15%
                valorPagado = (2 * VALOR_HORA) + ((horasExtras * VALOR_HORA) - ((horasExtras * VALOR_HORA) * 0.15));
            } else {
                valorPagado = (cantidadHoras * VALOR_HORA);
            }
        }
        return  valorPagado;
    }

    private Integer getCantidadHoras(LocalTime horaInicio, LocalTime horaFin) {
        return horaFin.getHour() - horaInicio.getHour();
    }

    private void validarSiHayServicioEnFechaAlquiler(LocalDate fechaAlquiler) {
        if (fechaAlquiler.getDayOfWeek().toString() == NOT_WORKING_WEDNESDAY) {
            throw new ExcepcionValorInvalido(EL_DIA_MIERCOLES_NO_HAY_SERVICIO);
        }
    }

    private void validarRangoHorarioAlquiler(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio.getHour() >= horaFin.getHour()) {
            throw new ExcepcionValorInvalido(HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN);
        }

        if (horaFin.getMinute() > 0 || horaInicio.getMinute() > 0
                || horaFin.getSecond() > 0 || horaInicio.getSecond() > 0) {
            throw new ExcepcionValorInvalido(EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS);
        }
    }
}
