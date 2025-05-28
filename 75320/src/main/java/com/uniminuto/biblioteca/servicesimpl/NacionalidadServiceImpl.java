package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.NacionalidadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class NacionalidadServiceImpl implements NacionalidadService {

    /**
     * Repositorio de datos para Nacionalidades.
     */
    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public List<Nacionalidad> obtenerListaNacionalidades() {
        return this.nacionalidadRepository.findAll();
    }

}
