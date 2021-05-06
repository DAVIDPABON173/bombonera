package com.ceiba.alquiler.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.alquiler.testdatabuilder.AlquilerTestDataBuider;
import com.ceiba.dominio.excepcion.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicioCrearAlquilerTest {

    private static final String HORARIO_NO_DISPONIBLE = "El horario de alquiler solicitado no se encuentra disponible.";

    @Test
    public void deberiaLanzarUnaExcepcionPorHorarioAlquilerNoDisponible() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider();
        RepositorioAlquiler repositorioAlquiler = Mockito.mock(RepositorioAlquiler.class);
        Alquiler alquilerTest = alquilerTestDataBuider.build();
        Mockito.when(repositorioAlquiler.existeAlquilerEnFechaYRangoHoras(alquilerTest.getFechaAlquiler(), alquilerTest.getHoraInicio(), alquilerTest.getHoraFin())).thenReturn(true);
        ServicioCrearAlquiler servicioCrearAlquiler = new ServicioCrearAlquiler(repositorioAlquiler);
        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearAlquiler.ejecutar(alquilerTest), ExcepcionHorarioNoDisponible.class, HORARIO_NO_DISPONIBLE);
    }




}
