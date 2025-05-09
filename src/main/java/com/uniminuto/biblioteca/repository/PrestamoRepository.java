package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Libro;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar los préstamos de libros.
 */
@Repository
public interface PrestamoRepository extends
        JpaRepository<Prestamo, Integer> {

    /**
     * Lista los préstamos por usuario.
     * @param usuario Usuario que hizo el préstamo.
     * @return Lista de préstamos.
     */
    List<Prestamo> findByUsuario(String usuario);

    /**
     * Lista los préstamos por estado.
     * @param estado Estado del préstamo (PRESTADO, VENCIDO, DEVUELTO).
     * @return Lista de préstamos con ese estado.
     */
    List<Prestamo> findByEstado(String estado);

    /**
     * Lista los préstamos por libro.
     * @param libro Libro asociado.
     * @return Lista de préstamos del libro dado.
     */
    List<Prestamo> findByLibro(Libro libro);

    /**
     * Lista los préstamos que deben devolverse entre dos fechas.
     * @param inicio Fecha inicial.
     * @param fin Fecha final.
     * @return Lista de préstamos.
     */
    List<Prestamo> findByFechaDevolucionBetween(LocalDate inicio, LocalDate fin);
}

