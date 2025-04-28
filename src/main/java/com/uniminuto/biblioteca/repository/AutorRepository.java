package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;             // ← aqui
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {

    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial,
                                            LocalDate fechaNacimientoFin);

    List<Autor> findByNacionalidad(String nacionalidad);

    List<Autor> findAllByOrderByFechaNacimientoAsc();

    List<Autor> findAllByOrderByFechaNacimientoDesc();

    // Nuevo: validar nombre único
    boolean existsByNombre(String nombre);

    // Nuevo: buscar un autor por nombre
    Optional<Autor> findByNombre(String nombre);
}

