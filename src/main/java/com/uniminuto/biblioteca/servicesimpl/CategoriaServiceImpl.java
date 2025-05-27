package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.services.CategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class CategoriaServiceImpl implements CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerListaCategorias() {
        return this.categoriaRepository.findAll();
    }
    
}