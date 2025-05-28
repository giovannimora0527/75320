package com.uniminuto.biblioteca.services;

import com.uniminuto.biblioteca.entity.Deuda;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Interfaz que define los servicios para gestionar deudas.
 * 
 * Permite listar, consultar, registrar, actualizar y pagar deudas.
 * 
 * @author lmora
 */
public interface DeudaService {

    /**
     * Lista todas las deudas registradas.
     * 
     * @return Lista de deudas registradas.
     * @throws BadRequestException Excepción en caso de error.
     */
    List<Deuda> listarDeudas() throws BadRequestException;

    /**
     * Obtiene una deuda dado su ID.
     * 
     * @param deudaId Id de la deuda.
     * @return Deuda correspondiente.
     * @throws BadRequestException Excepción si no se encuentra o es inválido.
     */
    Deuda obtenerDeudaPorId(Integer deudaId) throws BadRequestException;

    /**
     * Registra una nueva deuda.
     * 
     * @param deuda Objeto deuda a registrar.
     * @return Deuda registrada.
     * @throws BadRequestException Excepción si los datos son inválidos.
     */
    Deuda crearDeuda(Deuda deuda) throws BadRequestException;

    /**
     * Actualiza una deuda existente.
     * 
     * @param deudaId Id de la deuda a actualizar.
     * @param deuda Datos actualizados.
     * @return Deuda modificada.
     * @throws BadRequestException Excepción si ocurre un error.
     */
    Deuda actualizarDeuda(Integer deudaId, Deuda deuda) throws BadRequestException;

    /**
     * Marca una deuda como pagada, registrando el tipo de pago.
     * 
     * @param deudaId Id de la deuda.
     * @param tipoPago Tipo de pago (Efectivo, Nequi, Débito).
     * @return Deuda actualizada como pagada.
     * @throws BadRequestException Excepción si no se puede pagar.
     */
    Deuda pagarDeuda(Integer deudaId, String tipoPago) throws BadRequestException;
    
    /**
 * Obtiene todas las deudas asociadas a un usuario específico.
 * 
 * @param usuarioId Id del usuario.
 * @return Lista de deudas del usuario.
 * @throws BadRequestException Excepción si el ID es inválido o no se encuentran deudas.
 */
List<Deuda> obtenerDeudasPorUsuario(Integer usuarioId) throws BadRequestException;



}
