package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.CsvService;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CsvServiceImpl implements CsvService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void cargarUsuariosDesdeCsv(InputStream archivoCsv) {
        try (
            InputStreamReader reader = new InputStreamReader(archivoCsv, StandardCharsets.UTF_8);
            CSVParser parser = CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true).parse(reader)
        ) {
            List<Usuario> usuarios = new ArrayList<>();

            for (CSVRecord record : parser) {
                String nombre = record.get("nombre").trim();
                String correo = record.get("correo").trim();
                String telefono = record.get("telefono").trim();

                // Validación para evitar duplicados por correo
                if (!usuarioRepository.existsByCorreo(correo)) {
                    Usuario usuario = new Usuario();
                    usuario.setNombre(nombre);
                    usuario.setCorreo(correo);
                    usuario.setTelefono(telefono);
                    usuario.setFechaRegistro(LocalDateTime.now());
                    usuario.setActivo(true);
                    usuarios.add(usuario);
                }
            }

            usuarioRepository.saveAll(usuarios);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error procesando archivo CSV de usuarios: " + e.getMessage(), e);
        }
    }

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public void cargarLibrosDesdeCsv(InputStream archivoCsv) {
    try (CSVParser parser = CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreEmptyLines()
            .withTrim()
            .parse(new InputStreamReader(archivoCsv, StandardCharsets.UTF_8))) {

        List<Libro> libros = new ArrayList<>();

        for (CSVRecord record : parser) {
    String titulo = record.get("Título del libro").trim();

    if (libroRepository.existsByTitulo(titulo)) {
        continue;
    }

    // Obtener el autor por nombre
    String nombreAutor = record.get("Autor").trim();
    Optional<Autor> autorOptional = autorRepository.findByNombre(nombreAutor);

    if (autorOptional.isEmpty()) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
            "No se encontró el autor con nombre: " + nombreAutor);
    }

    Libro libro = new Libro();
    libro.setTitulo(titulo);
    libro.setAnioPublicacion(Integer.parseInt(record.get("Año de publicación del libro").trim()));
    libro.setCategoria(record.get("Categoría del libro").trim());
    libro.setExistencias(Integer.parseInt(record.get("Existencias").trim()));

    // Aquí se asigna el autor obtenido de la base de datos
    libro.setAutor(autorOptional.get());

    libros.add(libro);
}

        libroRepository.saveAll(libros);

    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error procesando archivo CSV de libros: " + e.getMessage(), e);
    }
}

    @Autowired
    private AutorRepository autorRepository;

    @Override
    public void cargarAutoresDesdeCsv(InputStream archivoCsv) {
        try (CSVParser parser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreEmptyLines()
                .withTrim()
                .parse(new InputStreamReader(archivoCsv, StandardCharsets.UTF_8))) {

            List<Autor> autores = new ArrayList<>();

            for (CSVRecord record : parser) {
                String nombre = record.get("Nombre").trim();
                String nacionalidad = record.get("Nacionalidad").trim();
                String fechaNacimientoStr = record.get("Fecha de nacimiento").trim();

                if (autorRepository.existsByNombre(nombre)) {
                    continue;
                }

                Autor autor = new Autor();
                autor.setNombre(nombre);
                autor.setNacionalidad(nacionalidad);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                autor.setFechaNacimiento(LocalDate.parse(fechaNacimientoStr, formatter));
                
                autor.setActivo(true);

                autores.add(autor);
            }

            autorRepository.saveAll(autores);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error procesando archivo CSV de autores: " + e.getMessage(), e);
        }
    }
}