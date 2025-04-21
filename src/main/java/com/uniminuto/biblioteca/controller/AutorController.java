package com.uniminuto.biblioteca.controller;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/biblioteca/v1/autor")
@CrossOrigin(origins = "*")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> listarAutores() {
        List<Autor> autores = autorService.listarAutores();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @PostMapping("/guardar-autor")
    public ResponseEntity<?> crearAutor(@RequestBody Autor autor) {
        try {
            Autor nuevoAutor = autorService.crearAutor(autor);
            return new ResponseEntity<>(nuevoAutor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizar-autor")
    public ResponseEntity<?> actualizarAutor(@RequestBody Autor autor) {
        try {
            Autor autorActualizado = autorService.actualizarAutor(autor);
            return new ResponseEntity<>(autorActualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // Inner class for error response
    static class ErrorResponse {
        private String message;

        public ErrorResponse() {
        }

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
