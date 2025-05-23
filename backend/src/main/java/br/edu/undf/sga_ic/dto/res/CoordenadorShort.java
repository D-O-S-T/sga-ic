package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record CoordenadorShort(
        Long id, String nome) {
}