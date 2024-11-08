package com.alura.literalura_mariana.model;

import com.alura.literalura_mariana.record.DatosAutor;
import com.alura.literalura_mariana.record.DatosLibro;
import com.alura.literalura_mariana.model.Autor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Libro {
    private Long idLibro;
    private String titulo;
    private List<DatosAutor> autores;
    private List<String> idiomas;
    private Integer numeroDeDescargas;

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        // En caso de que haya más de un autor, vamos a "capar" esa lista
        // para que pasemos un solo autor, el primero, para ser consistentes
        // con la consigna de que un libro solamente tiene un autor:
        this.autores = datosLibro.autores().stream()
                .limit(1)
                .collect(Collectors.toList());
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }
    private Autor convertirADatosAutor(DatosAutor datosAutor, Long idAutor) {
        return new Autor(idAutor, datosAutor.nombre(), datosAutor.fechaDeNacimiento(),
                datosAutor.fechaDeMuerte());
    }
    @Override
    public String toString() {
        return "Título: "+titulo+
                "\nAutor: " + (autores.isEmpty() ? "Desconocido"
                : convertirADatosAutor(autores.get(0), 1L).toString()) +
                "\nIdioma: "+idiomas+
                "\nNúmero de descargas: "+numeroDeDescargas;
    }

}

