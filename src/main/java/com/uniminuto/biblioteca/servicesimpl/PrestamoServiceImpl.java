
package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.enums.EstadoPrestamo;
import com.uniminuto.biblioteca.model.LibroDisponibleDto;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoEntregaRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.model.UsuarioDisponibleDto;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.services.PrestamoService;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Andres Peña
 */

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private LibroRepository libroRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public List<PrestamoRs> listarTodo() {
        return prestamoRepository.findAll().stream().map(p -> {
            PrestamoRs rs = new PrestamoRs();
            rs.setId(p.getId());
            rs.setNombreUsuario(p.getUsuario().getNombre());
            rs.setTituloLibro(p.getLibro().getTitulo());
            rs.setFechaPrestamo(p.getFechaPrestamo().format(dateTimeFormatter));
            rs.setFechaDevolucion(p.getFechaDevolucion().format(dateFormatter));
            rs.setFechaEntrega(p.getFechaEntrega() != null
                    ? p.getFechaEntrega().format(dateFormatter)
                    : "No entregado");
            rs.setEstado(p.getEstado().name());
            return rs;
        }).collect(Collectors.toList());
    }

    
    @Override
    public RespuestaGenerica guardarPrestamo(PrestamoRq rq) throws BadRequestException {
        Optional<Usuario> usuario = usuarioRepository.findById(rq.getUsuarioId());
        Optional<Libro> libro = libroRepository.findById(rq.getLibroId());

        if (usuario.isEmpty() || libro.isEmpty()) {
            throw new BadRequestException("Usuario o libro no válido.");
        }

        LocalDateTime ahora = LocalDateTime.now();
        if (!rq.getFechaDevolucion().isAfter(ahora.toLocalDate())) {
            throw new BadRequestException("La fecha de devolución debe ser por lo menos un día después de la fecha de préstamo.");
        }

        Prestamo nuevo = new Prestamo();
        nuevo.setUsuario(usuario.get());
        nuevo.setLibro(libro.get());
        nuevo.setFechaPrestamo(ahora);
        nuevo.setFechaDevolucion(rq.getFechaDevolucion());
        nuevo.setEstado(EstadoPrestamo.PRESTADO);

        prestamoRepository.save(nuevo);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Préstamo registrado correctamente.");
        return rta;
    }

    @Override
    public RespuestaGenerica actualizarEntrega(PrestamoEntregaRq rq) throws BadRequestException {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(rq.getPrestamoId());

        if (prestamoOpt.isEmpty()) {
            throw new BadRequestException("Préstamo no encontrado.");
        }

        Prestamo prestamo = prestamoOpt.get();
        LocalDate entrega = rq.getFechaEntrega();
        LocalDate devolucion = prestamo.getFechaDevolucion();

        if (entrega.isBefore(devolucion)) {
            throw new BadRequestException("Debe devolver el libro el día estimado para la devolución, no antes.");
        }

        prestamo.setFechaEntrega(entrega);
        prestamo.setEstado(
                entrega.isAfter(devolucion) ? EstadoPrestamo.VENCIDO : EstadoPrestamo.DEVUELTO
        );

        prestamoRepository.save(prestamo);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Entrega registrada correctamente.");
        return rta;
    }
    
    @Override
    public List<UsuarioDisponibleDto> obtenerUsuariosDisponiblesParaPrestamo() throws BadRequestException {
        List<Integer> ids = prestamoRepository.findUsuariosDisponiblesParaPrestamo();

        List<Usuario> usuarios = usuarioRepository.findAllById(ids);

        return usuarios.stream()
            .map(u -> new UsuarioDisponibleDto(u.getIdUsuario(), u.getNombre()))
            .collect(Collectors.toList());
    }

    @Override
    public List<LibroDisponibleDto> obtenerLibrosDisponiblesParaPrestamo() throws BadRequestException {
        List<Integer> ids = prestamoRepository.findLibrosDisponiblesParaPrestamo();

        List<Libro> libros = libroRepository.findAllById(ids);

        return libros.stream()
            .map(l -> new LibroDisponibleDto(l.getIdLibro(), l.getTitulo()))
            .collect(Collectors.toList());
    }
}
