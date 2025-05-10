package com.uniminuto.biblioteca.servicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.PrestamoRs;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.repository.UsuarioRepository;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDateTime;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;
    
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

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

        if (prestamoRq.getFechaDevolucion() != null
                && !prestamoRq.getFechaDevolucion().isAfter(LocalDateTime.now())) {
            throw new BadRequestException("La fecha de devolución debe ser al menos 24 horas después de la fecha de préstamo");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setUsuario(usuario);
        prestamo.setLibro(libro);
        prestamo.setFechaPrestamo(LocalDateTime.now());
        prestamo.setFechaDevolucion(prestamoRq.getFechaDevolucion());
        prestamo.setEstado("PRESTADO");

        prestamoRepository.save(prestamo);

        PrestamoRs response = new PrestamoRs();
        response.setMessage("Préstamo guardado correctamente");

        return response;
    }


    @Override
    public Prestamo actualizarPrestamo(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long id) {
        Optional<Prestamo> optional = prestamoRepository.findById(id);
        return optional.orElse(null);
    }
}
