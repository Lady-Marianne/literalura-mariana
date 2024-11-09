package com.alura.literalura_mariana.service;

import com.alura.literalura_mariana.model.Autor;
import com.alura.literalura_mariana.model.Libro;
import com.alura.literalura_mariana.record.DatosLibro;
import com.alura.literalura_mariana.repository.AutorRepository;
import com.alura.literalura_mariana.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibroService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    @Autowired
    public LibroService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public Libro buscarYGuardarLibro(DatosLibro datosLibro) {
        // Buscar si el libro ya existe en la base de datos:
        Optional<Libro> libroExistente = libroRepository.findByTitulo(datosLibro.titulo());
        if (libroExistente.isPresent()) {
            return null;  // El libro ya existe.
        }

        // Crear una nueva instancia de Autor usando los datos proporcionados:
        Autor autor = new Autor(datosLibro.autores().get(0));

// Comprobar si el autor ya existe en la base de datos:
        Optional<Autor> autorExistente = autorRepository.findByNombre(datosLibro.autores().get(0).nombre());

        if (autorExistente.isPresent()) {
            // Si el autor existe, usamos el autor existente:
            autor = autorExistente.get();
        } else {
            // Si el autor no existe, creamos uno nuevo y lo guardamos:
            autor = new Autor(datosLibro.autores().get(0));
            autorRepository.save(autor);  // Guardar el nuevo autor en la base de datos.
        }


        // Crear una nueva instancia de Libro usando los datos proporcionados y el autor existente o recién creado:
        Libro libroNuevo = new Libro(datosLibro, autor);

        // Guardar el libro en la base de datos
        return libroRepository.save(libroNuevo);
    }
}
