package com.example.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 1. Implementar paginado de APIs
 * 11. Implementar endpoint que reciba array de ids de tickets y devuelva los detalles para cada uno de esos tickets.
 * 6. Implementar endpoint para obtener todas las tareas de un ticket X filtrando por titulo (usar queryParam)
 * 3. Tests cucumber
 * 7. Estadisticas de ticket
 * 8. Documentar metodos
 * 9. Ajustar diagrama de clases
 * */
@SpringBootApplication
public class SoporteApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoporteApplication.class, args);
	}
}
