package com.ceiba.alquiler.servicio;

import com.ceiba.BasePrueba;
import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.alquiler.servicio.testdatabuilder.AlquilerTestDataBuider;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicioCrearAlquilerTest {

    private static final String SE_DEBE_INGRESAR_DOCUMENTO_PERSONA = "Se debe ingresar el documento de la persona";
    private static final String SE_DEBE_INGRESAR_FECHA_ALQUILER = "Se debe ingresar la fecha de alquiler";
    private static final String SE_DEBE_INGRESAR_HORA_INICIO = "Se debe ingresar la hora de inicio del alquiler";
    private static final String SE_DEBE_INGRESAR_HORA_FIN = "Se debe ingresar la hora de inicio del alquiler";

    private static final String EL_DIA_MIERCOLES_NO_HAY_SERVICIO = "El dia miercoles no hay servicio.";
    private static final String EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS = "Se debe ingresar un rango de horas validas.";
    private static final String HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN = "La hora de inciio no debe ser mayor o igual a la hora fin.";
    private static final String FECHA_ALQUILER_NO_DEBE_SER_MENOR_A_LA_FECHA_ACTUAL = "La fecha de alquiler, no debe ser menor a la fecha actual.";

    private static final String HORARIO_NO_DISPONIBLE = "El horario de alquiler solicitado no se encuentra disponible.";

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioDocumento() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conDocumento(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_DOCUMENTO_PERSONA);
    }

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioFechaAlquiler() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaAlquiler(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_FECHA_ALQUILER);
    }

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioHoraInicio() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conHoraInicio(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_HORA_INICIO);
    }

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioHoraFin() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conHoraFin(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_HORA_FIN);
    }

    @Test
    public void deberiaLanzarExcepcionporPorFechaAlquilerEnDiaNoLabolal() {
        // arrange
        LocalDate fechaAlquiler = LocalDate.of(2021, 04, 28);
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaAlquiler(fechaAlquiler);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorInvalido.class, EL_DIA_MIERCOLES_NO_HAY_SERVICIO);
    }

    @Test
    public void deberiaLanzarExcepcionporPorValorInvalidoDeRangoHorasAlquiler() {
        // arrange
        LocalTime horaInicio = LocalTime.of(7, 00);
        LocalTime horaFin = LocalTime.of(7, 00);
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorInvalido.class, HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN);
    }

    @Test
    public void deberiaLanzarExcepcionporPorValorInvalidoRangoHorasConMinutosAlquiler() {
        // arrange
        LocalTime horaInicio = LocalTime.of(7, 30);
        LocalTime horaFin = LocalTime.of(9, 00);
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorInvalido.class, EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS);
    }


    @Test
    public void deberiaLanzarExcepcionporPorFechaAlquilerSuperaFechaActualDeSolictud() {
        // arrange
        LocalDateTime fechaSolicitud = LocalDateTime.of(2021, 04, 30, 00, 00, 00);
        LocalDate fechaAlquiler = LocalDate.of(2021, 04, 29);
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorInvalido.class, FECHA_ALQUILER_NO_DEBE_SER_MENOR_A_LA_FECHA_ACTUAL);
    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerAplicando25PorcientoDescto() {
        // arrange
        LocalDateTime fechaSolicitud = LocalDateTime.of(2021, 04, 30, 00, 00, 00);
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 04);
        Double valorPagadoDosHorasAplicando25PorcientoDescuentoTest = 135000d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler);
        // act - assert
        assertEquals(valorPagadoDosHorasAplicando25PorcientoDescuentoTest, alquilerTestDataBuider.build().getValorPagado());

    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerSinAplicarDescuento() {
        // arrange
        Double valorPagadoDosHorasSinDescuentoTest = 180000d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider();
        // act - assert
        assertEquals(valorPagadoDosHorasSinDescuentoTest, alquilerTestDataBuider.build().getValorPagado());
    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerAplicandoDescuentoDe15PorcientoSoloAlExcedenteDeDosHorasAlquiler() {
        // arrange
        LocalTime horaInicio = LocalTime.of(7, 00);
        LocalTime horaFin = LocalTime.of(10, 00);
        Double valorPagadoDosHorasSinDescuentoYUnaConDescuento15porcientoTest = 256500d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        assertEquals(valorPagadoDosHorasSinDescuentoYUnaConDescuento15porcientoTest, alquilerTestDataBuider.build().getValorPagado());
    }

    @Test
    public void validarNoDisponibilidadHorariaDeAlquiler() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider();
        RepositorioAlquiler repositorioAlquiler = Mockito.mock(RepositorioAlquiler.class);
        Alquiler alquilerTest = alquilerTestDataBuider.build();
        Mockito.when(repositorioAlquiler.existeAlquilerEnFechaYRangoHoras(alquilerTest.getFechaAlquiler(), alquilerTest.getHoraInicio(), alquilerTest.getHoraFin())).thenReturn(true);
        ServicioCrearAlquiler servicioCrearAlquiler = new ServicioCrearAlquiler(repositorioAlquiler);
        // act - assert
        BasePrueba.assertThrows(() -> servicioCrearAlquiler.ejecutar(alquilerTest), ExcepcionValorInvalido.class, HORARIO_NO_DISPONIBLE);
    }


}
