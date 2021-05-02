package com.ceiba.alquiler.puerto.dao;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;

import java.util.List;

public interface DaoAlquiler {

    /**
     * Permite listar los registros de alquiler
     *
     * @return
     */
    List<DtoAlquiler> listar();


    /**
     * Permite obtener un alquiler a partir de su id
     *
     * @param id
     * @return
     */
    DtoAlquiler buscar(Long id);
}
