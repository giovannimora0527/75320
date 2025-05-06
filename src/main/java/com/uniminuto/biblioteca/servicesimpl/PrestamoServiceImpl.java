/**
 *
 * @author Sofía Pedraza
 */
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que implementa las operaciones para gestionar préstamos de libros.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    /**
     * Lista todos los préstamos registrados en la base de datos.
     *
     * @return lista de préstamos
     * @throws BadRequestException si ocurre un error durante la consulta
     */
    @Override
    public List<Prestamo> listarTodos() throws BadRequestException {
        return prestamoRepository.findAll();
    }

    /**
     * Registra un nuevo préstamo en el sistema.
     *
     * @param prestamoRq datos de la solicitud de préstamo
     * @return respuesta indicando el resultado o error de la operación
     * @throws BadRequestException si el usuario o el libro no existen, o si las
     * fechas no son válidas
     */
    @Override
    public PrestamoRs guardarPrestamoNuevo(PrestamoRq prestamoRq) throws BadRequestException {
        Usuario usuario = usuarioRepository.findById(prestamoRq.getIdUsuario())
                .orElseThrow(() -> new BadRequestException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(prestamoRq.getIdLibro())
                .orElseThrow(() -> new BadRequestException("Libro no encontrado"));

        boolean disponible = libroRepository.findLibrosDisponibles().stream()
                .anyMatch(libroDisponible
                        -> prestamoRq.getIdLibro().intValue() == libroDisponible.getIdLibro()
                );

        if (!disponible) {
            throw new BadRequestException("El libro no tiene ejemplares disponibles para préstamo");
        }

        LocalDate fechaPrestamo = LocalDate.now();

        if (prestamoRq.getFechaDevolucion() != null
                && !prestamoRq.getFechaDevolucion().isAfter(fechaPrestamo)) {
            throw new BadRequestException("La fecha de devolución debe ser al menos 24 horas después de la fecha de préstamo");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setEstado(Prestamo.EstadoPrestamo.PRESTADO);

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo guardado correctamente");

        return response;
    }

    /**
     * Actualiza la información de un préstamo existente, incluyendo su fecha de
     * entrega y estado final.
     *
     * @param prestamo entidad de préstamo con los datos actualizados
     * @return respuesta indicando el resultado de la operación
     * @throws BadRequestException si el préstamo no existe o si las fechas no
     * son válidas
     */
    @Override
    public PrestamoRs actualizarPrestamo(Prestamo prestamo) throws BadRequestException {
        Optional<Prestamo> prestamoExistenteOpt = prestamoRepository.findById(prestamo.getIdPrestamo());
        if (!prestamoExistenteOpt.isPresent()) {
            throw new BadRequestException("Préstamo no encontrado");
        }

        Prestamo prestamoExistente = prestamoExistenteOpt.get();

        if (prestamo.getFechaEntrega() != null
                && prestamo.getFechaEntrega().isBefore(prestamoExistente.getFechaPrestamo())) {
            throw new BadRequestException("La fecha de entrega no puede ser antes de la fecha de préstamo");
        }

        if (prestamoExistente.getFechaDevolucion() == null) {
            throw new BadRequestException("El préstamo no tiene una fecha de devolución registrada");
        }

        if (prestamo.getFechaEntrega() != null) {
            prestamoExistente.setFechaEntrega(prestamo.getFechaEntrega());

            if (prestamo.getFechaEntrega().isAfter(prestamoExistente.getFechaDevolucion())) {
                prestamoExistente.setEstado(Prestamo.EstadoPrestamo.VENCIDO);
            } else {
                prestamoExistente.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
            }
        }

        prestamoRepository.save(prestamoExistente);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo actualizado correctamente");

        return response;
    }
}