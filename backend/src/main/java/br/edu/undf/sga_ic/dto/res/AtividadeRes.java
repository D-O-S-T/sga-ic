package br.edu.undf.sga_ic.dto.res;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record AtividadeRes(

		Long id, String titulo, String descricao, String dataRegistro, LocalDate dataAbertura,
		LocalDate dataEncerramento) {
}