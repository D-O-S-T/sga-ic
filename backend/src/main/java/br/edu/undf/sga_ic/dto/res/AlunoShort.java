package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record AlunoShort(

		Long id, String nome) {
}