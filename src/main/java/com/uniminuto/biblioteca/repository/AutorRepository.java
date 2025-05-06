package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sofía Pedraza
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer> {

    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial,
            LocalDate fechaNacimientoFin);

    List<Autor> findByNacionalidad(String nacionalidad);

    List<Autor> findAllByOrderByFechaNacimientoAsc();

    List<Autor> findAllByOrderByFechaNacimientoDesc();

    Optional<Autor> findByNombre(String nombre);

    boolean existsByNombre(String nombre);
}