package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.LibroDisponibleProjection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Libro. Esta interfaz extiende JpaRepository para
 * proporcionar operaciones CRUD básicas para la entidad Libro. Además, incluye
 * métodos personalizados para realizar búsquedas específicas basadas en ciertos
 * criterios.
 *
 * @author Sofía Pedraza
 */
@Repository
public interface LibroRepository extends
        JpaRepository<Libro, Integer> {

    /**
     * Obtiene la lista dado un autor.
     *
     * @param autor Autor a buscar.
     * @return Lista de libros.
     */
    List<Libro> findByAutor(Autor autor);

    /**
     * Busca un libro por su nombre.
     *
     * @param nombreLibro Nombre del libro a buscar.
     * @return Libro.
     */
    Libro findByTitulo(String nombreLibro);

    /**
     * Lista los libros por rango de fecha de publicacion.
     *
     * @param anioIni Año inicial.
     * @param anioFin Año final.
     * @return Lista de libros que cumplen el criterio.
     */
    List<Libro> findByAnioPublicacionBetween(Integer anioIni, Integer anioFin);

    @Query(value = """
       SELECT 
                l.id_libro AS idLibro,
                l.titulo AS titulo,
                l.existencias AS existencias,
                
                a.id_autor AS idAutor,
                a.nombre AS nombreAutor,
                a.nacionalidad AS nacionalidad,
                a.fecha_nacimiento AS fechaNacimiento,
                
                c.categoria_id AS categoriaId,
                c.nombre AS nombreCategoria,
                
                COALESCE(COUNT(p.id_prestamo), 0) AS prestados,
                (l.existencias - COALESCE(COUNT(p.id_prestamo), 0)) AS disponibles
            FROM 
                libros l
            INNER JOIN 
                autores a ON l.id_autor = a.id_autor
            INNER JOIN 
                categoria c ON l.categoria_id = c.categoria_id
            LEFT JOIN 
                prestamos p ON l.id_libro = p.id_libro
                AND (p.estado = 'PRESTADO' OR p.estado = 'VENCIDO')
            GROUP BY 
                l.id_libro, l.titulo, l.existencias,
                a.id_autor, a.nombre, a.nacionalidad, a.fecha_nacimiento,
                c.categoria_id, c.nombre
            HAVING 
                COALESCE(COUNT(p.id_prestamo), 0) < l.existencias
        """, nativeQuery = true)
    List<LibroDisponibleProjection> findLibrosDisponibles();

}