package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

/**
 * Entidad que representa un préstamo de libro en la biblioteca.
 */
@Data
@Entity
@Table(name = "prestamos")
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del préstamo (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Integer idPrestamo;

    /**
     * Usuario que realiza el préstamo.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    /**
     * Libro prestado (relación ManyToOne con entidad Libro).
     */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

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
