package com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String nombre;
    private int fechaDeNacimiento;
    private int fechaDeFallecimiento;

    @OneToMany (mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libro;

    public Autor(){

    }

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeFallecimiento();
    }

    @Override
    public String toString() {
        return "\n*************** Autor ***************" +
                "\nnombre =" + nombre  +
                "\nfechaDeNacimiento = " + fechaDeNacimiento +
                "\nfechaDeFallecimiento = " + fechaDeFallecimiento +
                "\nlibro= " + libro +
                "\n*************************************\n";
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(int fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(int fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }

    public List<Libro> getLibro() {return libro;}

    public void setLibro(List<Libro> libro) {
        libro.forEach(e -> e.setAutor(this));
        this.libro = libro;}
}
