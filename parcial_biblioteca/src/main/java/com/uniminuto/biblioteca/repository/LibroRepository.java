package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Libro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {
    
    Optional<Libro> findByTitulo(String titulo); // Búsqueda caso-sensitiva
    
    // Nuevo método para filtrar libros por rango de años
    List<Libro> findByAnioPublicacionBetween(Integer inicio, Integer fin);
    
     List<Libro> findByAutorId(Integer autorId);
    
}