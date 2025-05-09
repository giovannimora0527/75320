package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.*;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.repository.UsuarioRepository;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Prestamo> listarPrestamos() throws BadRequestException {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo obtenerPrestamoPorId(Integer idPrestamo) throws BadRequestException {
        Optional<Prestamo> opt = prestamoRepository.findById(idPrestamo);
        if (!opt.isPresent()) {
            throw new BadRequestException("No existe el préstamo con id = " + idPrestamo);
        }
        return opt.get();
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorUsuario(String usuario) throws BadRequestException {
        if (usuario == null || usuario.isBlank()) {
            throw new BadRequestException("El nombre del usuario no puede estar vacío.");
        }
        return prestamoRepository.findByUsuario(usuario);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorEstado(String estado) throws BadRequestException {
        if (estado == null || estado.isBlank()) {
            throw new BadRequestException("El estado no puede estar vacío.");
        }
        return prestamoRepository.findByEstado(estado);
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorLibro(Integer idLibro) throws BadRequestException {
        Optional<Libro> libro = libroRepository.findById(idLibro);
        if (!libro.isPresent()) {
            throw new BadRequestException("El libro con id " + idLibro + " no existe.");
        }
        return prestamoRepository.findByLibro(libro.get());
    }

    @Override
    public List<Prestamo> obtenerPrestamosPorRangoFecha(LocalDate inicio, LocalDate fin) throws BadRequestException {
        if (inicio == null || fin == null || fin.isBefore(inicio)) {
            throw new BadRequestException("Las fechas son inválidas.");
        }
        return prestamoRepository.findByFechaDevolucionBetween(inicio, fin);
    }

    @Override
    public RespuestaGenerica crearPrestamo(PrestamoRq prestamo) {
        Optional<Usuario> optUser = this.usuarioRepository.findById(prestamo.getUsuarioId());
        // Validacion si el usuario no existe
        
        Optional<Libro> optLibro = this.libroRepository.findById(prestamo.getLibroId());
        
        // Validacion del libro no existe
        
        // Ver disponibilidad del libro para prestar.
        
        // si cumple las condiciones
        // Creo el registro de un nuevo prestamo
        Prestamo nuevo = new Prestamo();
        nuevo.setEstado("PRESTADO");
        nuevo.setFechaDevolucion(prestamo.getFechaDevolucion());
        nuevo.setFechaPrestamo(LocalDateTime.now());
        nuevo.setLibro(optLibro.get());
        nuevo.setUsuario(optUser.get());
        
        this.prestamoRepository.save(nuevo);
        
        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Se ha registrado el prestamo correctamente");
        return rta;
    }
}
