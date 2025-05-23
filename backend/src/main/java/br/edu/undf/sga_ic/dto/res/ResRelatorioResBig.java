package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

import java.util.List;

@Builder
public record ResRelatorioResBig(
        Long id, String descricao, ProfessorShort professor, List<ArquivoRes> arquivosResposta) {
}
