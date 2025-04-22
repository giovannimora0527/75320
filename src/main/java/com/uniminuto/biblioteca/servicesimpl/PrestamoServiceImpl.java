package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import com.uniminuto.biblioteca.utils.DateFormatterService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lmora
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DateFormatterService dateFormatterService;

    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        return this.prestamoRepository.findAllByOrderByFechaPrestamoDesc();
    }

    @Override
    @Transactional
    public RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) throws BadRequestException {
        Optional<Libro> optLibro = this.libroRepository.findById(prestamoRq.getLibroId());
        if (!optLibro.isPresent()) {
            throw new BadRequestException("No existe el libro");
        }

        Optional<Usuario> optUser = this.usuarioRepository.findById(prestamoRq.getUsuarioId());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el usuario");
        }

        Libro libroPrestar = optLibro.get();
        List<Prestamo> prestamosActivos = prestamoRepository.findByLibroAndEstadoIn(libroPrestar, 
        List.of(Prestamo.EstadoPrestamo.PRESTADO, Prestamo.EstadoPrestamo.VENCIDO));
        if (prestamosActivos.size() >= libroPrestar.getExistencias()) {
            throw new BadRequestException("No se puede prestar el libro " 
                    + libroPrestar.getTitulo() 
                    + ", porque no hay existencias en stock");
        }

        Prestamo prestamoNuevo = new Prestamo();
        prestamoNuevo.setEstado(Prestamo.EstadoPrestamo.PRESTADO);
        LocalDate fecha = dateFormatterService.parseStringToLocalDate(prestamoRq.getFechaDevolucion(), "yyyy-MM-dd");
        LocalTime horaActual = LocalTime.now(); // hora del sistema
        LocalDateTime fechaDevolucion = fecha.atTime(horaActual);
        prestamoNuevo.setFechaDevolucion(fechaDevolucion);
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime minimoFechaDevolucion = ahora.plusHours(24);

        if (prestamoNuevo.getFechaDevolucion().isBefore(minimoFechaDevolucion)) {
            throw new BadRequestException("La fecha de devolución debe ser al menos 1 día después de la fecha actual.");
        }

        prestamoNuevo.setFechaPrestamo(LocalDateTime.now());
        prestamoNuevo.setLibro(libroPrestar);
        prestamoNuevo.setUsuario(optUser.get());
        this.prestamoRepository.save(prestamoNuevo);
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setStatus(200);
        rta.setMessage("Se ha registrado el prestamo satisfactoriamente");
        return rta;
    }

}
