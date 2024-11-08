package com.alura.literalura_mariana.model;

import com.alura.literalura_mariana.record.DatosAutor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Autor {
    Long idAutor;
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte = datosAutor.fechaDeMuerte();
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre+
                "\nFecha de nacimiento: "+fechaDeNacimiento+
                "\nFecha de muerte: "+fechaDeMuerte;
    }
}


