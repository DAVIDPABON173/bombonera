package com.ceiba.alquiler.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.alquiler.servicio.ServicioCancelarAlquiler;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorCancelarAlquiler implements ManejadorComandoRespuesta<Long, ComandoRespuesta<String>> {

    private final ServicioCancelarAlquiler servicioCancelarAlquiler;

    public ManejadorCancelarAlquiler(ServicioCancelarAlquiler servicioCancelarAlquiler) {
        this.servicioCancelarAlquiler = servicioCancelarAlquiler;
    }

    public ComandoRespuesta<String> ejecutar(Long idAlquiler) {
        return new ComandoRespuesta<>(this.servicioCancelarAlquiler.ejecutar(idAlquiler));
    }
}
