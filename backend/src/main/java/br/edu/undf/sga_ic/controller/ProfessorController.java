package br.edu.undf.sga_ic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.ProfessorAdd;
import br.edu.undf.sga_ic.dto.res.ProfessorRes;
import br.edu.undf.sga_ic.dto.res.ProfessorResShort;
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

	@Operation(summary = "Find All Professor", description = "Este endpoint serve para retornar todos os Professores registrados.")
	@GetMapping
	public List<ProfessorResShort> findAll() throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar todos os Professores da Aplicação.");
		return professorService.findAll();
	}

	@Operation(summary = "Professor by Id", description = "Este endpoint serve para retornar um Professor pelo Id.")
	@GetMapping("/{professorId}")
	public ProfessorRes findById(@PathVariable Long professorId) throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar um Professor pelo id.");
		return professorService.findById(professorId);
	}

	@Operation(summary = "Deletar Professor", description = "Este endpoint serve para deletar um Professor.")
	@DeleteMapping("/deletar/{professorId}")
	public ResponseEntity<Retorno> deletar(@PathVariable Long professorId) throws CustomException {
		log.info(" >>> Um Usuário está tentando deletar um Professor.");
		return professorService.deletar(professorId);
	}

	@Operation(summary = "Editar Professor", description = "Este endpoint serve para editar um Professor.")
	@PutMapping("/editar/{professorId}")
	public ResponseEntity<Retorno> editar(@PathVariable Long professorId, @RequestBody @Valid ProfessorAdd professorAdd)
			throws CustomException {
		log.info(" >>> Um Usuário está tentando editar um Professor.");
		return professorService.editar(professorId, professorAdd);
	}
}