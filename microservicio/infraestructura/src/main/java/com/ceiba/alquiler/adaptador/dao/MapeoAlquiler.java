package com.ceiba.alquiler.adaptador.dao;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.infraestructura.jdbc.MapperResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class MapeoAlquiler implements RowMapper<DtoAlquiler>, MapperResult {

    @Override
    public DtoAlquiler mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Long id = resultSet.getLong("id");
        String documento = resultSet.getString("documento");
        LocalDateTime fechaSolicitud = extraerLocalDateTime(resultSet, "fecha_solicitud");
        ;
        LocalDate fechaAlquiler = extraerLocalDate(resultSet, "fecha_alquiler");
        LocalTime horaInicio = extraerLocalTime(resultSet, "hora_inicio");
        LocalTime horaFin = extraerLocalTime(resultSet, "hora_fin");
        Double valorPagado = resultSet.getDouble("valor_pagado");

        return new DtoAlquiler(id, documento, fechaSolicitud, fechaAlquiler, horaInicio, horaFin, valorPagado);
    }
}
