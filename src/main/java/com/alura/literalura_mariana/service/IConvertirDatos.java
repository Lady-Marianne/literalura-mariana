package com.alura.literalura_mariana.service;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json, Class<T> clase);

}
