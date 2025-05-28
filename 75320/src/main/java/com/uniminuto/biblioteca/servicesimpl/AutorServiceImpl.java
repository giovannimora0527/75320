package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Nacionalidad;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.RespuestaGenericaRs;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.repository.NacionalidadRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
     * @param autorFront objeto que lllega del front.
     * @return true/false si cambia.
     */
    private boolean compararObjetosAutorActualizar(Autor autorActual, Autor autorFront) {
        return !autorActual.getNombre().equals(autorFront.getNombre())
                || !autorActual.getNacionalidad().equals(autorFront.getNacionalidad())
                || !autorActual.getFechaNacimiento().equals(autorFront.getFechaNacimiento());
    }

    @Override
public AutorRs cargarAutoresDesdeCsv(MultipartFile archivo) throws BadRequestException {
    if (archivo.isEmpty()) {
        throw new BadRequestException("El archivo CSV está vacío.");
    }

    String nombreArchivo = archivo.getOriginalFilename();
    if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".csv")) {
        throw new BadRequestException("Solo se permiten archivos CSV");
    }

    int guardados = 0;
    int duplicados = 0;
    int errores = 0;
    StringBuilder erroresDetalle = new StringBuilder();

    Pattern NOMBRE_PATTERN = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$");

    try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8))) {

        String linea;
        int fila = 1;
        boolean primeraLinea = true;

        while ((linea = reader.readLine()) != null) {
            if (primeraLinea) {
                primeraLinea = false;
                fila++;
                continue;
            }

            String[] campos = linea.split(",");
            if (campos.length < 3) {
                erroresDetalle.append("Fila ").append(fila).append(": Datos incompletos.\n");
                errores++;
                fila++;
                continue;
            }

            String nombre = campos[0].trim();
            String nacionalidadIdStr = campos[1].trim();
            String fechaNacimientoStr = campos[2].trim();

            // Validación de nombre vacío
            if (nombre.isEmpty()) {
                erroresDetalle.append("Fila ").append(fila).append(": El nombre está vacío.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de nombre solo letras
            if (!NOMBRE_PATTERN.matcher(nombre).matches()) {
                erroresDetalle.append("Fila ").append(fila).append(": El nombre solo debe contener letras y espacios.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación y conversión de nacionalidad ID
            Integer nacionalidadId;
            try {
                nacionalidadId = Integer.parseInt(nacionalidadIdStr);
            } catch (NumberFormatException e) {
                erroresDetalle.append("Fila ").append(fila).append(": ID de nacionalidad inválido.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de existencia de nacionalidad
            Optional<Nacionalidad> optNacionalidad = nacionalidadRepository.findById(nacionalidadId);
            if (!optNacionalidad.isPresent()) {
                erroresDetalle.append("Fila ").append(fila).append(": La nacionalidad con ID ")
                        .append(nacionalidadId).append(" no existe.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación y conversión de fecha de nacimiento
            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            } catch (Exception e) {
                erroresDetalle.append("Fila ").append(fila).append(": Fecha de nacimiento inválida.\n");
                errores++;
                fila++;
                continue;
            }

            // Validación de duplicados
            if (autorRepository.existsByNombre(nombre)) {
                duplicados++;
                fila++;
                continue;
            }

            // Guardar autor
            try {
                Autor autor = new Autor();
                autor.setNombre(nombre);
                autor.setNacionalidad(optNacionalidad.get());
                autor.setFechaNacimiento(fechaNacimiento);
                autorRepository.save(autor);
                guardados++;
            } catch (Exception e) {
                erroresDetalle.append("Fila ").append(fila).append(": Error al guardar en la base de datos.\n");
                errores++;
            }

            fila++;
        }

    } catch (Exception e) {
        throw new BadRequestException("Error procesando el archivo CSV: " + e.getMessage());
    }

    AutorRs respuesta = new AutorRs();
    respuesta.setStatus(200);
    respuesta.setMessage(String.format(
        "Carga completada. Autores guardados: %d, Duplicados: %d, Errores: %d\n%s",
        guardados, duplicados, errores, erroresDetalle.toString()));

    return respuesta;
}

}
