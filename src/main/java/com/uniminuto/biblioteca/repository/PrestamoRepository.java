
package com.uniminuto.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Angie Vanegas Nieto
 */
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
@Query("SELECT COUNT(p) FROM Prestamo p WHERE p.libro.id = :libroId AND (p.estado = 'PRESTADO')")
int contarPrestamosPorLibro(@Param("libroId") Integer libroId);   
}
