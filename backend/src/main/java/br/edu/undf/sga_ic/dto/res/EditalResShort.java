package br.edu.undf.sga_ic.dto.res;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record EditalResShort(

		Long id, String titulo, String instituicao, LocalDate dtInicio, LocalDate dtFim) {
}