package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record ProfessorResShort(

		Long id, String nome, String cpf, String fotoPerfil) {
}