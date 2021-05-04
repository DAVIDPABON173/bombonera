package com.ceiba.configuracion;

import com.ceiba.alquiler.puerto.dao.DaoAlquiler;
import com.ceiba.alquiler.puerto.repositorio.RepositorioAlquiler;
import com.ceiba.alquiler.servicio.ServicioCancelarAlquiler;
import com.ceiba.alquiler.servicio.ServicioCrearAlquiler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServicio {

    @Bean
    public ServicioCrearAlquiler servicioCrearAlquiler(RepositorioAlquiler repositorioAlquiler) {
        return new ServicioCrearAlquiler(repositorioAlquiler);
    }

    @Bean
    public ServicioCancelarAlquiler servicioCancelarAlquiler(RepositorioAlquiler repositorioAlquiler, DaoAlquiler daoAlquiler) {
        return new ServicioCancelarAlquiler(repositorioAlquiler, daoAlquiler);
    }


}
