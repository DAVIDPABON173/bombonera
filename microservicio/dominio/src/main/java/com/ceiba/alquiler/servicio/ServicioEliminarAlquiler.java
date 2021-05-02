package com.ceiba.alquiler.servicio;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;

import java.time.LocalDate;

public class ServicioEliminarAlquiler {

    private static final String CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. se aplica devolución del 90% del valor pagado: $ ";
    private static final String CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. No aplica devolución del pago";

    private static final Double PORCENTAJE_DEVOLUCION = 0.9d;
    private final RepositorioAlquiler repositorioAlquiler;
    private final DaoAlquiler daoAlquiler;

    public ServicioEliminarAlquiler(RepositorioAlquiler repositorioAlquiler, DaoAlquiler daoAlquiler) {
        this.repositorioAlquiler = repositorioAlquiler;
        this.daoAlquiler = daoAlquiler;
    }

    public String ejecutar(Long id) {
        DtoAlquiler dtoAlquiler = this.daoAlquiler.buscar(id);
        this.repositorioAlquiler.eliminar(id);
        if (this.validarDevolucionDeDinero(dtoAlquiler)) {
            return CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO + (dtoAlquiler.getValorPagado() * PORCENTAJE_DEVOLUCION);
        } else {
            return CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO;
        }
    }

    private boolean validarDevolucionDeDinero(DtoAlquiler dtoAlquiler) {
        return dtoAlquiler != null && dtoAlquiler.getFechaAlquiler().isAfter(dtoAlquiler.getFechaSolicitud().toLocalDate())
                && dtoAlquiler.getFechaSolicitud().toLocalDate().isEqual(LocalDate.now());
    }

}
