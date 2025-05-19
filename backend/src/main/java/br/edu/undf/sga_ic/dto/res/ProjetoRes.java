package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record ProjetoRes(

		Long id, String titulo, String descricao) {
}