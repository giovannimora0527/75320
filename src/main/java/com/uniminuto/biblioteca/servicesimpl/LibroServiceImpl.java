package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.CargaMasivaError;
import com.uniminuto.biblioteca.model.LibroRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.AutorService;
import com.uniminuto.biblioteca.services.LibroService;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LibroServiceImpl implements LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final AutorService autorService;

    @Autowired
    public LibroServiceImpl(LibroRepository libroRepository,
                            AutorRepository autorRepository,
                            AutorService autorService) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.autorService = autorService;
    }

    @Override
    public List<LibroRs> listarLibros() throws BadRequestException {
        return this.libroRepository.findAll().stream().map(libro -> {
            LibroRs dto = new LibroRs();
            dto.setTitulo(libro.getTitulo());
            dto.setCategoria(libro.getCategoria());
            dto.setExistencias(libro.getExistencias());
            dto.setNombreAutor(libro.getAutor().getNombre());
            dto.setAnioPublicacion(libro.getAnioPublicacion());
            return dto;
        }).collect(Collectors.toList());
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
        return !librosAutor.isEmpty() ? librosAutor : Collections.emptyList();
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
    public List<CargaMasivaError> cargarLibrosDesdeCsv(MultipartFile file) {
        List<CargaMasivaError> errores = new ArrayList<>();
        List<Libro> librosValidos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            int numeroLinea = 0;

            reader.readLine(); // Saltar cabecera
            numeroLinea++;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                String[] campos = linea.split(",", -1); // -1 incluye vacíos

                String titulo = campos.length > 0 ? campos[0].trim() : "";
                String anioStr = campos.length > 1 ? campos[1].trim() : "";
                String categoria = campos.length > 2 ? campos[2].trim() : "";
                String existenciasStr = campos.length > 3 ? campos[3].trim() : "";
                String idAutorStr = campos.length > 4 ? campos[4].trim() : "";

                boolean tieneError = false;

                if (titulo.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo título es obligatorio"));
                    tieneError = true;
                }
                if (anioStr.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo año de publicación es obligatorio"));
                    tieneError = true;
                }
                if (categoria.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo categoría es obligatorio"));
                    tieneError = true;
                }
                if (existenciasStr.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo existencias es obligatorio"));
                    tieneError = true;
                }
                if (idAutorStr.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo ID del autor es obligatorio"));
                    tieneError = true;
                }

                if (tieneError) continue;

                try {
                    int anio = Integer.parseInt(anioStr);
                    int existencias = Integer.parseInt(existenciasStr);
                    Integer idAutor = Integer.parseInt(idAutorStr);

                    Optional<Autor> autorOpt = autorRepository.findById(idAutor);
                    if (!autorOpt.isPresent()) {
                        errores.add(new CargaMasivaError(numeroLinea, "No existe un autor con ID " + idAutor));
                        continue;
                    }

                    if (libroRepository.existsByTitulo(titulo)) {
                        errores.add(new CargaMasivaError(numeroLinea, "Ya existe un libro con el título '" + titulo + "'"));
                        continue;
                    }

                    Libro libro = new Libro();
                    libro.setTitulo(titulo);
                    libro.setAnioPublicacion(anio);
                    libro.setCategoria(categoria);
                    libro.setExistencias(existencias);
                    libro.setAutor(autorOpt.get());

                    librosValidos.add(libro);

                } catch (NumberFormatException e) {
                    errores.add(new CargaMasivaError(numeroLinea, "Año, existencias e ID del autor deben ser numéricos"));
                }
            }

        } catch (IOException e) {
            errores.add(new CargaMasivaError(0, "Error al leer el archivo: " + e.getMessage()));
        }

        if (!errores.isEmpty()) {
            return errores;
        }

        libroRepository.saveAll(librosValidos);
        return errores; // Lista vacía = éxito
    }
}
