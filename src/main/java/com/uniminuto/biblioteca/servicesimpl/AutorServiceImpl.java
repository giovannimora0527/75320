package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public RespuestaGenerica guardarAutor(AutorRq autor)
            throws BadRequestException {
        Optional<Autor> optUser = this.autorRepository
                .findByNombre(autor.getNombre());
        if (optUser.isPresent()) {
            throw new BadRequestException("El autor se encuentra registrado con el nombre: "
                    + autor.getNombre() + ". Valide e intente de nuevo.");
        }

        Autor userToSave = this.transformarAutorRqToAutor(autor);
        this.autorRepository.save(userToSave);
        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Se ha guardado el autor satisfactoriamente.");
        return rta;
    }

    private Autor transformarAutorRqToAutor(AutorRq autor) {
    Autor user = new Autor();
    user.setNacionalidad(autor.getNacionalidad());
    user.setNombre(autor.getNombre());
    user.setFechaNacimiento(autor.getFechaNacimiento());  // Asegúrate de usar 'fechaNacimiento'
    return user;
}

    @Override
    public RespuestaGenerica actualizarAutor(Autor autor)
            throws BadRequestException {
        //Paso 1.
        Optional<Autor> userOpt = this.autorRepository
                .findById(autor.getAutorId());
        if (!userOpt.isPresent()) {
            throw new BadRequestException("No existe el autor.");
        }
        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Se ha actualizado el autor.");
        Autor userActual = userOpt.get();
        if (!this.hayCambios(userActual, autor)) {
            // Paso 3.
            return rta;
        }

        // Paso 4 y paso 5
        if (!userActual.getNombre().equals(autor.getNombre())) {
            // Consulto si existe el nombre en la bd
            // Si existe lanzo excepcion
            if (this.autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException("El autor ya se encuentra registrado con el "
                        + "nombre " + autor.getNombre());
            }
        }

        
        userActual.setNombre(autor.getNombre());
        userActual.setNacionalidad(autor.getNacionalidad());
        userActual.setFechaNacimiento(autor.getFechaNacimiento());

        this.autorRepository.save(userActual);
        return rta;
    }

    private boolean hayCambios(Autor usuarioActual, Autor autor) {
        if (!usuarioActual.getNombre().equals(autor.getNombre())) {
            return true;
        }
        if (!usuarioActual.getNacionalidad().equals(autor.getNacionalidad())) {
            return true;
        }
        if (!usuarioActual.getFechaNacimiento().equals(autor.getFechaNacimiento())) {
            return true;
        }
        return false;
    }

}