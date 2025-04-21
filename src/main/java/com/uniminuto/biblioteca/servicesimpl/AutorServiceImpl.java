package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
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
    public Autor crearAutor(Autor autor) {
        if (autorRepository.findByNombre(autor.getNombre()).isPresent()) {
            throw new RuntimeException("El autor ya existe");
        }

        return autorRepository.save(autor);
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
    public RespuestaGenerica guardarAutor(AutorRq autorRq) throws BadRequestException {
        // Validar si ya existe un autor con el mismo nombre
    Optional<Autor> optAutor = this.autorRepository.findByNombre(autorRq.getNombre());
    if (optAutor.isPresent()) {
        throw new BadRequestException("El autor ya se encuentra registrado con el nombre: "
                + autorRq.getNombre());
    }

    // Transformar AutorRq a entidad Autor
    Autor autorToSave = this.transformarAutorRqToAutor(autorRq);

    // Guardar en la base de datos
    this.autorRepository.save(autorToSave);

    // Preparar respuesta
    RespuestaGenerica rta = new RespuestaGenerica();
    rta.setMessage("Se ha guardado el autor satisfactoriamente.");
    return rta;
}
    private Autor transformarAutorRqToAutor(AutorRq autorRq) {
    Autor autor = new Autor();
    autor.setNombre(autorRq.getNombre());
    autor.setNacionalidad(autorRq.getNacionalidad());
    autor.setFechaNacimiento(autorRq.getFechaNacimiento());
    autor.setActivo(Boolean.TRUE); // por defecto en true
    return autor;
    }

    @Override
    public RespuestaGenerica actualizarAutor(Autor autor) throws BadRequestException {
        // Paso 1: Buscar el autor por ID
    Optional<Autor> autorOpt = this.autorRepository.findById(autor.getAutorId());
    if (!autorOpt.isPresent()) {
        throw new BadRequestException("No existe un autor con el ID: " + autor.getAutorId());
    }

    Autor autorActual = autorOpt.get();

    // Paso 2: Validar si hubo cambios
    if (!hayCambiosAutor(autorActual, autor)) {
        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("No se realizaron cambios en el autor.");
        return rta;
    }

    // Paso 3: Validar que el nuevo nombre no exista si se cambió
    if (!autorActual.getNombre().equals(autor.getNombre())) {
        Optional<Autor> autorConMismoNombre = this.autorRepository.findByNombre(autor.getNombre());
        if (autorConMismoNombre.isPresent()) {
            throw new BadRequestException("Ya existe un autor con el nombre: " + autor.getNombre());
        }
    }

    // Paso 4: Actualizar los campos
    autorActual.setNombre(autor.getNombre());
    autorActual.setNacionalidad(autor.getNacionalidad());
    autorActual.setFechaNacimiento(autor.getFechaNacimiento());
    autorActual.setActivo(autor.getActivo());

    // Paso 5: Guardar
    this.autorRepository.save(autorActual);

    // Paso 6: Respuesta
    RespuestaGenerica rta = new RespuestaGenerica();
    rta.setMessage("Se ha actualizado el autor correctamente.");
    return rta;
}
    private boolean hayCambiosAutor(Autor actual, Autor nuevo) {
    if (!actual.getNombre().equals(nuevo.getNombre())) return true;
    if (!actual.getNacionalidad().equals(nuevo.getNacionalidad())) return true;
    if (!actual.getFechaNacimiento().equals(nuevo.getFechaNacimiento())) return true;
    if (!actual.getActivo().equals(nuevo.getActivo())) return true;
    return false;
}

}
