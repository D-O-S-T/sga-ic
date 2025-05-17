package br.edu.undf.sga_ic.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjetoAdd(

		@NotNull(message = "Informar Edital é obrigatório.") Long editalId,
		@NotBlank(message = "Informar Título é obrigatório.") String titulo,
		@NotBlank(message = "Informar Descrição é obrigatório.") String descricao) {
}