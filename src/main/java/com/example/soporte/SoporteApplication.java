package com.example.soporte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* 1. Implementar paginado de APIs
* 2. Implementar update de Ticket
* 3. Tests cucumber
* 5. Implementar manejo de notificaciones con Proyectos
* 6. Implementar endpoint para obtener todas las tareas de un ticket X filtrando por titulo (usar queryParam)
* 7. Estadisticas de ticket
* 8. Documentar metodos
* 9. Ajustar diagrama de clases
* 10. verificar que no exponemos data inncesaria, ej: atributos iternos
* */
@SpringBootApplication
public class SoporteApplication {
	public static void main(String[] args) {
		SpringApplication.run(SoporteApplication.class, args);
	}
}
