package com.alura.literalura_mariana.model;

import com.alura.literalura_mariana.record.DatosAutor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAutor;

    private String nombre;
    private Integer fechaDeNacimiento;
    private Integer fechaDeMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
    }

//    @Override
//    public String toString() {
//        return  "\nNombre: "+nombre+
//                "\nFecha de nacimiento: "+fechaDeNacimiento+
//                "\nFecha de muerte: "+fechaDeMuerte;
//    }

    @Override
    public String toString() {
        return """
            --------------------------
            👤 Nombre: %s
            🗓️  Fecha de nacimiento: %s
            🕯️  Fecha de muerte: %s
            --------------------------
            """.formatted(
                nombre,
                fechaDeNacimiento != null ? fechaDeNacimiento : "Desconocida",
                fechaDeMuerte != null ? fechaDeMuerte : "Desconocida"
        );
    }

}


