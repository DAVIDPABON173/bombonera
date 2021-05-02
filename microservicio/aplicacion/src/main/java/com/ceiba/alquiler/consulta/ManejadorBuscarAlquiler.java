package com.ceiba.alquiler.consulta;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import org.springframework.stereotype.Component;


@Component
public class ManejadorBuscarAlquiler {

    private final DaoAlquiler daoAlquiler;

    public ManejadorBuscarAlquiler(DaoAlquiler daoAlquiler) {
        this.daoAlquiler = daoAlquiler;
    }

    public DtoAlquiler ejecutar(Long id) {
        return this.daoAlquiler.buscar(id);
    }

}
