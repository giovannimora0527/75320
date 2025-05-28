package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Autor;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author lmora
 */
@Repository
public interface AutorRepository extends
        JpaRepository<Autor, Integer> {

    /**
     * Busca autores cuyas fechas de nacimiento se encuentren dentro del rango
     * especificado.
     *
     * @param fechaNacimientoInicial la fecha de nacimiento inicial del rango
     * (inclusive)
     * @param fechaNacimientoFin la fecha de nacimiento final del rango
     * (inclusive)
     * @return una lista de autores que nacieron en el rango de fechas
     * especificado, o lista vacía si no se encuentran resultados
     */
    List<Autor> findByFechaNacimientoBetween(LocalDate fechaNacimientoInicial,
            LocalDate fechaNacimientoFin);

    /**
     * Busca autores por su nacionalidad.
     *
     * @param nacionalidad la nacionalidad de los autores a buscar
     * @return una lista de autores con la nacionalidad especificada, o lista
     * vacía si no se encuentran resultados
     */
    List<Autor> findByNacionalidad(String nacionalidad);

    /**
     * Obtiene todos los autores ordenados por fecha de nacimiento en orden
     * ascendente.
     *
     * @return una lista de todos los autores ordenados por fecha de nacimiento
     * de más antiguo a más reciente, o lista vacía si no hay autores
     */
    List<Autor> findAllByOrderByFechaNacimientoAsc();

    /**
     * Obtiene todos los autores ordenados por fecha de nacimiento en orden
     * descendente.
     *
     * @return una lista de todos los autores ordenados por fecha de nacimiento
     * de más reciente a más antiguo, o lista vacía si no hay autores
     */
    List<Autor> findAllByOrderByFechaNacimientoDesc();

    /**
     * Verifica si existe al menos un autor con el nombre especificado.
     *
     * @param nombre el nombre del autor a verificar
     * @return true si existe al menos un autor con el nombre especificado,
     * false en caso contrario
     */
    boolean existsByNombre(String nombre);
}
