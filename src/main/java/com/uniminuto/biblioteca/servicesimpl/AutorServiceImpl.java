package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Autor> obtenerAutoresPorNacionalidad(String nacionalidad) {
        System.out.println("Ejecutando consulta para nacionalidad: " + nacionalidad);
        List<Autor> autores = autorRepository.findByNacionalidadIgnoreCase(nacionalidad);
        System.out.println("Autores encontrados: " + autores);
        return autores;
    }

    @Override
    public Autor guardarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> autorExistente = autorRepository.findByNombre(autor.getNombre());
        if (autorExistente.isPresent()) {
            throw new BadRequestException("El nombre del autor ya existe en el sistema.");
        }
        return autorRepository.save(autor);
    }

    @Override
    public Autor actualizarAutor(Autor autor) throws BadRequestException {
        if (autor.getId() == null) {
            throw new BadRequestException("El ID del autor es requerido para la actualización.");
        }
        Optional<Autor> autorExistente = autorRepository.findById(autor.getId());
        if (!autorExistente.isPresent()) {
            throw new BadRequestException("El autor a actualizar no existe.");
        }
        Optional<Autor> autorConNombre = autorRepository.findByNombre(autor.getNombre());
        if (autorConNombre.isPresent() && !autorConNombre.get().getId().equals(autor.getId())) {
            throw new BadRequestException("El nombre del autor ya existe en el sistema.");
        }
        return autorRepository.save(autor);
    }
}
