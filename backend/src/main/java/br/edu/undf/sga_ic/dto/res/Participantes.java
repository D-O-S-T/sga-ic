package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

import java.util.List;

@Builder
public record Participantes(
        List<ProfessorResShort> professores, List<AlunoResShort> bolsistas, List<AlunoResShort> naoBolsistas) {
}