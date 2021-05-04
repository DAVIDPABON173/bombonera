package com.ceiba.alquiler.servicio;

import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.dominio.excepcion.ExcepcionDiaNoLaboral;
import com.ceiba.dominio.excepcion.ExcepcionHorarioNoDisponible;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;

import java.time.LocalDate;

public class ServicioCrearAlquiler {

    private static final String HORARIO_NO_DISPONIBLE = "El horario de alquiler solicitado no se encuentra disponible.";
    private static final String EL_DIA_MIERCOLES_NO_HAY_SERVICIO = "El dia miercoles no hay servicio.";
    private static final String NOT_WORKING_WEDNESDAY = "WEDNESDAY";

    private RepositorioAlquiler repositorioAlquiler;

    public ServicioCrearAlquiler(RepositorioAlquiler repositorioAlquiler) {
        this.repositorioAlquiler = repositorioAlquiler;
    }

    public Long ejecutar(Alquiler alquiler) {
        this.validarSiHayServicioEnFechaAlquiler(alquiler);
        this.validarDisponibilidadHorariaDeAlquiler(alquiler);
        return this.repositorioAlquiler.crear(alquiler);
    }

    private void validarDisponibilidadHorariaDeAlquiler(Alquiler alquiler) {
        Boolean ocupado = this.repositorioAlquiler.existeAlquilerEnFechaYRangoHoras(alquiler.getFechaAlquiler(), alquiler.getHoraInicio(), alquiler.getHoraFin());
        if (ocupado) {
            throw new ExcepcionHorarioNoDisponible(HORARIO_NO_DISPONIBLE);
        }

    }

    private void validarSiHayServicioEnFechaAlquiler(Alquiler alquiler) {
        if (alquiler.getFechaAlquiler().getDayOfWeek().toString().equals(NOT_WORKING_WEDNESDAY)) {
            throw new ExcepcionDiaNoLaboral(EL_DIA_MIERCOLES_NO_HAY_SERVICIO);
        }
    }


}
