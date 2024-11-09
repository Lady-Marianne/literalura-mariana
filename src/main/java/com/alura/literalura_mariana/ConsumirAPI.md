```java
package com.alura.literalura_mariana.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumirAPI {
    public String obtenerDatos(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("La URL no puede ser null o estar vacía.");
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de estado HTTP:
            if (response.statusCode() != 200) {
                System.out.println("Error: Código de estado HTTP " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }

        return response.body();
    }
}
```

```java
private void buscarLibroPorTitulo() {
    System.out.println("Escriba el título del libro que desea buscar (o parte del mismo):");
    var tituloLibro = teclado.nextLine();
    if (tituloLibro == null || tituloLibro.trim().isEmpty()) {
        System.out.println("El título del libro no puede estar vacío.");
        return;
    }
    
    var url = URL_BASE + "?search=" + tituloLibro.replace(" ", "+");
    var json = consumirAPI.obtenerDatos(url);

    if (json == null) {
        System.out.println("No se pudieron obtener datos de la API.");
        return;
    }

    var jsonBonito = conversor.obtenerJsonBonito(json);
    var datosBusqueda = conversor.obtenerDatos(json, DatosResultado.class);

    Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
            .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
            .findFirst();
    
    if(libroBuscado.isPresent()){
        Libro libro = new Libro(libroBuscado.get());
        System.out.println(libro);
    } else {
        System.out.println("Libro no encontrado.");
    }
}
```

``Clase Principal usando Resultado en lugar de DatosResultado:

import com.alura.literalura_mariana.model.Libro;
import com.alura.literalura_mariana.model.Resultado;
import com.alura.literalura_mariana.record.DatosLibro;
import com.alura.literalura_mariana.record.DatosResultado;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Principal {
public static void main(String[] args) {
String jsonResponse = "{...}"; // Aquí iría tu JSON

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DatosResultado datosResultado = objectMapper.readValue(jsonResponse, DatosResultado.class);

            // Usa la clase Resultado para encapsular DatosResultado
            Resultado resultado = new Resultado(datosResultado);

            // Tomar solo el primer resultado
            if (!resultado.getLibros().isEmpty()) {
                DatosLibro datosLibro = resultado.getLibros().get(0);
                Libro libro = new Libro(datosLibro);
                System.out.println(libro);
            } else {
                System.out.println("No se encontraron libros.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}``