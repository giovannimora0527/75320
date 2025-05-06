package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        this.autorRepository.findByNacionalidad(nacionalidad).forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }

        return listaAutores;
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }

    @Override
    public Autor crearAutor(Autor autor) throws BadRequestException {
        if (autor.getNombre() == null || autor.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del autor es obligatorio.");
        }

        Optional<Autor> existente = autorRepository.findByNombre(autor.getNombre().trim());
        if (existente.isPresent()) {
            throw new BadRequestException("Ya existe un autor con ese nombre.");
        }

        return autorRepository.save(autor);
    }

    @Override
    public void eliminarAutor(Integer autorId) throws BadRequestException {
        Optional<Autor> autor = autorRepository.findById(autorId);
        if (!autor.isPresent()) {
            throw new BadRequestException("No se encontró el autor con ID " + autorId);
        }

        autorRepository.deleteById(autorId);
    }

    @Override
   public Autor actualizarAutor(Autor autor) throws BadRequestException {
    // 1. Validar que el autor y su ID no sean nulos
    if (autor == null || autor.getAutorId() == null) {
        throw new BadRequestException("El ID del autor es requerido");
    }

    // 2. Buscar el autor existente
    Autor autorExistente = autorRepository.findById(autor.getAutorId())
        .orElseThrow(() -> new BadRequestException("Autor no encontrado con ID: " + autor.getAutorId()));

    // 3. Validar nombre único solo si cambió
    if (!autorExistente.getNombre().equalsIgnoreCase(autor.getNombre().trim())) {
        Optional<Autor> autorConMismoNombre = autorRepository.findByNombre(autor.getNombre().trim());
        
        if (autorConMismoNombre.isPresent() && 
            !autorConMismoNombre.get().getAutorId().equals(autor.getAutorId())) {
            throw new BadRequestException("Ya existe un autor con el nombre: " + autor.getNombre());
        }
    }

    // 4. Actualizar campos
    autorExistente.setNombre(autor.getNombre().trim());
    autorExistente.setNacionalidad(autor.getNacionalidad());
    autorExistente.setFechaNacimiento(autor.getFechaNacimiento());

    // 5. Guardar cambios
    return autorRepository.save(autorExistente);
    }

}
