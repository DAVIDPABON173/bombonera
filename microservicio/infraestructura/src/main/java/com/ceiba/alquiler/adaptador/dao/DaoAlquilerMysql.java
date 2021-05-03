package com.ceiba.alquiler.adaptador.dao;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import com.ceiba.infraestructura.excepcion.ExcepcionRecursoNoEncontrado;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoAlquilerMysql implements DaoAlquiler {

    private static final String ALQUILER_NO_ENCONTRADO = "Alquiler no encontrado.";

    private final CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate;

    @SqlStatement(namespace = "alquiler", value = "listar")
    private static String sqlListar;

    @SqlStatement(namespace = "alquiler", value = "buscar")
    private static String sqlBuscar;

    public DaoAlquilerMysql(CustomNamedParameterJdbcTemplate customNamedParameterJdbcTemplate) {
        this.customNamedParameterJdbcTemplate = customNamedParameterJdbcTemplate;
    }

    @Override
    public List<DtoAlquiler> listar() {
        return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlListar, new MapeoAlquiler());
    }

    @Override
    public DtoAlquiler buscar(Long id) {
        MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("id", id);
        try {
            return this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().queryForObject(sqlBuscar, paramSource, new MapeoAlquiler());
        } catch (EmptyResultDataAccessException e) {
            throw new ExcepcionRecursoNoEncontrado(ALQUILER_NO_ENCONTRADO, e);
        }
    }


}
