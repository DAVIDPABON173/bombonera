package com.ceiba.alquiler.testdatabuilder;

import com.ceiba.alquiler.modelo.entidad.Alquiler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AlquilerTestDataBuider {

    private Long id;
    private String documento;
    private LocalDateTime fechaSolicitud;
    private LocalDate fechaAlquiler;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double valorPagado;

    public AlquilerTestDataBuider() {

        documento = "12345";
        fechaSolicitud = LocalDateTime.now();
        fechaAlquiler = !LocalDate.now().plusDays(1).getDayOfWeek().toString().equals(DayOfWeek.WEDNESDAY.toString()) ? LocalDate.now().plusDays(1) : LocalDate.now().plusDays(2);
        horaInicio = LocalTime.of(8, 00, 00);
        horaFin = LocalTime.of(10, 00, 00);

    }

    public AlquilerTestDataBuider conDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public AlquilerTestDataBuider conFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public AlquilerTestDataBuider conFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
        return this;
    }

    public AlquilerTestDataBuider conHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public AlquilerTestDataBuider conHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public AlquilerTestDataBuider conValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
        return this;
    }

    public AlquilerTestDataBuider conId(Long id) {
        this.id = id;
        return this;
    }

    public Alquiler build() {
        return new Alquiler(id, documento, fechaSolicitud, fechaAlquiler,
                horaInicio, horaFin, valorPagado);
    }


}
