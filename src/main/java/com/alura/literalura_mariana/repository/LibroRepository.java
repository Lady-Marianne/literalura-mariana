package com.alura.literalura_mariana.repository;

import com.alura.literalura_mariana.model.DatosLibro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<DatosLibro,Long> {

}
