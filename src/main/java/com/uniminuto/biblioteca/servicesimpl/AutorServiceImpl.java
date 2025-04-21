package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public List<AutorRs> listarAutores() throws BadRequestException {
    return autorRepository.findAll().stream().map(autor -> {
        AutorRs dto = new AutorRs();
        dto.setNombre(autor.getNombre());
        dto.setFechaNacimiento(autor.getFechaNacimiento());
        dto.setNacionalidad(autor.getNacionalidad());

        List<Libro> libros = autor.getLibros();
        dto.setNumeroLibros(libros.size());
        dto.setTitulosLibros(libros.stream()
            .map(Libro::getTitulo)
            .collect(Collectors.toList()));

        return dto;
    }).collect(Collectors.toList());
}

    @Override
    public RespuestaGenerica guardarAutor(AutorRq autor) throws BadRequestException {
        if (autorRepository.findByNombre(autor.getNombre()).isPresent()) {
            throw new BadRequestException("Ya existe un autor con el nombre: " + autor.getNombre());
        }

        Autor nuevo = new Autor();
        nuevo.setNombre(autor.getNombre());
        nuevo.setFechaNacimiento(autor.getFechaNacimiento());
        nuevo.setNacionalidad(autor.getNacionalidad());

        autorRepository.save(nuevo);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Autor creado exitosamente.");
        return rta;
    }

    @Override
    public RespuestaGenerica actualizarAutor(AutorRq autor) throws BadRequestException {
        Optional<Autor> autorActualOpt = autorRepository.findByNombre(autor.getNombreOriginal());

        if (!autorActualOpt.isPresent()) {
            throw new BadRequestException("No se encontró un autor con el nombre original: " + autor.getNombreOriginal());
        }

        Autor autorActual = autorActualOpt.get();

        // Validar si el nuevo nombre ya existe y no es el mismo autor
        if (!autor.getNombre().equals(autor.getNombreOriginal())) {
            Optional<Autor> autorConMismoNombre = autorRepository.findByNombre(autor.getNombre());
            if (autorConMismoNombre.isPresent()) {
                throw new BadRequestException("Ya existe otro autor con el nombre: " + autor.getNombre());
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setNacionalidad(autor.getNacionalidad());

        autorRepository.save(autorActual);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Autor actualizado correctamente.");
        return rta;
    }
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

}
