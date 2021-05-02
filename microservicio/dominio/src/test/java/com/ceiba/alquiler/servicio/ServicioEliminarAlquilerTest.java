package com.ceiba.alquiler.servicio;

import com.ceiba.alquiler.modelo.dto.DtoAlquiler;
import com.ceiba.alquiler.modelo.entidad.Alquiler;
import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.alquiler.servicio.testdatabuilder.AlquilerTestDataBuider;
import com.ceiba.alquiler.servicio.testdatabuilder.DtoAlquilerTestDataBuilder;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServicioEliminarAlquilerTest {

    private static final String CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. se aplica devolución del 90% del valor pagado: $ ";
    private static final String CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. No aplica devolución del pago";
    private static final Double PORCENTAJE_DEVOLUCION = 0.9d;

    private static final String NOT_WORKING_WEDNESDAY = "WEDNESDAY";
    private static final Double VALOR_PAGADO_TEST = 180000d;

    @Test
    public void DeberiaCancelarUnAlquilerSinDevolucionDeDinero() {
        // arrange
        RepositorioAlquiler repositorioAlquiler = Mockito.mock(RepositorioAlquiler.class);
        DaoAlquiler daoAlquiler = Mockito.mock(DaoAlquiler.class);

        DtoAlquilerTestDataBuilder dtoAlquilerTestDataBuilder = new DtoAlquilerTestDataBuilder();
        DtoAlquiler dtoAlquiler = dtoAlquilerTestDataBuilder.build();
        Mockito.when(daoAlquiler.buscar(Mockito.anyLong())).thenReturn(dtoAlquiler);
        Mockito.doNothing().when(repositorioAlquiler).eliminar(Mockito.anyLong());
        ServicioEliminarAlquiler servicioEliminarAlquiler = new ServicioEliminarAlquiler(repositorioAlquiler, daoAlquiler);
        // act - assert
        String respuesta = servicioEliminarAlquiler.ejecutar(Mockito.anyLong());
        assertEquals(respuesta, CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO);
    }


    @Test
    public void DeberiaCancelarUnAlquilerYrealizarDevolucionDeDinero() {
        // arrange
        RepositorioAlquiler repositorioAlquiler = Mockito.mock(RepositorioAlquiler.class);
        DaoAlquiler daoAlquiler = Mockito.mock(DaoAlquiler.class);

        LocalDateTime fechaSolicitud = LocalDateTime.now();
        LocalDate fechaAlquiler = LocalDate.now().plusDays(1).getDayOfWeek().toString() != NOT_WORKING_WEDNESDAY ? LocalDate.now().plusDays(1) : LocalDate.now().plusDays(2);

        DtoAlquilerTestDataBuilder dtoAlquilerTestDataBuilder = new DtoAlquilerTestDataBuilder()
                .conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler).conValorPagado(VALOR_PAGADO_TEST);
        DtoAlquiler dtoAlquiler = dtoAlquilerTestDataBuilder.build();

        Mockito.when(daoAlquiler.buscar(Mockito.anyLong())).thenReturn(dtoAlquiler);
        Mockito.doNothing().when(repositorioAlquiler).eliminar(Mockito.anyLong());
        ServicioEliminarAlquiler servicioEliminarAlquiler = new ServicioEliminarAlquiler(repositorioAlquiler, daoAlquiler);
        // act - assert
        String respuesta = servicioEliminarAlquiler.ejecutar(Mockito.anyLong());
        assertEquals(respuesta, CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO + (dtoAlquiler.getValorPagado() * PORCENTAJE_DEVOLUCION));
    }


}
