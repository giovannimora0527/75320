package com.uniminuto.biblioteca.apicontroller;

import com.uniminuto.biblioteca.api.CsvApi;
import com.uniminuto.biblioteca.services.CsvService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CsvApiController implements CsvApi {

    @Autowired
    private CsvService csvService;

    @Override
    public ResponseEntity<String> cargarUsuariosDesdeCsv(MultipartFile archivo) throws IOException {
        try {
            csvService.cargarUsuariosDesdeCsv(archivo.getInputStream());
            return ResponseEntity.ok("Usuarios cargados correctamente.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al cargar usuarios desde CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<String> cargarAutoresDesdeCsv(MultipartFile archivo) throws IOException {
        try {
            csvService.cargarAutoresDesdeCsv(archivo.getInputStream());
            return ResponseEntity.ok("Autores cargados correctamente.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al cargar autores desde CSV: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<String> cargarLibrosDesdeCsv(MultipartFile archivo) throws IOException {
        try {
            csvService.cargarLibrosDesdeCsv(archivo.getInputStream());
            return ResponseEntity.ok("Libros cargados correctamente.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error al cargar libros desde CSV: " + e.getMessage(), e);
        }
    }
}
