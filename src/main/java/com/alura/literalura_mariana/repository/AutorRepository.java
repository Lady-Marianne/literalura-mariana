package com.alura.literalura_mariana.repository;

import com.alura.literalura_mariana.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE " +
            "(CAST(a.fechaDeNacimiento AS int) <= :anio AND (CAST(a.fechaDeMuerte AS int)" +
            ">= :anio OR a.fechaMuerte IS NULL))")
    List<Autor> findByYear(@Param("anio") int anio);

}