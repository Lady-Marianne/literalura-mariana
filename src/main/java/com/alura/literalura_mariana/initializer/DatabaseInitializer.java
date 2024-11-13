/*package com.alura.literalura_mariana.initializer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        String dbName = "literalura";  // El nombre de la base de datos que estás verificando.

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {

            // Verificamos si ya existe la base de datos:
            ResultSet resultSet = statement.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "';"
            );

            if (!resultSet.next()) {  // Si el ResultSet está vacío, la base de datos no existe.
                statement.executeUpdate("CREATE DATABASE \"" + dbName + "\";");
                System.out.println("\nBase de datos creada: " + dbName);
            } else {
                System.out.println("\nLa base de datos ya existe: " + dbName);
            }
        } catch (Exception e) {
            System.out.println("\nError al verificar o crear la base de datos: " + e.getMessage());
        }
    }
}*/
