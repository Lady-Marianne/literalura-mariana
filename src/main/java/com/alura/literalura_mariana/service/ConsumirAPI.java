package com.alura.literalura_mariana.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumirAPI {
    public String obtenerDatos(String url){
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("la URL no puede ser nula ni estar vacía.");
        }
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
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
        String json = response.body();
        return json;
    }
}
