package com.ceiba.alquiler.controlador;

import com.ceiba.ApplicationMock;
import com.ceiba.alquiler.comando.ComandoAlquiler;
import com.ceiba.alquiler.testdatabuilder.ComandoAlquilerTestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationMock.class)
@WebMvcTest(ComandoControladorAlquiler.class)
public class ComandoControladorAlquilerTest {

    private static final String HORARIO_NO_DISPONIBLE = "El horario de alquiler solicitado no se encuentra disponible.";

    private static final String CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. se aplica devolución del 90% del valor pagado: $ ";
    private static final String CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO = "Cancelación exitosa!. No aplica devolución del pago";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mocMvc;

    @Test
    public void deberiaCrearUnAlquiler() throws Exception {
        // arrange
        ComandoAlquiler alquiler = new ComandoAlquilerTestDataBuilder().build();

        // act - assert
        mocMvc.perform(post("/alquiler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alquiler)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.valor", any(Integer.class)));
    }

    @Test
    public void deberiaNoPermitirCrearUnAlquilerPorDisponibilidadHoraria() throws Exception {
        // arrange
        LocalDateTime fechaSolicitud = LocalDateTime.of(2021, 05, 07, 00, 00, 00);
        LocalDate fechaAlquiler = LocalDate.of(2021, 05, 07); // dia Jueves
        LocalTime horaInicio = LocalTime.of(5, 00);
        LocalTime horaFin = LocalTime.of(7, 00);
        ComandoAlquiler alquiler = new ComandoAlquilerTestDataBuilder()
                .conFechaSolicitud(fechaSolicitud).conFechaAlquiler(fechaAlquiler)
                .conHoraInicio(horaInicio).conHoraFin(horaFin).build();
        // act - assert
        mocMvc.perform(post("/alquiler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(alquiler)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.mensaje", containsString(HORARIO_NO_DISPONIBLE)));
    }


    @Test
    public void deberiaCancelarUnAlquilerYNoAplicarDevolucion() throws Exception {
        // arrange
        Long id = 1L;

        // act - assert
        mocMvc.perform(delete("/alquiler/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor", containsString(CANCELACION_EXITOSA_NO_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO)));
    }


    @Test
    public void deberiaCancelarUnAlquilerYAplicarDevolucion() throws Exception {
        // arrange
        Long id = 3L;

        // act - assert
        mocMvc.perform(delete("/alquiler/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor", containsString(CANCELACION_EXITOSA_SE_APLICA_DEVOLUCION_PORCENTAJE_DEL_PAGO)));
    }


}
