package com.alura.literalura_mariana.record;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultado(
        @JsonAlias("results") List<DatosLibro> resultados
) {
}
