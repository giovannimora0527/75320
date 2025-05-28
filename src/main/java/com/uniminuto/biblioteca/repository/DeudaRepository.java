package com.uniminuto.biblioteca.repository;

import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.entity.Usuario;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD sobre la entidad Deuda.
 * 
 * Permite consultar deudas por usuario, estado de pago, y rango de fechas.
 * 
 * @author lmora
 */
@Repository
public interface DeudaRepository extends JpaRepository<Deuda, Integer> {

    /**
     * Obtiene la lista de deudas asociadas a un usuario.
     * 
     * @param usuario Nombre del usuario.
     * @return Lista de deudas del usuario.
     */
    List<Deuda> findByUsuario(String usuario);

    /**
     * Obtiene la lista de deudas pagadas o no pagadas.
     * 
     * @param pagada Estado de pago (true para pagadas, false para pendientes).
     * @return Lista de deudas según el estado de pago.
     */
    List<Deuda> findByPagada(boolean pagada);

    /**
     * Obtiene deudas entre un rango de fechas de inicio.
     * 
     * @param fechaInicioInicio Fecha inicial del rango.
     * @param fechaInicioFin Fecha final del rango.
     * @return Lista de deudas en el rango de fechas.
     */
    List<Deuda> findByFechaInicioBetween(LocalDate fechaInicioInicio, LocalDate fechaInicioFin);

    public List<Deuda> findByUsuario(Usuario usuario);
}

