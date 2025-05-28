package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import java.util.List;

/**
 *
 * @author lmora
 */
public interface NacionalidadService {
    /**
     * Lista las nacionalidades disponibles en bd.
     * @return Nacionalidades.
     */
    List<Nacionalidad> obtenerListaNacionalidades();
}
