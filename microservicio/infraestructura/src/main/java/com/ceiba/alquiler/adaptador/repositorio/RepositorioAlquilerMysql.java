package com.ceiba.alquiler.adaptador.repositorio;

import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public class RepositorioAlquilerMysql implements RepositorioAlquiler {

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace = "alquiler", value = "crear")
    private static String sqlCrear;

    @SqlStatement(namespace = "alquiler", value = "eliminar")
    private static String sqlEliminar;

    @SqlStatement(namespace = "alquiler", value = "buscar")
    private static String sqlBuscar;

    @SqlStatement(namespace = "alquiler", value = "existEnFechaYRangoHorario")
    private static String sqlExistEnFechaYRangoHorario;

    @SqlStatement(namespace = "alquiler", value = "esHoyFechaDeSolicitudYLaFechaAlquilerNoEsHoy")
    private static String sqlEsHoyFechaDeSolicitudYLaFechaAlquilerNoEsHoy;

    public RepositorioAlquilerMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public Long crear(Alquiler alquiler) {
        return this.customNamedParameterJdbcTemplate.crear(alquiler, sqlCrear);
    }

    @Override
    public void eliminar(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().update(sqlEliminar, paramSource);
    }

    @Override
    public Boolean existeAlquilerEnFechaYRangoHoras(LocalDate fechaAlquiler, LocalTime horaInicio, LocalTime horaFin) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("fecha_alquiler", fechaAlquiler);
        paramSource.addValue("hora_inicio", horaInicio);
        paramSource.addValue("hora_fin", horaFin);
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlExistEnFechaYRangoHorario, paramSource, Boolean.class);
    }

    @Override
    public boolean esHoyFechaDeSolicitudYLaFechaAlquilerNoEsHoy(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlEsHoyFechaDeSolicitudYLaFechaAlquilerNoEsHoy, paramSource, Boolean.class);
    }
}
