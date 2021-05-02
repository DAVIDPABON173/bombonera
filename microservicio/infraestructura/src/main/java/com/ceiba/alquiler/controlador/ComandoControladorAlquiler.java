package com.ceiba.alquiler.controlador;

import com.ceiba.ComandoRespuesta;
import com.ceiba.alquiler.comando.ComandoAlquiler;
import com.ceiba.alquiler.comando.manejador.ManejadorCrearAlquiler;
import com.ceiba.alquiler.comando.manejador.ManejadorEliminarAlquiler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alquiler")
@Api(tags = {"Controlador comando alquiler"})
public class ComandoControladorAlquiler {

    private final ManejadorCrearAlquiler manejadorCrearAlquiler;
    private final ManejadorEliminarAlquiler manejadorEliminarAlquiler;

    @Autowired
    public ComandoControladorAlquiler(ManejadorCrearAlquiler manejadorCrearAlquiler, ManejadorEliminarAlquiler manejadorEliminarAlquiler) {
        this.manejadorCrearAlquiler = manejadorCrearAlquiler;
        this.manejadorEliminarAlquiler = manejadorEliminarAlquiler;
    }

    @PostMapping
    @ApiOperation("Crear alquiler")
    public ComandoRespuesta<Long> crear(@RequestBody ComandoAlquiler comandoAlquiler) {
        return manejadorCrearAlquiler.ejecutar(comandoAlquiler);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Eliminar alquiler")
    public ComandoRespuesta<String> eliminar(@PathVariable Long id) {
        return manejadorEliminarAlquiler.ejecutar(id);
    }
}
