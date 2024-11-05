package com.alura.literalura_mariana.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToMany(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores;
    @ElementCollection(targetClass = Lenguaje.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "idiomas")
    private Lenguaje idiomas;
    private Integer numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autores = datosLibro.autores().stream()
                .map(a -> new Autor(a.nombre(), a.fechaDeNacimiento(), a.fechaDeMuerte()))
                .toList();
        this.idiomas = datosLibro.idiomas().stream()
                .map(i -> Lenguaje::fromEspanol)
                .toArray(Lenguaje[]::new);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return  "Título: " + titulo +
                " - Autores: " + autores +
                " - Idiomas disponibles: " + idiomas +
                " - Número de descargas: " + numeroDeDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public Lenguaje getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(Lenguaje idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }
}
