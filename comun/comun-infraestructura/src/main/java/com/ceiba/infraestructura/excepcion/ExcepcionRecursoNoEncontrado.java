package com.ceiba.infraestructura.excepcion;

public class ExcepcionRecursoNoEncontrado extends  RuntimeException {

    public ExcepcionRecursoNoEncontrado(String message, Exception e) {
        super(message, e);
    }
}
