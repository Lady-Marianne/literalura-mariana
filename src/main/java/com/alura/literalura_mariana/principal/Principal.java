package com.alura.literalura_mariana.principal;

import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);

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
                    buscarLibroPorTitulo();
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

    private void buscarLibrosPorIdioma() {
    }

    private void mostrarAutoresPorAnio() {
    }

    private void mostrarAutoresRegistrados() {
    }

    private void mostrarLibrosRegistrados() {
    }

    private void buscarLibroPorTitulo() {
    }
}
