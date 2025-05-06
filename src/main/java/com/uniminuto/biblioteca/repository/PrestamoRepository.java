/**
 * @author Sofía Pedraza
 */
package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio que permite realizar operaciones CRUD sobre la entidad {@link Prestamo}.
 */
@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
}