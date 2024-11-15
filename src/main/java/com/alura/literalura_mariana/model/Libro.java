package com.alura.literalura_mariana.model;

import com.alura.literalura_mariana.record.DatosAutor;
import com.alura.literalura_mariana.record.DatosLibro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;

    @Enumerated(EnumType.STRING)
    private Lenguaje idiomas;

    private Integer numeroDeDescargas;

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();

        // En caso de que haya más de un autor, vamos a "capar" esa lista
        // para que pasemos un solo autor, el primero, para ser consistentes
        // con la consigna de que un libro solamente tiene un autor:

        List<DatosAutor> datosAutores = datosLibro.autores().stream()
                .limit(1).collect(Collectors.toList());
        if(!datosAutores.isEmpty()) {
            DatosAutor datosAutor = datosAutores.get(0);
            this.autor = new Autor(datosAutor);
        }
        // Aseguramos que se pase el primer elemento del array o lista a "fromGutendex":
        if (datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()) {
            this.idiomas = Lenguaje.fromGutendex(datosLibro.idiomas().get(0));
        }
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

//    @Override
//    public String toString() {
//        return "\nTítulo: "+titulo+
//                "\nAutor: " + (autor!= null ? autor.getNombre() : "Desconocido")+
//                "\nIdioma: "+(idiomas == null ? "Desconocido" : idiomas.getLenguajeEspanol())+
//                "\nNúmero de descargas: "+numeroDeDescargas;
//    }

    @Override
    public String toString() {
        return """
            --------------------------
            📚 Título: %s
            ✍️  Autor: %s
            🌍 Idioma: %s
            🔢 Número de descargas: %d
            --------------------------
            """.formatted(
                titulo,
                autor != null ? autor.getNombre() : "Desconocido",
                idiomas == null ? "Desconocido" : idiomas.getLenguajeEspanol(),
                numeroDeDescargas != null ? numeroDeDescargas : 0
        );
    }


}

