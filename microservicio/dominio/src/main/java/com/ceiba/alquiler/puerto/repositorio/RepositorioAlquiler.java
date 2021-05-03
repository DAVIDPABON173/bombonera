package com.ceiba.alquiler.puerto.repositorio;

import com.ceiba.alquiler.modelo.entidad.Alquiler;

import java.time.LocalDate;
import java.time.LocalTime;

public interface RepositorioAlquiler {

    /**
     * Permite crear un alquiler
     *
     * @param alquiler
     * @return el id del alquiler
     */
    Long crear(Alquiler alquiler);


    /**
     * Permite eliminar un alquiler
     *
     * @param id
     */
    void eliminar(Long id);

    /**
     * Permite validar si existe algun alquiler que se pueda cruzar con la nueva solicitud de alquiler
     *
     * @param fechaAlquiler
     * @param horaInicio
     * @param horaFin
     * @return
     */
    Boolean existeAlquilerEnFechaYRangoHoras(LocalDate fechaAlquiler, LocalTime horaInicio, LocalTime horaFin);



}
