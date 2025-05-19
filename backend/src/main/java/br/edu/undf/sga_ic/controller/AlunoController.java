package br.edu.undf.sga_ic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.AlunoAdd;
import br.edu.undf.sga_ic.dto.res.AlunoRes;
import br.edu.undf.sga_ic.dto.res.AlunoResShort;
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

	@Operation(summary = "Find All Aluno", description = "Este endpoint serve para retornar todos os Alunos registrados.")
	@GetMapping
	public List<AlunoResShort> findAll() throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar todos os alunos da Aplicação.");
		return alunoService.findAll();
	}

	@Operation(summary = "Aluno by Id", description = "Este endpoint serve para retornar um Aluno pelo Id.")
	@GetMapping("/{alunoId}")
	public AlunoRes findById(@PathVariable Long alunoId) throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar um Aluno pelo id.");
		return alunoService.findById(alunoId);
	}

	@Operation(summary = "Deletar Aluno", description = "Este endpoint serve para deletar um ALuno.")
	@DeleteMapping("/deletar/{alunoId}")
	public ResponseEntity<Retorno> deletar(@PathVariable Long alunoId) throws CustomException {
		log.info(" >>> Um Usuário está tentando deletar um Aluno.");
		return alunoService.deletar(alunoId);
	}
}