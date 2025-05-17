package br.edu.undf.sga_ic.dto.req;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditalAdd(

		String descricao,
		@NotNull(message = "Data de fim é obrigatória.") LocalDate dtFim,
		@NotBlank(message = "Informar Título é obrigatório.") String titulo,
		@NotNull(message = "Data de início é obrigatória.") LocalDate dtInicio,
		@NotBlank(message = "Informar Instituição é obrigatório.") String instituicao,
		@NotNull(message = "Informar quantidade de alunos é obrigatório.") @Digits(integer = 10, fraction = 0, message = "Quantidade de alunos deve ser um número inteiro.") BigDecimal qtdAlunos,
		@NotNull(message = "Informar quantidade de projetos é obrigatório.") @Digits(integer = 10, fraction = 0, message = "Quantidade de projetos deve ser um número inteiro.") BigDecimal qtdProjetos,
		@NotNull(message = "Informar quantidade de bolsistas é obrigatório.") @Digits(integer = 10, fraction = 0, message = "Quantidade de bolsistas deve ser um número inteiro.") BigDecimal qtdBolsistas,
		@NotNull(message = "Informar quantidade de professores é obrigatório.") @Digits(integer = 10, fraction = 0, message = "Quantidade de professores deve ser um número inteiro.") BigDecimal qtdProfessores) {
}
