package br.edu.undf.sga_ic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.ProjetoAdd;
import br.edu.undf.sga_ic.dto.res.ProjetoRes;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.ProjetoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/projeto")
@Tag(name = "ProjetoController", description = "Endpoints relacionados a requisições de Projeto")
public class ProjetoController {

	private final ProjetoService projetoService;

	@Operation(summary = "Registrar Projeto", description = "Este endpoint serve para registrar um novo Projeto.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestBody @Valid ProjetoAdd projetoAdd) throws CustomException {
		log.info(" >>> Um Usuário está tentando registrar um novo Projeto.");
		return projetoService.registrar(projetoAdd);
	}

	@Operation(summary = "Projetos Aluno", description = "Este endpoint serve para retornar endpoints de um Aluno.")
	@GetMapping("/aluno")
	public List<ProjetoRes> findByAluno(HttpServletRequest request) throws CustomException {
		log.info(" >>> Tentando retornar projetos de um Aluno.");
		return projetoService.findByAluno(request);
	}

	@Operation(summary = "Projetos Professor", description = "Este endpoint serve para retornar endpoints de um Professor.")
	@GetMapping("/professor")
	public List<ProjetoRes> findByProfessor(HttpServletRequest request) throws CustomException {
		log.info(" >>> Tentando retornar projetos de um Professor.");
		return projetoService.findByProfessor(request);
	}
}