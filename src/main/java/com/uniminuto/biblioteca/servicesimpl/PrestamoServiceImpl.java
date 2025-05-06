package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.repository.PrestamoRepository;
import com.uniminuto.biblioteca.services.PrestamoService;
import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
   public List<Prestamo> listarPrestamos() {
    return prestamoRepository.findAllWithRelations();
}

    @Override
    @Transactional
    public Prestamo crearPrestamo(Prestamo prestamo) {
        LocalDate hoy = LocalDate.now();

        if (prestamo.getFechaDevolucion() == null || prestamo.getFechaDevolucion().isBefore(hoy.plusDays(1))) {
            throw new IllegalArgumentException("La fecha de devolución debe ser al menos un día después de hoy.");
        }

        prestamo.setFechaPrestamo(hoy);
        prestamo.setEstadoPrestamo(Prestamo.EstadoPrestamo.PRESTADO);
        return prestamoRepository.save(prestamo);
    }

    @Override
    @Transactional
    public Prestamo actualizarEntrega(Long idPrestamo, LocalDate fechaEntrega) {
        Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (fechaEntrega == null) {
            throw new IllegalArgumentException("Debe proporcionar una fecha de entrega.");
        }

        if (fechaEntrega.isBefore(prestamo.getFechaPrestamo())) {
            throw new IllegalArgumentException("La fecha de entrega no puede ser anterior a la fecha del préstamo.");
        }

        prestamo.setFechaEntrega(fechaEntrega);

        // Determinar estado
        if (fechaEntrega.isAfter(prestamo.getFechaDevolucion())) {
            prestamo.setEstadoPrestamo(Prestamo.EstadoPrestamo.VENCIDO);
        } else {
            prestamo.setEstadoPrestamo(Prestamo.EstadoPrestamo.DEVUELTO);
        }

        return prestamoRepository.save(prestamo);
    }
}
