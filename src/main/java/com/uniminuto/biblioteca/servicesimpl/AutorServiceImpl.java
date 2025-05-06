package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sofía Pedraza
 */
@Service
public class AutorServiceImpl implements AutorService {

@Autowired
    private AutorRepository autorRepository;

    @Override
    public List<Autor> listarTodo() throws BadRequestException {
        return autorRepository.findAll();
    }

    @Override
    public Autor buscarPorNombre(String nombre) throws BadRequestException {
        if (nombre == null || nombre.isBlank()) {
            throw new BadRequestException("El nombre es obligatorio.");
        }

        return autorRepository.findByNombre(nombre)
                .orElseThrow(() -> new BadRequestException("Autor no encontrado."));
    }

    @Override
    public AutorRs guardarAutorNuevo(AutorRq autorRq) throws BadRequestException {
        if (autorRepository.existsByNombre(autorRq.getNombre())) {
            throw new BadRequestException("Ya existe un autor con este nombre.");
        }

        Autor autor = new Autor();
        autor.setNombre(autorRq.getNombre());
        autor.setNacionalidad(autorRq.getNacionalidad());
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        autorRepository.save(autor);

        AutorRs respuesta = new AutorRs();
        respuesta.setMessage("Autor guardado con éxito.");
        return respuesta;
    }

    @Override
    public AutorRs actualizarAutor(Autor autor) throws BadRequestException {
        Optional<Autor> optAutor = autorRepository.findById(autor.getAutorId());
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }

        Autor autorExistente = optAutor.get();
        if (!autorExistente.getNombre().equals(autor.getNombre())
                && autorRepository.existsByNombre(autor.getNombre())) {
            throw new BadRequestException("El nombre ya existe.");
        }

        autorExistente.setNombre(autor.getNombre());
        autorExistente.setNacionalidad(autor.getNacionalidad());
        autorExistente.setFechaNacimiento(autor.getFechaNacimiento());

        autorRepository.save(autorExistente);

        AutorRs respuesta = new AutorRs();
        respuesta.setMessage("Autor actualizado con éxito.");
        return respuesta;
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