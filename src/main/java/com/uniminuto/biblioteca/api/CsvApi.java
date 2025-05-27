package com.uniminuto.biblioteca.api;

import java.io.IOException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * API para el cargue masivo de datos desde archivos CSV.
 * 
 * @author Angie Vanegas
 */
@RequestMapping("/csv")
public interface CsvApi {

    @PostMapping(value = "/usuarios", consumes = "multipart/form-data")
    ResponseEntity<String> cargarUsuariosDesdeCsv(@RequestParam("archivo") MultipartFile archivo)
            throws BadRequestException, IOException;

    @PostMapping(value = "/autores", consumes = "multipart/form-data")
    ResponseEntity<String> cargarAutoresDesdeCsv(@RequestParam("archivo") MultipartFile archivo)
            throws BadRequestException, IOException;

    @PostMapping(value = "/libros", consumes = "multipart/form-data")
    ResponseEntity<String> cargarLibrosDesdeCsv(@RequestParam("archivo") MultipartFile archivo)
            throws BadRequestException, IOException;
}
