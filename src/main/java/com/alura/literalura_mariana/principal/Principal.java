package com.alura.literalura_mariana.principal;

import com.alura.literalura_mariana.model.DatosLibro;
import com.alura.literalura_mariana.model.Libro;
import com.alura.literalura_mariana.repository.LibroRepository;
import com.alura.literalura_mariana.service.ConsumirAPI;
import com.alura.literalura_mariana.service.ConvertirDatos;

import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);
    ConsumirAPI consumirAPI = new ConsumirAPI();
    ConvertirDatos conversor = new ConvertirDatos();
    private LibroRepository repositorio;

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Buscar libro por título.
                    2 - Mostrar los libros registrados.
                    3 - Mostrar los autores registrados. 
                    4 - Mostrar autores vivos durante un año determinado.
                    5 - Mostrar libros por idiomas.
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroEnAPI();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresPorAnio();
                    break;
                case 5:
                    buscarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    private DatosLibro buscarLibroPorTitulo() {
        System.out.println("Escriba el título del libro que desea buscar (o parte del mismo):");
        var tituloLibro = teclado.nextLine();
        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        System.out.println(json);
        DatosLibro datosLibro = conversor.obtenerDatos(json, DatosLibro.class);
        return datosLibro;
    }

    private void buscarLibroEnAPI() {
        DatosLibro datosLibro = buscarLibroPorTitulo();
        Libro libro = new Libro(datosLibro);
        repositorio.save(libro);
        System.out.println(datosLibro);
    }

    private void buscarLibrosPorIdioma() {
    }

    private void mostrarAutoresPorAnio() {
    }

    private void mostrarAutoresRegistrados() {
    }

    private void mostrarLibrosRegistrados() {
    }



}
