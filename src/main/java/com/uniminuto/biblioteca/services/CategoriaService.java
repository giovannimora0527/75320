package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Categoria;
import java.util.List;

/**
 *
 * @author juand
 */
public interface CategoriaService {
    
    List<Categoria> obtenerListaCategorias();
}