package br.edu.undf.sga_ic.infra.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(title = "sga-ic", description = "Projeto SGA-IC de Estágio Acadêmico de Alunos da UnDF", version = "v1"))
public class SwaggerConfig {

}