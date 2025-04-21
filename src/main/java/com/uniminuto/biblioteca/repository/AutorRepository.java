package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {
    Optional<Autor> findByNombre(String nombre);

    List<Autor> findAllByOrderByFechaNacimientoDesc();

    List<Autor> findByNacionalidad(String nacionalidad);

    List<Autor> findByNacionalidadIgnoreCase(String nacionalidad);
}
