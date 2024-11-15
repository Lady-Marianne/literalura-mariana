package com.alura.literalura_mariana.principal;

import com.alura.literalura_mariana.model.Autor;
import com.alura.literalura_mariana.model.Lenguaje;
import com.alura.literalura_mariana.model.Libro;
import com.alura.literalura_mariana.record.DatosLibro;
import com.alura.literalura_mariana.record.DatosResultado;
import com.alura.literalura_mariana.repository.AutorRepository;
import com.alura.literalura_mariana.repository.LibroRepository;
import com.alura.literalura_mariana.service.ConsumirAPI;
import com.alura.literalura_mariana.service.ConvertirDatos;
import com.alura.literalura_mariana.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.alura.literalura_mariana.model.Lenguaje.normalizarTexto;

@Component
public class Principal {

    private static final String URL_BASE = "https://gutendex.com/books/";
    private Scanner teclado = new Scanner(System.in);

    private final ConsumirAPI consumirAPI;
    private final ConvertirDatos conversor;
    private final LibroService libroService;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    @Autowired
    public Principal(ConsumirAPI consumirAPI, ConvertirDatos conversor, LibroService libroService,
                     LibroRepository libroRepository, AutorRepository autorRepository) {
        this.consumirAPI = consumirAPI;
        this.conversor = conversor;
        this.libroService = libroService;
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
//            var menu = """
//                    \n1 - Buscar y mostrar libro por título.
//                    2 - Mostrar los libros registrados y sus estadísticas.
//                    3 - Mostrar el Top 10 de los libros más descargados.
//                    4 - Mostrar libros por idiomas.
//                    5 - Mostrar los autores registrados.
//                    6 - Mostrar autores vivos durante un año determinado.
//                    7 - Mostrar libros por autor.
//                    0 - Salir
//                    """;
            var bienvenida = """
                   *****************************************************
                   *   ¡BIENVENID@S A LITERALURA!                      *
                   *   La biblioteca virtual donde los libros cobran   *
                   *   vida.                                           *
                   *****************************************************
                   """;

            var menu = """
             ┌──────────────────────────────────────────────┐
             │                 MENÚ PRINCIPAL               │
             ├──────────────────────────────────────────────┤
             │ 1 - Buscar y mostrar libro por título.       │
             │ 2 - Mostrar los libros registrados y sus     │
             │     estadísticas.                            │
             │ 3 - Mostrar el Top 10 de los libros más      │
             │     descargados.                             │
             │ 4 - Mostrar libros por idiomas.              │
             │ 5 - Mostrar los autores registrados.         │
             │ 6 - Mostrar autores vivos durante un año     │
             │     determinado.                             │
             │ 7 - Mostrar libros por autor.                │
             │ 0 - Salir                                    │
             └──────────────────────────────────────────────┘
            """;
            System.out.println(bienvenida);
            System.out.println(menu);

            try {
                opcion = teclado.nextInt();
                teclado.nextLine();  // Limpiamos el buffer.
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                teclado.nextLine();  // Limpiamos el buffer de entrada para evitar el loop infinito.
                opcion = -1;         // Restablecemos "opcion" para que el menú se muestre de nuevo.
                continue;            // Salta al siguiente ciclo sin ejecutar el resto del código en el "while".
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarTop10LibrosMasDescargados();
                    break;
                case 4:
                    buscarLibrosPorIdioma();
                    break;
                case 5:
                    mostrarAutoresRegistrados();
                    break;
                case 6:
                    mostrarAutoresPorAnio();
                    break;
                case 7:
                    mostrarLibrosPorAutor();
                    break;
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
        if (tituloLibro == null || tituloLibro.trim().isEmpty()) {
            System.out.println("El título del libro no puede estar vacío.");
            return;
        }
        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" +
                tituloLibro.replace(" ", "+"));

        // Hacer búsqueda por libro y autor:
//        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" +
//                tituloLibro.replace(" ", "+") + "%20"
//               + nombreAutor.replace(" ","+"));

        // Obtener el JSON bonito que me muestra todas las versiones del libro:
        var jsonBonito = conversor.obtenerJsonBonito(json);

        // Imprimir JSON formateado (se usa más por razones de prueba y depuración.
        // Se puede descomentar:
//        System.out.println("\n" + jsonBonito + "\n");

        var datosBusqueda = conversor.obtenerDatos(json, DatosResultado.class);

        // Buscar el primer libro cuyo título contenga la búsqueda.
        // Antes, nos aseguramos que el libro esté en un idioma "permitido",
        // o sea, de los que figuran en nuestro enum:
        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> {
                    try {
                        // Verificar que la lista de idiomas no esté vacía y procesar el primer idioma.
                        // Aclaración: La mayoría de los libros vienen en un solo idioma, con excepción de
                        // algunos diccionarios:
                        if (!l.idiomas().isEmpty()) {
                            Lenguaje lenguaje = Lenguaje.fromGutendex(l.idiomas().get(0));
                            return l.titulo().toUpperCase().contains((tituloLibro.toUpperCase()));
                        } else {
                            System.out.println("Idioma no especificado para el libro: " + l.titulo());
                            return false;
                        }
                    } catch (IllegalArgumentException e) {
                        return false;
                    }
                })
                .findFirst();
        Libro libro = null;
        if (libroBuscado.isPresent()) {
            libro = new Libro(libroBuscado.get(), null);
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado o el libro está en un idioma no permitido.");
        }

        // Llamamos a la función de verificación y guardado desde LibroService:
        String resultado = libroService.verificarYGuardarLibro(libro);

        // Mostrar el resultado en la consola:
        System.out.println(resultado);
    }

    public void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("Libros registrados:");
            libros.forEach(libro -> System.out.println("- " + libro));
        }

        // Calcular las estadísticas de las descargas:
        IntSummaryStatistics estadisticas = libros.stream()
                .mapToInt(Libro::getNumeroDeDescargas)  // Extrae el número de descargas.
                .summaryStatistics();  // Obtiene estadísticas de los descargas.

        // Mostrar las estadísticas:
        System.out.println("\nEstadísticas de las descargas:");
        System.out.println("Promedio de descargas: " + String.format("%.2f",estadisticas.getAverage()));
        System.out.println("Número máximo de descargas: " + estadisticas.getMax());
        System.out.println("Número mínimo de descargas: " + estadisticas.getMin());
        System.out.println("Total de descargas: " + estadisticas.getSum());
    }

    private void mostrarTop10LibrosMasDescargados() {
        List<Libro> top10Libros = libroRepository.findAll().stream()
                .sorted((l1, l2) -> l2.getNumeroDeDescargas().compareTo(l1.getNumeroDeDescargas()))
                .limit(10)
                .collect(Collectors.toList());
        // Mostrar el top 10 en la consola:
        System.out.println("Top 10 de los libros más descargados:");
        top10Libros.forEach(System.out::println);
    }

    private void buscarLibrosPorIdioma() {
        System.out.println("Ingrese un idioma: ");
        var idioma = teclado.nextLine().trim();

        try {
            // Normalizamos el texto ingresado usando el método del enum:
            Lenguaje lenguaje = Lenguaje.fromEspanol(normalizarTexto(idioma));
            String idiomaBaseDeDatos = lenguaje.name();
            List<Libro> librosPorIdioma = libroRepository.findByLanguage(lenguaje);
            System.out.println("Libros escritos en " + lenguaje.getLenguajeEspanol() + ":");
            librosPorIdioma.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no válido. Por favor, intente nuevamente.");
        }
    }

    private void mostrarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
        } else {
            System.out.println("Autores registrados:");
            autores.forEach(autor -> System.out.println("- " + autor));
        }
    }

    private void mostrarAutoresPorAnio() {
        try {
            System.out.println("Ingrese un año: ");
            var anio = teclado.nextInt();
            if (anio > LocalDate.now().getYear()) {
                System.out.println("Año inválido. Por favor, ingrese un año válido.");
                return;
            }

            // Obtener los autores vivos en el año especificado:
            List<Autor> autoresVivos = autorRepository.findByYear(anio);

            // Mostrar los resultados:
            if (autoresVivos.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio + ".");
            } else {
                System.out.println("Autores vivos en el año " + anio + ":");
                autoresVivos.forEach(autor -> System.out.println(autor));
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Por favor, ingrese un número entero válido para el año.");
            // Limpiar el buffer de entrada para evitar un loop infinito:
            teclado.nextLine();
        }
    }

        private void mostrarLibrosPorAutor() {
        System.out.println("Ingrese el nombre de un autor: ");
        var nombreAutor = teclado.nextLine();
            if (nombreAutor == null || nombreAutor.trim().isEmpty()) {
                System.out.println("El nombre del autor no puede estar vacío.");
                return;
            }
            List<Libro> libros = libroRepository.findByName(nombreAutor);
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros de: " + nombreAutor);
            } else {
//                System.out.println("Libros de " + libros.get(0).getAutor().getNombre() + ":");
//                libros.forEach(libro -> System.out.println("Título: " + libro.getTitulo() +
//                        "\nDescargas: " + libro.getNumeroDeDescargas()));
                libros.forEach(libro -> System.out.println("- " + libro));

            }
        }
}

