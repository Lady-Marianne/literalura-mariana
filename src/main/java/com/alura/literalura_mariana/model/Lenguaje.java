package com.alura.literalura_mariana.model;

public enum Lenguaje {
    ESPANIOL("es","Español"),
    INGLES("en","Inglés"),
    PORTUGUES("pt","Portugués"),
    FRANCES("fr","Francés");

    private String lenguajeGutendex;
    private String lenguajeEspanol;
    Lenguaje (String lenguajeGutendex, String lenguajeEspanol){
        this.lenguajeGutendex = lenguajeGutendex;
        this.lenguajeEspanol = lenguajeEspanol;
    }


}
