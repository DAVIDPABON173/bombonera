package com.ceiba.alquiler.servicio;

import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;

public class ServicioCrearAlquiler {

    private static final String HORARIO_NO_DISPONIBLE = "El horario de alquiler solicitado no se encuentra disponible.";

    private RepositorioAlquiler repositorioAlquiler;

    public ServicioCrearAlquiler(RepositorioAlquiler repositorioAlquiler) {
        this.repositorioAlquiler = repositorioAlquiler;
    }

    public Long ejecutar(Alquiler alquiler) {
        validarDisponibilidadHorariaDeAlquiler(alquiler);
        return this.repositorioAlquiler.crear(alquiler);
    }

    private void validarDisponibilidadHorariaDeAlquiler(Alquiler alquiler) {
        Boolean ocupado = this.repositorioAlquiler.existeAlquilerEnFechaYRangoHoras(alquiler.getFechaAlquiler(), alquiler.getHoraInicio(), alquiler.getHoraFin());
        if (ocupado) {
            throw new ExcepcionValorInvalido(HORARIO_NO_DISPONIBLE);
        }

    }


}
