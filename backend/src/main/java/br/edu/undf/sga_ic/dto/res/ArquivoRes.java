package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record ArquivoRes(

		Long id, String nomeArquivo, String arquivo) {
}