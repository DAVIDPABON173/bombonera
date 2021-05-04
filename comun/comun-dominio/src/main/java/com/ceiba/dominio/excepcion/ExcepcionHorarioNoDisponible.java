package com.ceiba.dominio.excepcion;

public class ExcepcionHorarioNoDisponible extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcepcionHorarioNoDisponible(String mensaje) {
        super(mensaje);
    }
}