package com.ceiba.alquiler.modelo.entidad;

import com.ceiba.BasePrueba;
import com.ceiba.alquiler.testdatabuilder.AlquilerTestDataBuider;
import com.ceiba.dominio.excepcion.ExcepcionDiaNoLaboral;
import com.ceiba.dominio.excepcion.ExcepcionLongitudValor;
import com.ceiba.dominio.excepcion.ExcepcionValorInvalido;
import com.ceiba.dominio.excepcion.ExcepcionValorObligatorio;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlquilerTest {

    private static final String SE_DEBE_INGRESAR_DOCUMENTO_PERSONA = "Se debe ingresar el documento de la persona";
    private static final String SE_DEBE_INGRESAR_DOCUMENTO_CON_MINIMO_CINCO_DIGITOS = "Se debe ingresar documento con minimo cinco digitos.";
    private static final String SE_DEBE_INGRESAR_FECHA_ALQUILER = "Se debe ingresar la fecha de alquiler";
    private static final String SE_DEBE_INGRESAR_FECHA_SOLICITUD = "Se debe ingresar la fecha de solicitud";
    private static final String SE_DEBE_INGRESAR_HORA_INICIO = "Se debe ingresar la hora de inicio del alquiler";
    private static final String SE_DEBE_INGRESAR_HORA_FIN = "Se debe ingresar la hora fin del alquiler";

    private static final String EL_DIA_MIERCOLES_NO_HAY_SERVICIO = "El dia miercoles no hay servicio.";
    private static final String EL_ALQUILER_SE_REALIZA_POR_RANGO_HORAS_EXACTAS = "El Alquiler solo se realiza por horas exactas sin minutos adicionales. Formato ej: HH:00";
    private static final String HORA_INICIO_NO_DEBE_SER_MAYOR_O_IGUAL_A_HORA_FIN = "La hora de inciio no debe ser mayor o igual a la hora fin.";

    @Test
    public void deberiaLanzarExcepcionPorCampoObligatorioDocumento() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conDocumento(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_DOCUMENTO_PERSONA);
    }

    @Test
    public void deberiaLanzarExcepcionPorLongitudMinimaDocumento() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conDocumento("1234");
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionLongitudValor.class, SE_DEBE_INGRESAR_DOCUMENTO_CON_MINIMO_CINCO_DIGITOS);
    }

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioFechaAlquiler() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaAlquiler(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_FECHA_ALQUILER);
    }

    @Test
    public void deberiaLanzarExcepcionporCampoObligatorioFechaSolicitud() {
        // arrange
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaSolicitud(null);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionValorObligatorio.class, SE_DEBE_INGRESAR_FECHA_SOLICITUD);
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
        LocalDate fechaAlquiler = LocalDate.of(2021, 04, 28); // dia miercoles
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaAlquiler(fechaAlquiler);
        // act - assert
        BasePrueba.assertThrows(() -> alquilerTestDataBuider.build(), ExcepcionDiaNoLaboral.class, EL_DIA_MIERCOLES_NO_HAY_SERVICIO);
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
    public void deberiaCalcularValorPagadoPorHorasAlquilerAplicando25PorcientoDesctoMartes() {
        // arrange
        LocalDateTime fechaSolicitud = LocalDateTime.of(2021, 04, 30, 00, 00, 00);
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 04); // dia martes
        Double valorPagadoDosHorasAplicando25PorcientoDescuentoTest = 135000d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler);
        // act - assert
        assertEquals(valorPagadoDosHorasAplicando25PorcientoDescuentoTest, alquilerTestDataBuider.build().getValorPagado());

    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerAplicando25PorcientoDesctoJueves() {
        // arrange
        LocalDateTime fechaSolicitud = LocalDateTime.of(2021, 04, 30, 00, 00, 00);
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 06); // dia Jueves
        LocalTime horaInicio = LocalTime.of(10, 00);
        LocalTime horaFin = LocalTime.of(14, 00);
        Double valorPagadoDosHorasAplicando25PorcientoDescuentoTest = 270000d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider()
                .conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler)
                .conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        assertEquals(valorPagadoDosHorasAplicando25PorcientoDescuentoTest, alquilerTestDataBuider.build().getValorPagado());

    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerSinAplicarDescuento() {
        // arrange
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 03); // dia lunes
        LocalTime horaInicio = LocalTime.of(10, 00);
        LocalTime horaFin = LocalTime.of(12, 00);
        Double valorPagadoDosHorasSinDescuentoTest = 180000d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider()
                .conFechaAlquiler(fechaAlquiler).conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        assertEquals(valorPagadoDosHorasSinDescuentoTest, alquilerTestDataBuider.build().getValorPagado());
    }

    @Test
    public void deberiaCalcularValorPagadoPorHorasAlquilerAplicandoDescuentoDe15PorcientoSoloAlExcedenteDeDosHorasAlquiler() {
        // arrange
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 03); // dia lunes
        LocalTime horaInicio = LocalTime.of(7, 00);
        LocalTime horaFin = LocalTime.of(10, 00);
        Double valorPagadoDosHorasSinDescuentoYUnaConDescuento15porcientoTest = 256500d;
        AlquilerTestDataBuider alquilerTestDataBuider = new AlquilerTestDataBuider().conFechaAlquiler(fechaAlquiler).conHoraInicio(horaInicio).conHoraFin(horaFin);
        // act - assert
        assertEquals(valorPagadoDosHorasSinDescuentoYUnaConDescuento15porcientoTest, alquilerTestDataBuider.build().getValorPagado());
    }
}
