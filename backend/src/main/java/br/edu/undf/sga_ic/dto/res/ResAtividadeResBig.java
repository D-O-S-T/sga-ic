package br.edu.undf.sga_ic.dto.res;

import java.util.List;

import lombok.Builder;

@Builder
public record ResAtividadeResBig(

		Long id, String descricao, AlunoShort aluno, List<ArquivoRes> arquivosResposta) {
}