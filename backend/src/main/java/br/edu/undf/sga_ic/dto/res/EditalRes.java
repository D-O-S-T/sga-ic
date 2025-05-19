package br.edu.undf.sga_ic.dto.res;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record EditalRes(

		Long id, String titulo, String instituicao, String descricao, BigDecimal qtdBolsistas, BigDecimal qtdAlunos,
		BigDecimal qtdProfessores, BigDecimal qtdProjetos, LocalDate dtInicio, LocalDate dtFim) {
}