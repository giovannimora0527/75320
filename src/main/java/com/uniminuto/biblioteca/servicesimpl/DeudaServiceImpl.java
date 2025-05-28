package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Deuda;
import com.uniminuto.biblioteca.entity.Usuario;
import com.uniminuto.biblioteca.repository.DeudaRepository;
import com.uniminuto.biblioteca.services.DeudaService;
import com.uniminuto.biblioteca.services.UsuarioService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de la lógica de negocio para el módulo de deudas.
 * 
 * @author lmora
 */
@Service
public class DeudaServiceImpl implements DeudaService {

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public List<Deuda> listarDeudas() throws BadRequestException {
        return this.deudaRepository.findAll();
    }

    @Override
    public Deuda obtenerDeudaPorId(Integer deudaId) throws BadRequestException {
        Optional<Deuda> optDeuda = this.deudaRepository.findById(deudaId);
        if (!optDeuda.isPresent()) {
            throw new BadRequestException("No se encuentra la deuda con el id = " + deudaId);
        }
        return optDeuda.get();
    }

    @Override
    public List<Deuda> obtenerDeudasPorUsuario(Integer usuarioId) throws BadRequestException {
        if (usuarioId == null) {
            throw new BadRequestException("El id del usuario no puede ser vacío.");
        }

        Usuario usuario = this.usuarioService.obtenerUsuarioId(usuarioId);
        if (usuario == null) {
            throw new BadRequestException("El usuario con el id ingresado no existe.");
        }

        List<Deuda> deudasUsuario = this.deudaRepository.findByUsuario(usuario);
        return !deudasUsuario.isEmpty() ? deudasUsuario : Collections.emptyList();
    }

    @Override
    public Deuda crearDeuda(Deuda deuda) throws BadRequestException {
        if (deuda == null) {
            throw new BadRequestException("Los datos de la deuda no pueden ser nulos.");
        }

        deuda.setPagada(false); // Por defecto, la deuda se marca como no pagada
        return this.deudaRepository.save(deuda);
    }

    @Override
    public Deuda actualizarDeuda(Integer deudaId, Deuda deuda) throws BadRequestException {
        if (deuda == null || deuda.getIdDeuda() == null) {
            throw new BadRequestException("Debe especificarse el ID de la deuda a actualizar.");
        }

        Deuda existente = obtenerDeudaPorId((Integer) deuda.getIdDeuda());

        existente.setUsuario(deuda.getUsuario());
        existente.setLibro(deuda.getLibro());
        existente.setFechaInicio(deuda.getFechaInicio());
        existente.setFechaFin(deuda.getFechaFin());
        // No se actualiza pagada ni tipoPago desde aquí

        return this.deudaRepository.save(existente);
    }

    @Override
    public Deuda pagarDeuda(Integer deudaId, String tipoPago) throws BadRequestException {
        if (tipoPago == null || tipoPago.isBlank()) {
            throw new BadRequestException("Debe especificar el tipo de pago.");
        }

        Deuda deuda = obtenerDeudaPorId(deudaId);

        if (deuda.isPagada()) {
            throw new BadRequestException("La deuda ya ha sido pagada.");
        }

        deuda.setPagada(true);
        deuda.setTipoPago(tipoPago);

        return this.deudaRepository.save(deuda);
    }


}
