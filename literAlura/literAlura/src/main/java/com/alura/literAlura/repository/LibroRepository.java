package com.alura.literAlura.repository;

import com.alura.literAlura.model.Libro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long>  {

    Optional<Libro> findByTituloContainsIgnoreCase (String titulo);

    List<Libro> findByLenguaje (List<String> idioma);
}
