package com.ceiba.alquiler.consulta;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ManejadorListarAlquileres {

    private final DaoAlquiler daoAlquiler;

    public ManejadorListarAlquileres(DaoAlquiler daoAlquiler) {
        this.daoAlquiler = daoAlquiler;
    }

    public List<DtoAlquiler> ejecutar() {
        return this.daoAlquiler.listar();
    }
}
