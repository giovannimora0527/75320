package com.uniminuto.biblioteca.servicesimpl;
 
import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class LibroServiceImpl implements LibroService {
 
    @Autowired
    private LibroRepository libroRepository;
 
    @Autowired
    private AutorService autorService;
 
    @Override
    public List<Libro> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll();
    }
 
    @Override
    public Libro obtenerLibroId(Integer libroId) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(libroId);
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No se encuentra el libro con el id = " + libroId);
        }
        return optLibro.get();
    }
 
    @Override
    public List<Libro> obtenerLibrosPorAutor(Integer autorId) throws BadRequestException {
        if (autorId == null) {
            throw new BadRequestException("El id del autor no puede ser vacío.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) {
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return librosAutor.isEmpty() ? Collections.emptyList() : librosAutor;
    }
 
    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        if (nombreLibro == null || nombreLibro.isBlank()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de " + nombreLibro + ".");
        }
        return libro;
    }
 
    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(Integer fechaInicio, Integer fechaFin) throws BadRequestException {
        if (fechaInicio == null || fechaFin == null) {
            throw new BadRequestException("Ambas fechas, de inicio y final, son obligatorias.");
        }
        if (fechaFin < fechaInicio) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }
 
        return this.libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);
    }
 
    @Override
    public RespuestaGenerica guardarLibro(LibroRq libroRq) throws BadRequestException {
        // Validaciones básicas
        if (libroRq.getTitulo() == null || libroRq.getTitulo().isBlank()) {
            throw new BadRequestException("El título del libro es obligatorio.");
        }
        if (libroRq.getExistencias() == null || libroRq.getExistencias() < 0) {
            throw new BadRequestException("La cantidad de existencias debe ser un número positivo.");
        }
        // Validar que no exista un libro con el mismo título
        Libro libroExistente = this.libroRepository.findByTitulo(libroRq.getTitulo());
        if (libroExistente != null) {
            throw new BadRequestException("Ya existe un libro registrado con el título: " + libroRq.getTitulo());
        }
        // Validar que el autor existe
        Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());
        if (autor == null) {
            throw new BadRequestException("El autor con ID " + libroRq.getAutorId() + " no existe.");
        }
        // Validar año de publicación si se proporciona
        if (libroRq.getAnioPublicacion() != null && libroRq.getAnioPublicacion() <= 0) {
            throw new BadRequestException("El año de publicación debe ser un valor positivo.");
        }
        // Crear y guardar el nuevo libro
        Libro nuevoLibro = transformarLibroRqToLibro(libroRq);
        this.libroRepository.save(nuevoLibro);
        RespuestaGenerica respuesta = new RespuestaGenerica();
        respuesta.setMessage("Libro guardado exitosamente.");
        return respuesta;
    }
 
    @Override
    public RespuestaGenerica actualizarLibro(Libro libro) throws BadRequestException {
        // Verificar que el libro existe
        Optional<Libro> libroOpt = this.libroRepository.findById(libro.getIdLibro());
        if (!libroOpt.isPresent()) {
            throw new BadRequestException("No existe el libro con ID " + libro.getIdLibro());
        }
        RespuestaGenerica respuesta = new RespuestaGenerica();
        respuesta.setMessage("Libro actualizado exitosamente.");
        Libro libroActual = libroOpt.get();
        // Verificar si hay cambios
        if (!hayCambios(libroActual, libro)) {
            return respuesta;
        }
        // Validar cambios en el título
        if (!libroActual.getTitulo().equals(libro.getTitulo())) {
            Libro libroConMismoTitulo = this.libroRepository.findByTitulo(libro.getTitulo());
            if (libroConMismoTitulo != null && !libroConMismoTitulo.getIdLibro().equals(libro.getIdLibro())) {
                throw new BadRequestException("Ya existe otro libro con el título: " + libro.getTitulo());
            }
        }
        // Validar cambios en el autor
        if (!libroActual.getAutor().getAutorId().equals(libro.getAutor().getAutorId())) {
            Autor autor = this.autorService.obtenerAutorPorId(libro.getAutor().getAutorId());
            if (autor == null) {
                throw new BadRequestException("El autor con ID " + libro.getAutor().getAutorId() + " no existe.");
            }
        }
        // Validar existencias
        if (libro.getExistencias() == null || libro.getExistencias() < 0) {
            throw new BadRequestException("La cantidad de existencias debe ser un número positivo.");
        }
        // Actualizar los campos del libro
        libroActual.setTitulo(libro.getTitulo());
        libroActual.setAnioPublicacion(libro.getAnioPublicacion());
        libroActual.setAutor(libro.getAutor());
        libroActual.setCategoria(libro.getCategoria());
        libroActual.setExistencias(libro.getExistencias());
        this.libroRepository.save(libroActual);
        return respuesta;
    }
 
   private Libro transformarLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
    Libro libro = new Libro();
    libro.setTitulo(libroRq.getTitulo());
    libro.setAnioPublicacion(libroRq.getAnioPublicacion());
    libro.setCategoria(libroRq.getCategoria());
    libro.setExistencias(libroRq.getExistencias());
    // Verificar que autorId no es null
    if (libroRq.getAutorId() == null) {
        throw new BadRequestException("El ID del autor es obligatorio.");
    }
 
    // Obtener el autor del servicio
    Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());
    if (autor == null) {
        throw new BadRequestException("El autor con el ID " + libroRq.getAutorId() + " no existe.");
    }
 
    libro.setAutor(autor);
    return libro;
}
 
 
    private boolean hayCambios(Libro libroActual, Libro libroNuevo) {
        return !libroActual.getTitulo().equals(libroNuevo.getTitulo()) ||
               !libroActual.getAnioPublicacion().equals(libroNuevo.getAnioPublicacion()) ||
               !libroActual.getAutor().getAutorId().equals(libroNuevo.getAutor().getAutorId()) ||
               !libroActual.getCategoria().equals(libroNuevo.getCategoria()) ||
               !libroActual.getExistencias().equals(libroNuevo.getExistencias());
    }
}