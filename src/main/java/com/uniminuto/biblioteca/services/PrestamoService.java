package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Prestamo;
import com.uniminuto.biblioteca.model.PrestamoRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 *
 * @author lmora
 */
public interface PrestamoService {
    
    List<Prestamo> listarPrestamos() throws BadRequestException;
    
    RespuestaGenericaRs crearPrestamo(PrestamoRq prestamoRq) 
            throws BadRequestException;
}
