package com.alura.literalura_mariana.principal;

import com.alura.literalura_mariana.model.Datos;
import com.alura.literalura_mariana.model.DatosLibro;
import com.alura.literalura_mariana.service.ConsumirAPI;
import com.alura.literalura_mariana.service.ConvertirDatos;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);
    ConsumirAPI consumirAPI = new ConsumirAPI();
    ConvertirDatos conversor = new ConvertirDatos();
//    private LibroRepository repositorio;

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n1 - Buscar y mostrar libro por título.
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
                    buscarLibroPorTitulo();
                    break;
//                case 2:
//                    mostrarLibrosRegistrados();
//                    break;
//                case 3:
//                    mostrarAutoresRegistrados();
//                    break;
//                case 4:
//                    mostrarAutoresPorAnio();
//                    break;
//                case 5:
//                    buscarLibrosPorIdioma();
//                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el título del libro que desea buscar (o parte del mismo):");
        var tituloLibro = teclado.nextLine();
        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));

        // Obtener el JSON bonito que me muestra todas las versiones del libro:

        var jsonBonito = conversor.obtenerJsonBonito(json);

        // Imprimir JSON formateado:

        System.out.println(jsonBonito);

        // Aquí muestro la primera versión del libro buscado, que suele ser la que está
        // en su idioma original:

        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado: " + libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado.");
        }
        //repositorio.save(datosLibro);

    }

//    private void buscarLibrosPorIdioma() {
//    }
//
//    private void mostrarAutoresPorAnio() {
//    }
//
//    private void mostrarAutoresRegistrados() {
//    }
//
//    private void mostrarLibrosRegistrados() {
//    }


}
