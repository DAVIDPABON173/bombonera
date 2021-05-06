package com.ceiba.alquiler.testdatabuilder;

import com.ceiba.alquiler.comando.ComandoAlquiler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ComandoAlquilerTestDataBuilder {

    private Long id;
    private String documento;
    private LocalDateTime fechaSolicitud;
    private LocalDate fechaAlquiler;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double valorPagado;

    public ComandoAlquilerTestDataBuilder() {
        documento = "12345";
        fechaSolicitud = LocalDateTime.of(2021, 04, 30, 6, 00, 00, 0000);
        fechaAlquiler = !LocalDate.now().plusDays(1).getDayOfWeek().toString().equals(DayOfWeek.WEDNESDAY.toString()) ? LocalDate.now().plusDays(1) : LocalDate.now().plusDays(2);
        horaInicio = LocalTime.of(8, 00);
        horaFin = LocalTime.of(10, 00);
    }

    public ComandoAlquilerTestDataBuilder conDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
        return this;
    }

    public ComandoAlquilerTestDataBuilder conId(Long id) {
        this.id = id;
        return this;
    }

    public ComandoAlquiler build() {
        return new ComandoAlquiler(id, documento, fechaSolicitud, fechaAlquiler,
                horaInicio, horaFin, valorPagado);
    }


}
