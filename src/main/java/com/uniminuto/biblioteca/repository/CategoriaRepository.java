package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author lmora
 */
public interface CategoriaRepository  extends
        JpaRepository<Categoria, Integer>{
    
}
