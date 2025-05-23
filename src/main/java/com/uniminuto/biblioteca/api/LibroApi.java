package com.uniminuto.biblioteca.api;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author lmora
 */
@CrossOrigin(origins = "*")
@RequestMapping("/libro")
public interface LibroApi {

    /**
     * Metodo para listar los libros registrados en bd.
     *
     * @return Lista de libros registrados.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibros()
            throws BadRequestException;

    /**
     * Metodo para listar los autores registrados en bd.
     *
     * @param libroId Id del libro.
     * @return Obtiene el libro por Id.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-id",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorId(@RequestParam Integer libroId)
            throws BadRequestException;
    
    /**
     * Metodo para obtener los libros dado un autor.
     *
     * @param autorId Id del autor.
     * @return Lista de libros del autor.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-autor",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorAutor(@RequestParam Integer autorId)
            throws BadRequestException;
    
    /**
     * Metodo para listar los libros dado un nombre.
     *
     * @param nombreLibro Nombre del libro.
     * @return Libri que cumpla el criterio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-nombre",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Libro> obtenerLibroPorNombre(@RequestParam String nombreLibro)
            throws BadRequestException;
    
    /**
     * Metodo para listar los libros dado un rango de fecha.
     *
     * @param anioIni
     * @param anioFin
     * @return Lista de libros.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/obtener-libro-anio-publicacion",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> obtenerLibroPorFechaPublicacion(
            @RequestParam Integer anioIni,
            @RequestParam Integer anioFin)
            throws BadRequestException;
    
    /**
     * Metodo para guardar un libro nuevo.
     *
     * @param LibroRq entrada.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/crear-libro",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> crearLibro(@RequestBody LibroRq LibroRq)
            throws BadRequestException;
    
    
    /**
     * Metodo para listar los libros disponibles para prestamo.
     *
     * @return Lista de libros disponibles.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/listar-for-prestamo",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Libro>> listarLibrosParaPrestamo()
            throws BadRequestException;
    
    
    /**
     * Metodo para actualizar un libro nuevo.
     *
     * @param Libro entrada.
     * @return Respuesta del servicio.
     * @throws BadRequestException excepcion.
     */
    @RequestMapping(value = "/actualizar-libro",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<RespuestaGenericaRs> actualizarLibro(@RequestBody Libro Libro)
            throws BadRequestException;
    
}
