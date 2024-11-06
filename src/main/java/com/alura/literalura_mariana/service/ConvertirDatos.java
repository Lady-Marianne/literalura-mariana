package com.alura.literalura_mariana.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertirDatos implements IConvertirDatos {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json,clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON a la clase: " + clase.getName(), e);
        }
    }

    // Método para devolver un JSON "bonito" (pretty print):
    public String obtenerJsonBonito(String json) {
        try {
            // Formatea el JSON de manera legible
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(json));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al formatear el JSON", e);
        }
    }
}
