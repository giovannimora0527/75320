
package com.uniminuto.biblioteca.model;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;

/**
 *
 * @author Andres Peña
 */
@Data

public class AutorRs {
    private String nombre;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private int numeroLibros;
    private List<String> titulosLibros;
    // constructor, getters y setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setNumeroLibros(int numeroLibros) {
        this.numeroLibros = numeroLibros;
    }

    public void setTitulosLibros(List<String> titulosLibros) {
        this.titulosLibros = titulosLibros;
    }
}
