package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Prestamo.EstadoPrestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de datos para la tabla prestamo.
 * @author lmora
 */
public interface PrestamoRepository extends
        JpaRepository<Prestamo, Integer> {
    List<Prestamo> findAllByOrderByFechaPrestamoDesc();
    
    List<Prestamo> findByLibro(Libro libro);
    
    List<Prestamo> findByLibroAndEstadoIn(Libro libro, List<EstadoPrestamo> estados);
}
