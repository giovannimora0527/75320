package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.util.List;
import java.util.Optional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import javax.transaction.Transactional;

/**
 *
 * @author lmora
 */
@Service
public class AutorServiceImpl implements AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NacionalidadRepository nacionalidadRepository;

    @Override
    public List<Autor> obtenerListadoAutores() {
        return this.autorRepository.findAllByOrderByFechaNacimientoDesc();
    }

    @Override
    public List<Autor> obtenerListadoAutoresPorNacionalidad(String nacionalidad)
            throws BadRequestException {
        this.autorRepository.findByNacionalidad(nacionalidad).forEach(elem -> {
            System.out.println("Nombre Autor => " + elem.getNombre());
        });
        List<Autor> listaAutores = this.autorRepository.findByNacionalidad(nacionalidad);
        if (listaAutores.isEmpty()) {
            throw new BadRequestException("No existen autores con esa nacionalidad.");
        }

        return listaAutores;
    }

    @Override
    public Autor obtenerAutorPorId(Integer autorId) throws BadRequestException {
        Optional<Autor> optAutor = this.autorRepository.findById(autorId);
        if (!optAutor.isPresent()) {
            throw new BadRequestException("No se encuentra el autor con el id "
                    + autorId);
        }
        return optAutor.get();
    }

    @Override
    public RespuestaGenericaRs crearAutor(AutorRq autorRq) throws BadRequestException {
        if (this.autorRepository.existsByNombre(autorRq.getNombre())) {
            throw new BadRequestException("El Autor se encuentra ya registrado");
        }

        Autor autorGuardar = this.convertirAutorRqToAutor(autorRq);
        this.autorRepository.save(autorGuardar);
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha guardado el Autor satisfactoriamente");
        return rta;
    }

    /**
     * Convierte un objeto rq a un entity.
     * 
     * @param autorRq dato de entrada.
     * @return entity autor.
     * @throws BadRequestException excepcion.
     */
    private Autor convertirAutorRqToAutor(AutorRq autorRq)
            throws BadRequestException {
        Autor autor = new Autor();
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        Optional<Nacionalidad> optCat = this.nacionalidadRepository
                .findById(autorRq.getNacionalidadId());
        if (!optCat.isPresent()) {
            throw new BadRequestException("No existe la Nacionalidad");
        }
        Nacionalidad nacionalidad = optCat.get();
        autor.setFechaNacimiento(autorRq.getFechaNacimiento());
        autor.setNombre(autorRq.getNombre());
        autor.setNacionalidad(nacionalidad);
        return autor;
    }

    @Override
    public RespuestaGenericaRs actualizarAutor(Autor autor)
            throws BadRequestException {
        Optional<Autor> optUser = this.autorRepository
                .findById(autor.getAutorId());
        if (!optUser.isPresent()) {
            throw new BadRequestException("No existe el Autor.");
        }

        Autor autorActual = optUser.get();
        RespuestaGenericaRs rta = new RespuestaGenericaRs();
        rta.setMessage("Se ha actualizado el registro satisfactoriamente");
        if (!compararObjetosAutorActualizar(autorActual, autor)) {
            return rta;
        }

        if (!autorActual.getNombre().equals(autor.getNombre())) {
            // El nombre cambio
            if (this.autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException("El autor "
                        + autor.getNombre()
                        + ", existe en la bd. por favor verifique.");
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setNacionalidad(autor.getNacionalidad());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        this.autorRepository.save(autorActual);
        return rta;
    }

    /**
     * Compara si un objeto autor cambia.
     * 
     * @param autorActual objeto actual.
     * @param autorFront  objeto que lllega del front.
     * @return true/false si cambia.
     */
    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
                || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad())
                || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento());
    }

    @Override
    @Transactional
    public List<Autor> guardarAutoresMasivo(List<AutorRq> autores) throws BadRequestException {
        if (autores == null || autores.isEmpty()) {
            throw new BadRequestException("La lista de autores no puede estar vacía");
        }

        // Validar duplicados en el archivo de entrada
        validarDuplicadosEnArchivo(autores);

        // Validar duplicados con la BD
        validarDuplicadosEnBD(autores);

        // Si llegamos aquí, no hay duplicados, procedemos a guardar
        List<Autor> autoresGuardados = new ArrayList<>();
        int fila = 1;

        for (AutorRq autorRq : autores) {
            try {
                // Validar campos requeridos
                if (autorRq.getNombre() == null || autorRq.getNombre().trim().isEmpty()) {
                    throw new BadRequestException("El nombre en la fila " + fila + " es requerido");
                }
                if (autorRq.getNacionalidadId() == null) {
                    throw new BadRequestException("La nacionalidad en la fila " + fila + " es requerida");
                }
                if (autorRq.getFechaNacimiento() == null) {
                    throw new BadRequestException("La fecha de nacimiento en la fila " + fila + " es requerida");
                }

                // Validar que la nacionalidad existe
                Optional<Nacionalidad> optNacionalidad = nacionalidadRepository.findById(autorRq.getNacionalidadId());
                if (!optNacionalidad.isPresent()) {
                    throw new BadRequestException("La nacionalidad con ID " + autorRq.getNacionalidadId()
                            + " en la fila " + fila + " no existe");
                }

                // Validar que la fecha de nacimiento no sea futura
                if (autorRq.getFechaNacimiento().isAfter(java.time.LocalDate.now())) {
                    throw new BadRequestException("La fecha de nacimiento en la fila " + fila
                            + " no puede ser futura");
                }

                // Convertir y guardar autor
                Autor autor = convertirAutorRqToAutor(autorRq);
                autoresGuardados.add(autorRepository.save(autor));

            } catch (BadRequestException e) {
                throw new BadRequestException("Error en la fila " + fila + ": " + e.getMessage());
            }
            fila++;
        }

        return autoresGuardados;
    }

    /**
     * Valida que no existan duplicados en el archivo de entrada.
     * 
     * @param autores Lista de autores a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnArchivo(List<AutorRq> autores) throws BadRequestException {
        // Validar nombres duplicados en el archivo
        for (int i = 0; i < autores.size(); i++) {
            for (int j = i + 1; j < autores.size(); j++) {
                AutorRq autor1 = autores.get(i);
                AutorRq autor2 = autores.get(j);

                if (autor1.getNombre().equalsIgnoreCase(autor2.getNombre())) {
                    throw new BadRequestException(
                            String.format("El nombre '%s' está duplicado en las filas %d y %d",
                                    autor1.getNombre(), i + 1, j + 1));
                }
            }
        }
    }

    /**
     * Valida que no existan duplicados con registros en la base de datos.
     * 
     * @param autores Lista de autores a validar
     * @throws BadRequestException si se encuentran duplicados
     */
    private void validarDuplicadosEnBD(List<AutorRq> autores) throws BadRequestException {
        for (int i = 0; i < autores.size(); i++) {
            AutorRq autor = autores.get(i);

            if (autorRepository.existsByNombre(autor.getNombre())) {
                throw new BadRequestException(
                        String.format("El nombre '%s' en la fila %d ya existe en la base de datos",
                                autor.getNombre(), i + 1));
            }
        }
    }
}