package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Nacionalidad;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lmora
 */
public interface NacionalidadRepository  extends
        JpaRepository<Nacionalidad, Integer>{
    
}