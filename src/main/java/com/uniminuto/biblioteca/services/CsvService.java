package com.uniminuto.biblioteca.services;

import java.io.InputStream;
/**
 *
 * @author Angie Vanegas Nieto
 */
public interface CsvService {
    
    public void cargarUsuariosDesdeCsv(InputStream archivoCsv);

    public void cargarLibrosDesdeCsv(InputStream archivoCsv);

    public void cargarAutoresDesdeCsv(InputStream archivoCsv);
}
