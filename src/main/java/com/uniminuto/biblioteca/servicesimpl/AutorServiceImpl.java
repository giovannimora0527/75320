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
    public boolean existeAutorPorNombre(String nombre) {
        return autorRepository.existsByNombre(nombre);
}

    @Override
    public Autor crearAutor(Autor autor) {
       return autorRepository.save(autor);
}

    @Override
    public Autor actualizarAutor(Integer id, Autor autorActualizado) {
     Autor autorExistente = autorRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + id));

      if (!autorExistente.getNombre().equals(autorActualizado.getNombre())
            && autorRepository.existsByNombre(autorActualizado.getNombre())) {
         throw new IllegalArgumentException("Ya existe otro autor con ese nombre.");
    }

       autorExistente.setNombre(autorActualizado.getNombre());
         return autorRepository.save(autorExistente);
}


}
