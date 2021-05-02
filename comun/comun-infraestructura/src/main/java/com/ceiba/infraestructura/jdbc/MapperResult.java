package com.ceiba.infraestructura.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface MapperResult {

	default LocalDate extraerLocalDate(ResultSet resultSet, String label) throws SQLException {
        Timestamp fecha = resultSet.getTimestamp(label);
        LocalDate resultado = null;
        if (!resultSet.wasNull()) {
            resultado = fecha.toLocalDateTime().toLocalDate();
        }
        return resultado;
    } 
	
	default LocalDateTime extraerLocalDateTime(ResultSet resultSet, String label) throws SQLException {
        Timestamp fecha = resultSet.getTimestamp(label);
        LocalDateTime resultado = null;
        if (!resultSet.wasNull()) {
            resultado = fecha.toLocalDateTime();
        }
        return resultado;
    }

    /**
     * Nuevo
     * @param resultSet
     * @param label
     * @return
     * @throws SQLException
     */
    default LocalTime extraerLocalTime(ResultSet resultSet, String label) throws SQLException {
        Time tiempo = resultSet.getTime(label);
        LocalTime resultado = null;
        if (!resultSet.wasNull()) {
            resultado = tiempo.toLocalTime();
        }
        return resultado;
    }
}
