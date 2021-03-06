package com.ceiba.alquiler.modelo.entidad;

import com.ceiba.dominio.excepcion.ExcepcionDiaNoLaboral;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.time.TimeUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.ceiba.dominio.ValidadorArgumento.validarLongitudMinima;
import static com.ceiba.dominio.ValidadorArgumento.validarObligatorio;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Alquiler {

    private static final String SE_DEBE_INGRESAR_DOCUMENTO_PERSONA = "Se debe ingresar el documento de la persona";
    private static final String SE_DEBE_INGRESAR_DOCUMENTO_CON_MINIMO_CINCO_DIGITOS = "Se debe ingresar documento con minimo cinco digitos.";
    private static final Integer LONGITUD_MINIMA_DOCUMENTO = 5;
    private static final String SE_DEBE_INGRESAR_FECHA_ALQUILER = "Se debe ingresar la fecha de alquiler";
    private static final String SE_DEBE_INGRESAR_FECHA_SOLICITUD = "Se debe ingresar la fecha de solicitud";
    private static final String SE_DEBE_INGRESAR_HORA_INICIO = "Se debe ingresar la hora de inicio del alquiler";
    private static final String SE_DEBE_INGRESAR_HORA_FIN = "Se debe ingresar la hora fin del alquiler";
    private static final String EL_DIA_MIERCOLES_NO_HAY_SERVICIO = "El dia miercoles no hay servicio.";


    private static final String EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS = "El Alquiler solo se realiza por horas exactas sin minutos adicionales. Formato ej: HH:00";
    private static final String HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN = "La hora de inciio no debe ser mayor o igual a la hora fin.";

    private static final Double VALOR_HORA = 90000d;
    private static final Double DESCUENTO_25_PORCIENTO = 0.25;
    private static final Double DESCUENTO_15_PORCIENTO = 0.15;
    private static final int LIMITE_BASE_2_HORAS  = 2;
    private static final int MINUTOS_CERO  = 0;

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
        validarTiempoAlquilerSoloPorHorasEnteras(horaInicio, horaFin);

        setId(id);
        setDocumento(documento);
        setFechaSolicitud(fechaSolicitud);
        setFechaAlquiler(fechaAlquiler);
        setHoraInicio(horaInicio);
        setHoraFin(horaFin);
        setValorPagado(this.calcularValorPagar());
    }

    private double calcularValorPagar() {
        int cantidadHoras = TimeUtil.calcularDiferenciaEntreHoras(this.horaInicio, this.horaFin);
        double valorPagado;
        // los dias martes y jueves se aplica un 25% Descto.
        if (this.fechaAlquiler.getDayOfWeek().toString().equals(DayOfWeek.TUESDAY.toString())
                || this.fechaAlquiler.getDayOfWeek().toString().equals(DayOfWeek.THURSDAY.toString()
        )) {
            valorPagado = (cantidadHoras * VALOR_HORA) - ((cantidadHoras * VALOR_HORA) * DESCUENTO_25_PORCIENTO);
        } else {
            if (cantidadHoras > LIMITE_BASE_2_HORAS) {
                Integer horasExtras = cantidadHoras - LIMITE_BASE_2_HORAS;
                // Las horas extras se les aplicara un descuento del 15%
                valorPagado = (LIMITE_BASE_2_HORAS * VALOR_HORA) + ((horasExtras * VALOR_HORA) - ((horasExtras * VALOR_HORA) * DESCUENTO_15_PORCIENTO));
            } else {
                valorPagado = (cantidadHoras * VALOR_HORA);
            }
        }
        return  valorPagado;
    }

    private void validarRangoHorarioAlquiler(LocalTime horaInicio, LocalTime horaFin) {
        if (horaInicio.getHour() >= horaFin.getHour()) {
            throw new ExcepcionValorInvalido(HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN);
        }
    }

    private void validarTiempoAlquilerSoloPorHorasEnteras(LocalTime horaInicio, LocalTime horaFin){
        if (horaFin.getMinute() > MINUTOS_CERO || horaInicio.getMinute() > MINUTOS_CERO) {
            throw new ExcepcionValorInvalido(EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS);
        }
    }
    private void validarSiHayServicioEnFechaAlquiler(LocalDate fechaAlquiler) {
        if (fechaAlquiler.getDayOfWeek().toString().equals(DayOfWeek.WEDNESDAY.toString())) {
            throw new ExcepcionDiaNoLaboral(EL_DIA_MIERCOLES_NO_HAY_SERVICIO);

        }
    }

}
