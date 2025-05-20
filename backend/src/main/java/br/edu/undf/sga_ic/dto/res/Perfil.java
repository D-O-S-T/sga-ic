package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record Perfil(
        Long id, String nome, String fotoPerfil) {
}