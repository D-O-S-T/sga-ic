package br.edu.undf.sga_ic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.AlunoAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/aluno")
@Tag(name = "AlunoController", description = "Endpoints relacionados a requisições de Aluno")
public class AlunoController {

	private final AlunoService alunoService;

	@Operation(summary = "Registrar Aluno", description = "Este endpoint serve para registrar um novo ALuno.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestBody @Valid AlunoAdd alunoAdd) throws CustomException {
		log.info(" >>> Um Usuário está tentando registrar um novo Aluno.");
		return alunoService.registrar(alunoAdd);
	}
}