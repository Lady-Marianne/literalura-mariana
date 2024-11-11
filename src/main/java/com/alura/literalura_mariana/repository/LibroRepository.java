package com.alura.literalura_mariana.repository;

import com.alura.literalura_mariana.model.Lenguaje;
import com.alura.literalura_mariana.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE l.idiomas = :idiomas")
    List<Libro> findByLanguage(@Param("idiomas") Lenguaje idiomas);
}