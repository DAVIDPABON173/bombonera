package com.ceiba.alquiler.controlador;

import com.ceiba.alquiler.consulta.ManejadorBuscarAlquiler;
import com.ceiba.alquiler.consulta.ManejadorListarAlquileres;
import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alquiler")
@Api(tags = {"Controlador consulta alquiler"})
public class ConsultaControladorAlquiler {

    private final ManejadorListarAlquileres manejadorListarAlquileres;
    private final ManejadorBuscarAlquiler manejadorBuscarAlquiler;

    public ConsultaControladorAlquiler(ManejadorListarAlquileres manejadorListarAlquileres, ManejadorBuscarAlquiler manejadorBuscarAlquiler) {
        this.manejadorListarAlquileres = manejadorListarAlquileres;
        this.manejadorBuscarAlquiler = manejadorBuscarAlquiler;
    }

    @GetMapping
    @ApiOperation("Listar alquileres")
    public List<DtoAlquiler> listar() {
        return this.manejadorListarAlquileres.ejecutar();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Buscar alquiler por Id")
    public DtoAlquiler buscar(@PathVariable Long id) {
        return this.manejadorBuscarAlquiler.ejecutar(id);
    }

}
