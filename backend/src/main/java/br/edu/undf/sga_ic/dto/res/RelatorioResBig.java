package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

import java.util.List;

@Builder
public record RelatorioResBig(

        Long id, String titulo, String descricao, String dataRegistro, String dataAbertura,
        String dataEncerramento, CoordenadorShort coordenador, List<ArquivoRes> arquivosRelatorio,
        List<ResRelatorioResBig> repostas) {
}
