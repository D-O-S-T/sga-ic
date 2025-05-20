package br.edu.undf.sga_ic.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Permite todos os endpoints
						.allowedOrigins("http://localhost:4200") // Substitua pelo endereço do Angular
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Métodos permitidos
						.allowedHeaders("*") // Permite todos os cabeçalhos
						.allowCredentials(true); // Permite envio de cookies e credenciais
			}
		};
	}
}