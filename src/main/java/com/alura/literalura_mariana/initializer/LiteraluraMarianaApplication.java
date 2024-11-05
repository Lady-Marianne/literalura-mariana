package com.alura.literalura_mariana.initializer;

import com.alura.literalura_mariana.principal.Principal;
import com.alura.literalura_mariana.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.alura.literalura_mariana.model")
@EnableJpaRepositories("com.alura.literalura_mariana.repository")
public class LiteraluraMarianaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraMarianaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		try {
			Principal principal = new Principal();
			principal.mostrarMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
