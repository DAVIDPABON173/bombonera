package com.ceiba.alquiler.adaptador.dao;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import com.ceiba.infraestructura.jdbc.CustomNamedParameterJdbcTemplate;
import com.ceiba.infraestructura.jdbc.sqlstatement.SqlStatement;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoAlquilerMysql implements DaoAlquiler {

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
        List<DtoAlquiler> list = this.customNamedParameterJdbcTemplate.getNamedParameterJdbcTemplate().query(sqlBuscar, paramSource, new MapeoAlquiler());
        return !list.isEmpty() ?  list.get(0) : null;
    }


}
