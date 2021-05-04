package com.ceiba.alquiler.controlador;

import com.ceiba.ApplicationMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationMock.class)
@WebMvcTest(ConsultaControladorAlquiler.class)
public class ConsultaControladorAlquilerTest {

    private static final String ALQUILER_NO_ENCONTRADO = "Alquiler no encontrado.";

    @Autowired
    private MockMvc mocMvc;

    @Test
    public void deberialistarLosAlquileres() throws Exception {
        // arrange

        // act - assert
        mocMvc.perform(get("/alquiler")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$[*].documento", hasItem("778899")));
    }

    @Test
    public void deberiaBuscarUnAlquilerPorId() throws Exception {
        // arrange
        Long id = 2L;
        // act - assert
        mocMvc.perform(get("/alquiler/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.documento", notNullValue()));
    }

    @Test
    public void deberiaLanzarExcepcionAlBuscarYNoEncontrarUnAlquilerPorId() throws Exception {
        // arrange
        Long id = 600L;
        // act - assert
        mocMvc.perform(get("/alquiler/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", not(empty())))
                .andExpect(jsonPath("$.mensaje", containsString(ALQUILER_NO_ENCONTRADO)));
    }
}
