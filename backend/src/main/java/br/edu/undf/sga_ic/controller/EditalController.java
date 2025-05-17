package br.edu.undf.sga_ic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.EditalAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.service.EditalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/edital")
@Tag(name = "EditalController", description = "Endpoints relacionados a requisições de Edital")
public class EditalController {

	private final EditalService editalService;

	@Operation(summary = "Registrar Edital", description = "Este endpoint serve para registrar um novo Edital.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestBody @Valid EditalAdd editalAdd) {
		log.info(" >>> Um Usuário está tentando registrar um novo Aluno.");
		return editalService.registrar(editalAdd);
	}
}