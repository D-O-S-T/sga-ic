package br.edu.undf.sga_ic.dto.req;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AlunoAdd(

        String curriculoLattes,
        @NotBlank(message = "Informar Nome é obrigatório.") String nome,
        @NotBlank(message = "Informar E-mail é obrigatório.") @Email(message = "Email inválido. O formato deve ser: xxxxxxxx@xxxx.xxx") String email,
        @NotBlank(message = "Informar CPF é obrigatório.") @CPF(message = "CPF inválido pelo formato ou validação. O formato deve ser: XXX.XXX.XXX-XX") String cpf,
        @NotBlank(message = "Informar Celular é obrigatório.") String celular) {
}