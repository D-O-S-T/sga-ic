package br.edu.undf.sga_ic.dto.res;

import lombok.Builder;

@Builder
public record ProfessorRes(

		Long id, String nome, String cpf, String email, String celular, String curriculoLattes, String fotoPerfil) {
}