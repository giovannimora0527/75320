package com.uniminuto.biblioteca.servicesImpl;

import com.uniminuto.biblioteca.entity.EstadoPrestamo;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.repository.LibroRepository;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;
    
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Prestamo> obtenerListadoDePrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Optional<Prestamo> obtenerPorId(Integer id)throws BadRequestException {
        Optional<Prestamo> prestamoOpt = prestamoRepository.findById(id);
        if (!prestamoOpt.isPresent()) {
            throw new BadRequestException("No se encontró el préstamo con el id: " + id);
        }
        return prestamoOpt;
    }

    @Override
    public RespuestaGenerica guardarPrestamo(Prestamo prestamo) throws BadRequestException {
       if (prestamo.getIdPrestamo() == null) {
        throw new BadRequestException("El ID del préstamo es obligatorio para editar.");
    }

    Prestamo existente = prestamoRepository.findById(prestamo.getIdPrestamo())
        .orElseThrow(() -> new BadRequestException("No se encontró el préstamo para actualizar."));

    if (prestamo.getFechaEntrega() == null) {
        throw new BadRequestException("La fecha de entrega es obligatoria.");
    }

    existente.setFechaEntrega(prestamo.getFechaEntrega());

    if (prestamo.getFechaEntrega().isAfter(existente.getFechaDevolucion())) {
        existente.setEstado(EstadoPrestamo.VENCIDO);
    } else {
        existente.setEstado(EstadoPrestamo.DEVUELTO);
    }

    // Actualizar stock del libro
    Libro libro = existente.getLibro();
    libro.setExistencias(libro.getExistencias() + 1);
    libroRepository.save(libro);

    prestamoRepository.save(existente);

    RespuestaGenerica respuesta = new RespuestaGenerica();
    respuesta.setMessage("Préstamo actualizado exitosamente.");
    return respuesta;
}

    @Override
    public Prestamo crearPrestamo(Prestamo prestamo) throws BadRequestException {
        if (prestamo.getLibro() == null || prestamo.getUsuario() == null) {
        throw new BadRequestException("El libro y el usuario son obligatorios.");
    }

    if (prestamo.getFechaDevolucion() == null) {
        throw new BadRequestException("La fecha de devolución es obligatoria.");
    }

    // Obtener libro y validar disponibilidad
    Libro libro = libroRepository.findById(prestamo.getLibro().getIdLibro())
        .orElseThrow(() -> new BadRequestException("No se encontró el libro."));

    if (libro.getExistencias() <= 0) {
    throw new BadRequestException("No hay unidades disponibles para prestar.");
}

    prestamo.setFechaPrestamo(LocalDateTime.now());
    prestamo.setEstado(EstadoPrestamo.PRESTADO);
    
    libro.setExistencias(libro.getExistencias() - 1);
    libroRepository.save(libro);

    return prestamoRepository.save(prestamo);
}
    
}
