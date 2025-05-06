
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.enums.EstadoPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author Andres Peña
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

    // 1. Listar todos los préstamos
    
    
    // 2. IDs de usuarios disponibles para préstamo (sin préstamo activo)
    @Query("""
        SELECT u.idUsuario
        FROM Usuario u
        WHERE u.idUsuario NOT IN (
            SELECT p.usuario.idUsuario
            FROM Prestamo p
            WHERE p.estado IN ('PRESTADO', 'VENCIDO')
        )
    """)
    List<Integer> findUsuariosDisponiblesParaPrestamo();

    // 3. IDs de libros disponibles para préstamo
    @Query("""
        SELECT l.idLibro
        FROM Libro l
        WHERE l.idLibro NOT IN (
            SELECT p.libro.idLibro
            FROM Prestamo p
            WHERE p.id IN (
                SELECT MAX(p2.id)
                FROM Prestamo p2
                GROUP BY p2.libro.idLibro
            )
            AND p.estado IN ('PRESTADO', 'VENCIDO')
        )
    """)
    List<Integer> findLibrosDisponiblesParaPrestamo();
}
