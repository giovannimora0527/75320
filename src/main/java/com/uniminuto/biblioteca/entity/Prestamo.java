package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa un préstamo de un libro por un usuario.
 */
@Entity
@Table(name = "prestamos")
@Data
public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Identificador único del préstamo.
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
     * Libro que ha sido prestado.
     */
    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    /**
     * Fecha y hora del préstamo.
     */
    @Column(name = "fecha_prestamo")
    private LocalDateTime fechaPrestamo;

    /**
     * Fecha límite de devolución.
     */
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDateTime fechaDevolucion;
    
    
    /**
     * Fecha límite de devolución.
     */
    @Column(name = "fecha_entrega", nullable = false)
    private LocalDateTime fechaEntrega;

    /**
     * Estado actual del préstamo.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPrestamo estado = EstadoPrestamo.PRESTADO;

    /**
     * Enum que representa los posibles estados del préstamo.
     */
    public enum EstadoPrestamo {
        PRESTADO,
        DEVUELTO,
        VENCIDO
    }
}