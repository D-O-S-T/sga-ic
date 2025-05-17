package br.edu.undf.sga_ic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.ProfessorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/professor")
@Tag(name = "ProfessorController", description = "Endpoints relacionados a requisições de Professor")
public class ProfessorController {

	private final ProfessorService professorService;

	@Operation(summary = "Registrar Professor", description = "Este endpoint serve para registrar um novo Professor.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestBody @Valid ProfessorAdd professorAdd) throws CustomException {
		log.info(" >>> Um Usuário está tentando registrar um novo Professor.");
		return professorService.registrar(professorAdd);
	}
}