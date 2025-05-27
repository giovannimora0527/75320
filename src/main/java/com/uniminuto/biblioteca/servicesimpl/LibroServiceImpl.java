package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Categoria;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import com.uniminuto.biblioteca.model.LibroRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.CategoriaRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

/**
 *
 * @author lmora
 */
@Service
public class LibroServiceImpl implements LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

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
    public List<Libro> obtenerLibrosPorAutor(Integer autorId)
            throws BadRequestException {
        Objects.requireNonNull(autorId, "El id del autor no puede ser vacío.");
        Autor autor = this.autorService.obtenerAutorPorId(autorId);
        if (autor == null) {
            throw new BadRequestException("El autor con el id ingresado no existe.");
        }
        List<Libro> librosAutor = this.libroRepository.findByAutor(autor);
        return !librosAutor.isEmpty()
                ? librosAutor
                : Collections.EMPTY_LIST;
    }

    @Override
    public Libro obtenerLibroPorNombre(String nombreLibro) throws BadRequestException {
        Objects.requireNonNull(nombreLibro, "El nombre del libro es obligatorio.");
        if (nombreLibro.isBlank()) {
            throw new BadRequestException("El nombre del libro es obligatorio.");
        }
        Libro libro = this.libroRepository.findByTitulo(nombreLibro);
        if (libro == null) {
            throw new BadRequestException("No existe el libro con el nombre de "
                    + nombreLibro + ".");
        }
        return libro;
    }

    @Override
    public List<Libro> obtenerLibroXRangoPublicacion(
            Integer fechaInicio, Integer fechaFin)
            throws BadRequestException {
        if (fechaInicio == null) {
            throw new BadRequestException("La fecha de inicio es obligatoria.");
        }
        if (fechaFin == null) {
            throw new BadRequestException("La fecha final es obligatoria.");
        }

        if (fechaFin < fechaInicio) {
            throw new BadRequestException("La fecha final no puede ser menor que la inicial.");
        }

        return this.libroRepository.findByAnioPublicacionBetween(fechaInicio, fechaFin);
    }

    @Override
    public List<Libro> listarLibrosDisponibles() throws BadRequestException {
        // Obtengo la lista de libros con existencias.
        List<Libro> libros = this.listarLibros();
        if (libros.isEmpty()) {
            throw new BadRequestException("No hay libros con existencias en stock.");
        }

        List<Prestamo> listPrestamoByLibro;
        List<Libro> librosParaPrestamo = new ArrayList<>();
        for (Libro libro : libros) {
            listPrestamoByLibro = this.prestamoRepository.findByLibro(libro);
            // Verificar si no hay préstamos activos o si el préstamo está en estado
            // DEVUELTO
            boolean libroDisponible = true;
            if (libro.getExistencias() == 0) {
                libroDisponible = false;
                break; // Si hay al menos un préstamo prestado, el libro no está disponible
            }
            for (Prestamo prestamo : listPrestamoByLibro) {
                if (prestamo.getEstado() == EstadoPrestamo.PRESTADO) {
                    libroDisponible = false;
                    break; // Si hay al menos un préstamo prestado, el libro no está disponible
                }
            }

            // Si el libro no tiene préstamos activos o todos los préstamos están devueltos,
            // lo agregamos a la lista
            if (libroDisponible) {
                librosParaPrestamo.add(libro);
            }
        }
        return librosParaPrestamo;
    }

    @Override
    public RespuestaGenericaRs crearLibro(LibroRq libroRq) throws BadRequestException {
        if (this.libroRepository.existsByTitulo(libroRq.getTitulo())) {
            throw new BadRequestException("El libro se encuentra ya registrado");
        }

        Libro libroGuardar = this.convertirLibroRqToLibro(libroRq);
        this.libroRepository.save(libroGuardar);
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha guardado el libro satisfactoriamente");
        return rta;
    }

    private Libro convertirLibroRqToLibro(LibroRq libroRq) throws BadRequestException {
        Libro libro = new Libro();
        libro.setAnioPublicacion(libroRq.getAnioPublicacion());
        Autor autor = this.autorService.obtenerAutorPorId(libroRq.getAutorId());
        Optional<Categoria> optCat = this.categoriaRepository.findById(libroRq.getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la categoria");
        }
        Categoria categoria = optCat.get();
        libro.setAutor(autor);
        libro.setCategoria(categoria);
        libro.setTitulo(libroRq.getTitulo());
        libro.setExistencias(libroRq.getExistencias());
        return libro;
    }

    @Override
    public RespuestaGenericaRs actualizarLibro(Libro actualizarLibro) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(actualizarLibro.getIdLibro());
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No existe el libro con el ID proporcionado.");
        }

        Libro libroActual = optLibro.get();

        // Verifica si hay cambios reales
        if (!hayCambiosEnLibro(libroActual, actualizarLibro)) {
            RespuestaGenericaRs rta = new RespuestaGenericaRs();
            rta.setMessage("No se detectaron cambios en el libro.");
            return rta;
        }

        // Si cambió el título, valida si ya existe otro libro con ese título
        if (!libroActual.getTitulo().trim().equalsIgnoreCase(actualizarLibro.getTitulo().trim())
                && this.libroRepository.existsByTitulo(actualizarLibro.getTitulo())) {
            throw new BadRequestException("Ya existe un libro con el título '" + actualizarLibro.getTitulo() + "'.");
        }

        // Valida el autor
        if (actualizarLibro.getAutor() == null || actualizarLibro.getAutor().getAutorId() == null) {
            throw new BadRequestException("Debe especificar un autor válido.");
        }
        Autor autor = this.autorService.obtenerAutorPorId(actualizarLibro.getAutor().getAutorId());
        if (autor == null) {
            throw new BadRequestException("No existe el autor con ID " + actualizarLibro.getAutor().getAutorId());
        }

        // Valida la categoría
        if (actualizarLibro.getCategoria() == null || actualizarLibro.getCategoria().getCategoriaId() == null) {
            throw new BadRequestException("Debe especificar una categoría válida.");
        }
        Optional<Categoria> optCat = this.categoriaRepository.findById(actualizarLibro.getCategoria().getCategoriaId());
        if (!optCat.isPresent()) {
            throw new BadRequestException(
                    "No existe la categoría con ID " + actualizarLibro.getCategoria().getCategoriaId());
        }
        Categoria categoria = optCat.get();

        // Actualiza los campos
        libroActual.setTitulo(actualizarLibro.getTitulo());
        libroActual.setAnioPublicacion(actualizarLibro.getAnioPublicacion());
        libroActual.setAutor(autor);
        libroActual.setCategoria(categoria);
        libroActual.setExistencias(actualizarLibro.getExistencias());

        this.libroRepository.save(libroActual);

        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el libro satisfactoriamente.");
        return rta;
    }

    /**
     * Funcion que valida si hay cambios en un registro.
     *
     * @param actual dato entrante de bd.
     * @param nuevo  dato desde el front.
     * @return true/false si hay cambios.
     */
    private boolean hayCambiosEnLibro(Libro actual, Libro nuevo) {
        if (nuevo.getAutor() == null || actual.getAutor() == null
                || nuevo.getCategoria() == null || actual.getCategoria() == null) {
            return true; // Si alguna de las relaciones es nula, consideramos que hay cambio
        }
        return !actual.getTitulo().equals(nuevo.getTitulo())
                || !Objects.equals(actual.getAnioPublicacion(), nuevo.getAnioPublicacion())
                || !actual.getAutor().getAutorId().equals(nuevo.getAutor().getAutorId())
                || !actual.getCategoria().getCategoriaId().equals(nuevo.getCategoria().getCategoriaId())
                || !actual.getExistencias().equals(nuevo.getExistencias());
    }

    @Override
    @Transactional
    public List<Libro> guardarLibrosMasivo(List<LibroRq> libros) throws BadRequestException {
        if (libros == null || libros.isEmpty()) {
            throw new BadRequestException("La lista de libros no puede estar vacía");
        }

        // Validar duplicados en el archivo de entrada
        validarDuplicadosEnArchivo(libros);

        // Validar duplicados con la BD
        validarDuplicadosEnBD(libros);

        // Si llegamos aquí, no hay duplicados, procedemos a guardar
        List<Libro> librosGuardados = new ArrayList<>();
        int fila = 1;

        for (LibroRq libroRq : libros) {
            try {
                // Validar campos requeridos
                if (libroRq.getTitulo() == null || libroRq.getTitulo().trim().isEmpty()) {
                    throw new BadRequestException("El título en la fila " + fila + " es requerido");
                }
                if (libroRq.getAutorId() == null) {
                    throw new BadRequestException("El autor en la fila " + fila + " es requerido");
                }
                if (libroRq.getCategoriaId() == null) {
                    throw new BadRequestException("La categoría en la fila " + fila + " es requerida");
                }
                if (libroRq.getExistencias() == null) {
                    throw new BadRequestException("Las existencias en la fila " + fila + " son requeridas");
                }
                if (libroRq.getExistencias() < 0) {
                    throw new BadRequestException("Las existencias en la fila " + fila + " no pueden ser negativas");
                }

                // Validar que el autor existe
                Autor autor = autorService.obtenerAutorPorId(libroRq.getAutorId());
                if (autor == null) {
                    throw new BadRequestException("El autor con ID " + libroRq.getAutorId()
                            + " en la fila " + fila + " no existe");
                }

                // Validar que la categoría existe
                Optional<Categoria> optCategoria = categoriaRepository.findById(libroRq.getCategoriaId());
                if (!optCategoria.isPresent()) {
                    throw new BadRequestException("La categoría con ID " + libroRq.getCategoriaId()
                            + " en la fila " + fila + " no existe");
                }

                // Validar año de publicación si se proporciona
                if (libroRq.getAnioPublicacion() != null) {
                    int anioActual = java.time.LocalDate.now().getYear();
                    if (libroRq.getAnioPublicacion() > anioActual) {
                        throw new BadRequestException("El año de publicación en la fila " + fila
                                + " no puede ser futuro");
                    }
                }

                // Convertir y guardar libro
                Libro libro = convertirLibroRqToLibro(libroRq);
                librosGuardados.add(libroRepository.save(libro));

            } catch (BadRequestException e) {
                throw new BadRequestException("Error en la fila " + fila + ": " + e.getMessage());
            }
            fila++;
        }

        return librosGuardados;
    }

    /**
     * Valida que no existan duplicados en el archivo de entrada.
     * 
     * @param libros Lista de libros a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnArchivo(List<LibroRq> libros) throws BadRequestException {
        // Validar títulos duplicados en el archivo
        for (int i = 0; i < libros.size(); i++) {
            for (int j = i + 1; j < libros.size(); j++) {
                LibroRq libro1 = libros.get(i);
                LibroRq libro2 = libros.get(j);

                if (libro1.getTitulo().equalsIgnoreCase(libro2.getTitulo())) {
                    throw new BadRequestException(
                            String.format("El título '%s' está duplicado en las filas %d y %d",
                                    libro1.getTitulo(), i + 1, j + 1));
                }
            }
        }
    }

    /**
     * Valida que no existan duplicados con registros en la base de datos.
     * 
     * @param libros Lista de libros a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnBD(List<LibroRq> libros) throws BadRequestException {
        for (int i = 0; i < libros.size(); i++) {
            LibroRq libro = libros.get(i);

            if (libroRepository.existsByTitulo(libro.getTitulo())) {
                throw new BadRequestException(
                        String.format("El título '%s' en la fila %d ya existe en la base de datos",
                                libro.getTitulo(), i + 1));
            }
        }
    }
}