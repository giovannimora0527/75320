package com.uniminuto.biblioteca.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa una deuda asociada a un usuario y un libro.
 * 
 * @author
 */
@Data
@Entity
@Table(name = "deudas")
public class Deuda implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único de la deuda (clave primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_deuda")
    private Integer idDeuda;

    /** Nombre del usuario asociado a la deuda. */
    @Column(name = "usuario", nullable = false, length = 100)
    private String usuario;

    /** Nombre del libro asociado a la deuda. */
    @Column(name = "libro", nullable = false, length = 200)
    private String libro;

    /** Fecha de inicio de la deuda. */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    /** Fecha límite o final de la deuda. */
    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    /** Indica si la deuda ya ha sido pagada. */
    @Column(name = "pagada", nullable = false)
    private boolean pagada;

    /** Tipo de pago utilizado para saldar la deuda (Efectivo, Nequi, Débito, etc). */
    @Column(name = "tipo_pago", length = 50)
    private String tipoPago;

    public void setPagada(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Object getIdDeuda() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
