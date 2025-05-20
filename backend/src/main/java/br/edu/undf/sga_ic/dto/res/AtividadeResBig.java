package br.edu.undf.sga_ic.dto.res;

import java.util.List;

import lombok.Builder;

@Builder
public record AtividadeResBig(

		Long id, String titulo, String descricao, String dataRegistro, String dataAbertura, String dataEncerramento,
		ProfessorShort professor, List<ArquivoRes> arquivosAtividade, List<ResAtividadeResBig> repostas) {
}