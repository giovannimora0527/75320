package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    @Query("SELECT p FROM Prestamo p JOIN FETCH p.usuario JOIN FETCH p.libro")
    List<Prestamo> findAllWithRelations();
    List<Prestamo> findByEstadoPrestamo(Prestamo.EstadoPrestamo estado);
    List<Prestamo> findByIdLibroAndEstadoPrestamo( Long idLibro, Prestamo.EstadoPrestamo estado);
}
