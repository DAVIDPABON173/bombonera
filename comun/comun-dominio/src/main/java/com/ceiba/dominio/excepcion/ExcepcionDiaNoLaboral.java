package com.ceiba.dominio.excepcion;

public class ExcepcionDiaNoLaboral extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ExcepcionDiaNoLaboral(String mensaje) {
        super(mensaje);
    }
}
