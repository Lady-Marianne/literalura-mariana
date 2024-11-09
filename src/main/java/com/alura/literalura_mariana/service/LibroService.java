package com.alura.literalura_mariana.service;

import com.alura.literalura_mariana.model.Autor;
import com.alura.literalura_mariana.model.Libro;
import com.alura.literalura_mariana.record.DatosLibro;
import com.alura.literalura_mariana.repository.AutorRepository;
import com.alura.literalura_mariana.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private final AutorRepository autorRepository;
    @Autowired
    private final LibroRepository libroRepository;

    @Autowired
    public LibroService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional
    public String verificarYGuardarLibro(Libro libro) {
        // 1. Verificar si el libro ya existe por su título
        Optional<Libro> libroExistente = libroRepository.findByTitulo(libro.getTitulo());

        if (libroExistente.isPresent()) {
            return "El libro ya está en la base de datos.";
        }

        // 2. Verificar si el autor ya existe por su nombre:
        try {
            Optional<Autor> autorExistente = autorRepository.findByNombre(libro.getAutor().getNombre());

            Autor autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                // Si el autor no existe, se guarda uno nuevo
                autor = libro.getAutor();
                autorRepository.save(autor);
            }
            libro.setAutor(autor);
            // 3. Guardar el nuevo libro con el autor ya verificado:
            libroRepository.save(libro);

            return "Libro y autor guardados correctamente.";
        } catch (DataAccessException e) {
            e.printStackTrace();
            return "Error al guardar el libro: " + e.getMessage();
        }
    }
}
