package com.ceiba.alquiler.comando.manejador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.alquiler.comando.ComandoAlquiler;
import com.ceiba.alquiler.comando.fabrica.FabricaAlquiler;
import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.servicio.ServicioCrearAlquiler;
import com.ceiba.manejador.ManejadorComandoRespuesta;
import org.springframework.stereotype.Component;

@Component
public class ManejadorCrearAlquiler implements ManejadorComandoRespuesta<ComandoAlquiler, ComandoRespuesta<Long>> {

    private final FabricaAlquiler fabricaAlquiler;

    private final ServicioCrearAlquiler servicioCrearAlquiler;

    public ManejadorCrearAlquiler(FabricaAlquiler fabricaAlquiler, ServicioCrearAlquiler servicioCrearAlquiler) {
        this.fabricaAlquiler = fabricaAlquiler;
        this.servicioCrearAlquiler = servicioCrearAlquiler;
    }

    public ComandoRespuesta<Long> ejecutar(ComandoAlquiler comandoAlquiler) {
        Alquiler alquiler = this.fabricaAlquiler.crear(comandoAlquiler);
        return new ComandoRespuesta<>(this.servicioCrearAlquiler.ejecutar(alquiler));
    }
}
