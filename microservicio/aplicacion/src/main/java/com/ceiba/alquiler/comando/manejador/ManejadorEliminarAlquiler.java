package com.ceiba.alquiler.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.alquiler.servicio.ServicioEliminarAlquiler;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorEliminarAlquiler implements ManejadorComandoRespuesta<Long, ComandoRespuesta<String>> {

    private final ServicioEliminarAlquiler servicioEliminarAlquiler;

    public ManejadorEliminarAlquiler(ServicioEliminarAlquiler servicioEliminarAlquiler) {
        this.servicioEliminarAlquiler = servicioEliminarAlquiler;
    }

    public ComandoRespuesta<String> ejecutar(Long idAlquiler) {
        return new ComandoRespuesta<>(this.servicioEliminarAlquiler.ejecutar(idAlquiler));
    }
}
