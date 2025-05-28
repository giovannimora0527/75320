package com.uniminuto.biblioteca.servicesimpl;

import com.uniminuto.biblioteca.entity.Autor;
import com.uniminuto.biblioteca.entity.Libro;
import com.uniminuto.biblioteca.model.AutorRq;
import com.uniminuto.biblioteca.model.AutorRs;
import com.uniminuto.biblioteca.model.CargaMasivaError;
import com.uniminuto.biblioteca.model.RespuestaGenerica;
import com.uniminuto.biblioteca.repository.AutorRepository;
import com.uniminuto.biblioteca.services.AutorService;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    
    @Override
    public List<AutorRs> listarAutores() throws BadRequestException {
        return autorRepository.findAll().stream().map(autor -> {
            AutorRs dto = new AutorRs();
            dto.setNombre(autor.getNombre());
            dto.setFechaNacimiento(autor.getFechaNacimiento());
            dto.setNacionalidad(autor.getNacionalidad());

            List<Libro> libros = autor.getLibros();
            dto.setNumeroLibros(libros.size());
            dto.setTitulosLibros(libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.toList()));

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public RespuestaGenerica guardarAutor(AutorRq autor) throws BadRequestException {
        if (autorRepository.findByNombre(autor.getNombre()).isPresent()) {
            throw new BadRequestException("Ya existe un autor con el nombre: " + autor.getNombre());
        }

        Autor nuevo = new Autor();
        nuevo.setNombre(autor.getNombre());
        nuevo.setFechaNacimiento(autor.getFechaNacimiento());
        nuevo.setNacionalidad(autor.getNacionalidad());

        autorRepository.save(nuevo);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Autor creado exitosamente.");
        return rta;
    }

    @Override
    public RespuestaGenerica actualizarAutor(AutorRq autor) throws BadRequestException {
        Optional<Autor> autorActualOpt = autorRepository.findByNombre(autor.getNombreOriginal());

        if (!autorActualOpt.isPresent()) {
            throw new BadRequestException("No se encontró un autor con el nombre original: " + autor.getNombreOriginal());
        }

        Autor autorActual = autorActualOpt.get();

        // Validar si el nuevo nombre ya existe y no es el mismo autor
        if (!autor.getNombre().equals(autor.getNombreOriginal())) {
            Optional<Autor> autorConMismoNombre = autorRepository.findByNombre(autor.getNombre());
            if (autorConMismoNombre.isPresent()) {
                throw new BadRequestException("Ya existe otro autor con el nombre: " + autor.getNombre());
            }
        }

        autorActual.setNombre(autor.getNombre());
        autorActual.setFechaNacimiento(autor.getFechaNacimiento());
        autorActual.setNacionalidad(autor.getNacionalidad());

        autorRepository.save(autorActual);

        RespuestaGenerica rta = new RespuestaGenerica();
        rta.setMessage("Autor actualizado correctamente.");
        return rta;
    }
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
            throw new BadRequestException("No se encuentra el autor con el id " + autorId);
        }
        return optAutor.get();
    }
    
    @Override
    public List<CargaMasivaError> cargarAutoresDesdeCsv(MultipartFile file) {
        List<CargaMasivaError> errores = new ArrayList<>();
        List<Autor> autoresValidos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String linea;
            int numeroLinea = 0;

            // Saltar encabezado
            reader.readLine();
            numeroLinea++;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                String[] campos = linea.split(",", -1);

                String nombre = campos.length > 0 ? campos[0].trim().replace("\"", "") : "";
                String fechaNacimientoStr = campos.length > 1 ? campos[1].trim().replace("\"", "") : "";
                String nacionalidad = campos.length > 2 ? campos[2].trim().replace("\"", "") : "";

                boolean tieneError = false;

                if (nombre.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo nombre es obligatorio (línea " + numeroLinea + ")"));
                    tieneError = true;
                }

                if (fechaNacimientoStr.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo fecha de nacimiento es obligatorio (línea " + numeroLinea + ")"));
                    tieneError = true;
                }

                if (nacionalidad.isEmpty()) {
                    errores.add(new CargaMasivaError(numeroLinea, "El campo nacionalidad es obligatorio (línea " + numeroLinea + ")"));
                    tieneError = true;
                }

                if (tieneError) continue;
                
                if (autorRepository.existsByNombre(nombre)) {
                    errores.add(new CargaMasivaError(numeroLinea, "Ya existe un autor con el nombre '" + nombre + "'"));
                    continue;
                }

                try {
                    LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);

                    Autor autor = new Autor();
                    autor.setNombre(nombre);
                    autor.setFechaNacimiento(fechaNacimiento);
                    autor.setNacionalidad(nacionalidad);

                    autoresValidos.add(autor);

                } catch (Exception ex) {
                    errores.add(new CargaMasivaError(numeroLinea, "Formato incorrecto en fecha de nacimiento (línea " + numeroLinea + ")"));
                }
            }

        } catch (Exception e) {
            errores.add(new CargaMasivaError(0, "Error al leer el archivo: " + e.getMessage()));
        }

        if (!errores.isEmpty()) {
            return errores;
        }

        autorRepository.saveAll(autoresValidos);
        return errores;
    }


}
