package com.alura.literAlura.principal;

import com.alura.literAlura.model.*;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import com.alura.literAlura.service.ConsumoAPI;
import com.alura.literAlura.service.ConvierteDatos;
import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;
    private Autor autor;
    private List<Libro>libros;
    private List<Autor>autores;


    public Principal(LibroRepository repository, AutorRepository repository2) {
        this.libroRepository = repository;
        this.autorRepository = repository2;
    }

    public void muestraElMenu() {
        System.out.println("*******************************\n" +
                "Bienvenido al programa Literalura\n" +
                "Que deseas Hacer Hoy?\n" +
                "*******************************");
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    1 - Buscar Libro en la Web
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ingresa una opcion valida");
                teclado.nextLine();
                muestraElMenu();
            }


            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEntreAños();
                    break;
                case 5:
                    librosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("\nEscribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine().toLowerCase();
        var existencia = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);
        if (existencia.isPresent()) {
            System.out.println("\nYa existe en la base de datos, ingresa otro");
            return getDatosLibro();
        }else {
            var json = consumoAPI.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
            if (json.contains("title")) {
                Datos datos = conversor.obtenerDatos(json, Datos.class);
                DatosLibro datosLibro = datos.resultados().getFirst();
                return datosLibro;
            } else {
                System.out.println("\nEste libro no exite, ingresa un libro existente");
                return getDatosLibro();
            }
        }
    }

    private void buscarLibroWeb() {
        DatosLibro datosLibro = getDatosLibro();
        Libro libro = new Libro(datosLibro);
        DatosAutor datosAutor = datosLibro.autor().getFirst();
        Autor autor = new Autor(datosAutor);
        libro.setAutor(autor);
        System.out.println(libro);
        autorRepository.save(autor);
        libroRepository.save(libro);
    }

    private void listarLibrosRegistrados() {
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getId))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autores = autorRepository.findAll();
        autores.forEach(a ->
                System.out.printf("\n*************** Autor ***************\n" +
                                "nombre = %s \n" +
                                "fechaDeNacimiento = %s \n" +
                                "fechaDeFallecimiento= %s \n" +
                                "libro= %s \n" +
                                "*************************************\n",
                        a.getNombre(),a.getFechaDeNacimiento(),a.getFechaDeFallecimiento(),a.getLibro().getFirst().getTitulo()));
    }

    private void autoresVivosEntreAños() {
        System.out.println("\nIngresa el primer año (el menor de los dos): ");
        var primerFecha = teclado.nextInt();
        System.out.println("\nIngresa el segundo año (el mayor de los dos): ");
        var segundaFecha = teclado.nextInt();
        autores = autorRepository.findByFechaDeFallecimientoBetween(primerFecha,segundaFecha);
        if (!autores.isEmpty()) {
                autores.forEach(a ->
                    System.out.printf("\n*************** Autor ***************\n" +
                                    "nombre = %s \n" +
                                    "fechaDeNacimiento = %s \n" +
                                    "fechaDeFallecimiento= %s \n" +
                                    "libro= %s \n" +
                                    "*************************************\n",
                            a.getNombre(), a.getFechaDeNacimiento(), a.getFechaDeFallecimiento(), a.getLibro().getFirst().getTitulo()));
        }else {
            System.out.println("No hay autores vivos registrados en la base de datos entre esos años");
        }
    }

    private void librosPorIdioma(){
        System.out.println("\nEscribe las iniciales correspondientes al idioma que deseas buscar: \n" +
                "Idiomas admitidos:\n" +
                "es - Español\n" +
                "en - Ingles\n" +
                "fr - frances\n" +
                "pt - portugues\n");
        List<String> entrada = Collections.singletonList(teclado.nextLine().toLowerCase());
        List <Libro> librosPorLenguaje = libroRepository.findByLenguaje(entrada);
        if (!librosPorLenguaje.isEmpty()){
            librosPorLenguaje.stream()
                    .forEach(System.out::println);
        } else {
            System.out.println("No hay libros registrados en ese idioma");
        }

    }

}
