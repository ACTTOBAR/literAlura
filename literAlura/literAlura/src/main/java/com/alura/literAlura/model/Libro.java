package com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    private List<String> lenguaje;
    private double numeroDeDescargas;
    @ManyToOne
    private Autor autor;

    public Libro(){

    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.lenguaje = datosLibro.lenguaje();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(List<String> lenguaje) {
        this.lenguaje = lenguaje;
    }

    public double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n***************Libro***************\n" +
                "titulo = " + titulo  +
                "\nautor = " + autor.getNombre() +
                "\nlenguaje = " + lenguaje +
                "\nnumeroDeDescargas = " + numeroDeDescargas +
                "\n***********************************\n";
    }
}
