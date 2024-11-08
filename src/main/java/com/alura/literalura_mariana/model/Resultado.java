package com.alura.literalura_mariana.model;

import com.alura.literalura_mariana.record.DatosLibro;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Resultado {
    private List<DatosLibro> resultados;
}