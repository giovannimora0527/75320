package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
<<<<<<< HEAD
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

/**
 * Entidad que representa un préstamo de libro en la biblioteca.
 */
=======
import javax.persistence.*;
import lombok.Data;

>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {
<<<<<<< HEAD

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del préstamo (clave primaria).
     */
=======
    private static final long serialVersionUID = 1L;

>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

<<<<<<< HEAD
    /**
     * Usuario que realiza el préstamo.
     */
=======
>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

<<<<<<< HEAD
    /**
     * Libro prestado (relación ManyToOne con entidad Libro).
     */
=======
>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

<<<<<<< HEAD
    /**
     * Fecha en la que se realiza el préstamo.
     */
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    /**
     * Fecha límite para la devolución del libro.
     */
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDateTime fechaDevolucion;

    /**
     * Estado actual del préstamo: PRESTADO, VENCIDO, DEVUELTO.
     */
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    /**
     * Fecha en la que se entregó el libro (si ya fue devuelto).
     */
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
}
=======
    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado;
    
    public enum EstadoPrestamo {
        PRESTADO,
        DEVUELTO,
        VENCIDO
    }

    @Column(name = "fecha_entrega", nullable = true)
    private LocalDate fechaEntrega;
}
>>>>>>> 5c56f8d446d5a59b870431403c8c05c636d466fd
