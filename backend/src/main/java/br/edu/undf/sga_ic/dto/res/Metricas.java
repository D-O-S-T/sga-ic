package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record Metricas(
        long qtdProfessores,
        long qtdBolsistas,
        long qtdNaoBolsistas,
        long qtdAtividades,
        long qtdResAtividades,
        long qtdRelatorios,
        long qtdResRelatorios) {
}