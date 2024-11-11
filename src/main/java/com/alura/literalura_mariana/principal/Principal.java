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

import java.util.*;

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
            var menu = """
                    \n1 - Buscar y mostrar libro por título.
                    2 - Mostrar los libros registrados.
                    3 - Mostrar los autores registrados. 
                    4 - Mostrar autores vivos durante un año determinado.
                    5 - Mostrar libros por idiomas.
                    0 - Salir
                    """;
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
                    mostrarAutoresRegistrados();
                    break;
//                case 4:
//                    mostrarAutoresPorAnio();
//                    break;
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

    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el título del libro que desea buscar (o parte del mismo):");
        var tituloLibro = teclado.nextLine();
        if (tituloLibro == null || tituloLibro.trim().isEmpty()) {
            System.out.println("El título del libro no puede estar vacío.");
            return;
        }
        var json = consumirAPI.obtenerDatos(URL_BASE + "?search=" +
                tituloLibro.replace(" ", "+"));

        // Obtener el JSON bonito que me muestra todas las versiones del libro:
        var jsonBonito = conversor.obtenerJsonBonito(json);

        // Imprimir JSON formateado:
        System.out.println("\n" + jsonBonito + "\n");

        var datosBusqueda = conversor.obtenerDatos(json, DatosResultado.class);

        // Buscar el primer libro cuyo título contenga la búsqueda.
        // Además, nos fijamos si el idioma del libro se halla en el enum Lenguaje,
        // si no se halla en el enum Lenguajes, descartamos el libro:

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> {
                    // Aquí verificamos si el idioma del libro está permitido:
                    try {
                        // Verifica si el idioma es permitido:
                        Lenguaje.fromGutendex(l.idiomas().get(0));
                        return l.titulo().toUpperCase().contains(tituloLibro.toUpperCase());
                        // Si el idioma es válido, buscamos por título:
                    } catch (IllegalArgumentException e) {
                        return false;  // Si el idioma no está permitido, lo omitimos.
                    }
                })
                .findFirst();
        Libro libro = null;
        if (libroBuscado.isPresent()) {
            libro = new Libro(libroBuscado.get(), null);
            System.out.println(libro);
        } else {
            System.out.println("El libro no se ha encontrado o está en un idioma no permitido.");
        }

        // Llamamos a la función de verificación y guardado desde LibroService:
        String resultado = libroService.verificarYGuardarLibro(libro);

        // Mostrar el resultado (por ejemplo, en la consola):
        System.out.println(resultado);
    }

//    public void mostrarLibrosRegistrados() {
//        System.out.println(libroRepository.findAll());
//    }
//
//    private void mostrarAutoresRegistrados() {
//        System.out.println(autorRepository.findAll());
//    }

    public void mostrarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
        } else {
            System.out.println("Libros registrados:");
            libros.forEach(libro -> System.out.println("- " + libro));
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


    private void buscarLibrosPorIdioma() {
        System.out.println("Ingrese un idioma: ");
        var idioma = teclado.nextLine().trim();

        try {
            // Normalizamos el texto ingresado usando el método del enum:
            Lenguaje lenguaje = Lenguaje.fromEspanol(Lenguaje.normalizarTexto(idioma));
            String idiomaBaseDeDatos = lenguaje.name();
            List<Libro> librosPorIdioma = libroRepository.findByLanguage(lenguaje);
            System.out.println("Libros escritos en " + lenguaje.getLenguajeEspanol() + ":");
            librosPorIdioma.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no válido. Por favor, intente nuevamente.");
        }
    }

}

