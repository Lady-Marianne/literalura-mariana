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
    private List<Autor> autores;
    private Lenguaje idiomas;
    private Integer numeroDeDescargas;

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();

        // En caso de que haya más de un autor, vamos a "capar" esa lista
        // para que pasemos un solo autor, el primero, para ser consistentes
        // con la consigna de que un libro solamente tiene un autor:

        this.autores = datosLibro.autores().stream()
                .limit(1)
                .map(a -> new Autor(null, a.nombre(), a.fechaDeNacimiento(),
                        a.fechaDeMuerte()))
                .collect(Collectors.toList());

        // Aseguramos que se pase el primer elemento del array o lista a "fromGutendex":

        if (datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()) {
            this.idiomas = Lenguaje.fromGutendex(datosLibro.idiomas().get(0));
        }
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }
//    private Autor convertirADatosAutor(DatosAutor datosAutor, Long idAutor) {
//        return new Autor(idAutor, datosAutor.nombre(), datosAutor.fechaDeNacimiento(),
//                datosAutor.fechaDeMuerte());
//    }
    @Override
    public String toString() {
        return "Título: "+titulo+
                "\nAutor: " + (autores.isEmpty() ? "Desconocido"
                : autores.get(0).toString()) +
                "\nIdioma: "+(idiomas == null ? "Desconocido" : idiomas.getLenguajeEspanol())+
                "\nNúmero de descargas: "+numeroDeDescargas;
    }

}

