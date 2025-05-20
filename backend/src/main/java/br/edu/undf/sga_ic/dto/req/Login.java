package br.edu.undf.sga_ic.dto.req;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record Login(

        @NotBlank(message = "Informar CPF é obrigatório.") @CPF String cpf,
        @NotBlank(message = "Informar Senha é obrigatório.") String senha) {
}