package com.uniminuto.biblioteca.service;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    @Transactional
    public Autor crearAutor(Autor autor) throws IllegalArgumentException {
        Optional<Autor> autorExistente = autorRepository.findByNombre(autor.getNombre());
        if (autorExistente.isPresent()) {
            throw new IllegalArgumentException("El nombre del autor ya existe en el sistema.");
        }
        return autorRepository.save(autor);
    }

    @Transactional
    public Autor actualizarAutor(Autor autor) throws IllegalArgumentException {
        Optional<Autor> autorExistente = autorRepository.findById(autor.getId());
        if (autorExistente.isEmpty()) {
            throw new IllegalArgumentException("El autor a actualizar no existe.");
        }

        Optional<Autor> autorConMismoNombre = autorRepository.findByNombre(autor.getNombre());
        if (autorConMismoNombre.isPresent() && !autorConMismoNombre.get().getId().equals(autor.getId())) {
            throw new IllegalArgumentException("El nombre del autor ya existe en el sistema.");
        }

        return autorRepository.save(autor);
    }
}
