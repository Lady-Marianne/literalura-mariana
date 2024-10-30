package com.alura.literalura_mariana;

import com.alura.literalura_mariana.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraMarianaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraMarianaApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.mostrarMenu();
	}
}
