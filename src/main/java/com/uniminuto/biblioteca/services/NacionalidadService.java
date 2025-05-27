package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;

/**
 *
 * @author juand
 */
public interface NacionalidadService {
    /**
     * Lista las nacionalidades disponibles en bd.
     * @return Nacionalidades.
     */
    List<Nacionalidad> obtenerListaNacionalidades();
}