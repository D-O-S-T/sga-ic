package br.edu.undf.sga_ic.dto.res;

import br.edu.undf.sga_ic.enums.AtividadeStatus;
import lombok.Builder;

@Builder
public record AtividadeRes(

		Long id, String titulo, String descricao, String dataRegistro, String dataAbertura, String dataEncerramento,
		AtividadeStatus atividadeStatus) {
}