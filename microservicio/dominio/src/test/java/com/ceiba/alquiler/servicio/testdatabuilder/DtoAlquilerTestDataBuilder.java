package com.ceiba.alquiler.servicio.testdatabuilder;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DtoAlquilerTestDataBuilder {

    private Long id;
    private String documento;
    private LocalDateTime fechaSolicitud;
    private LocalDate fechaAlquiler;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double valorPagado;

    public DtoAlquilerTestDataBuilder() {

        documento = "1090123";
        fechaSolicitud = LocalDateTime.now();
        fechaAlquiler = LocalDate.now();
        horaInicio = LocalTime.of(8, 00);
        horaFin = LocalTime.of(10, 00);

    }

    public DtoAlquilerTestDataBuilder conDocumento(String documento) {
        this.documento = documento;
        return this;
    }

    public DtoAlquilerTestDataBuilder conFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
        return this;
    }

    public DtoAlquilerTestDataBuilder conFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
        return this;
    }

    public DtoAlquilerTestDataBuilder conHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
        return this;
    }

    public DtoAlquilerTestDataBuilder conHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
        return this;
    }

    public DtoAlquilerTestDataBuilder conValorPagado(Double valorPagado) {
        this.valorPagado = valorPagado;
        return this;
    }

    public DtoAlquilerTestDataBuilder conId(Long id) {
        this.id = id;
        return this;
    }

    public DtoAlquiler build() {
        return new DtoAlquiler(id, documento, fechaSolicitud, fechaAlquiler,
                horaInicio, horaFin, valorPagado);
    }
}
