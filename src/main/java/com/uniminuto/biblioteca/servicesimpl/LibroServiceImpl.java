package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() throws BadRequestException {
       return this.libroRepository.findAll();
    }

    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {        
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = " 
                    + libroId);
        }
      return optLibro.get();
    }
    
    @Override
public Libro obtenerLibroPorTitulo(String titulo) throws BadRequestException {
    return libroRepository.findByTitulo(titulo)
        .orElseThrow(() -> new BadRequestException("Libro no encontrado con título: " + titulo));
}

     @Override
    public List<Libro> obtenerLibrosPorRangoDeFechas(Integer inicio, Integer fin) throws BadRequestException {
        if (inicio > fin) {
            throw new BadRequestException("El año de inicio no puede ser mayor al año final.");
        }
        return libroRepository.findByAnioPublicacionBetween(inicio, fin);
    }
}
