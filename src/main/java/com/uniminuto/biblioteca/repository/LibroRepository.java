package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
    List<Libro> findByAutor_AutorId(Integer idAutor);
    Libro findByTitulo (String titulo);
    List<Libro> findByAnioPublicacionBetween(Integer fechaInicio, Integer fechaFin);

}
