package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.req.ProjetoAlunoAdd;
import br.edu.undf.sga_ic.dto.req.ProjetoProfessorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.ProjetoEditalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/projeto-edital")
@Tag(name = "ProjetoEditalController", description = "Endpoints relacionados a requisições de relacionamento entre Projeto e Edital")
public class ProjetoEditalController {

	private final ProjetoEditalService projetoEditalService;

	@Operation(summary = "Registrar Aluno", description = "Este endpoint serve para registrar um Aluno em um projeto.")
	@PostMapping("/registrar/aluno")
	public ResponseEntity<Retorno> registrarAluno(@RequestBody ProjetoAlunoAdd projetoAlunoAdd) throws CustomException {
		log.info("Tentativa de vincular o aluno de id: " + projetoAlunoAdd.alunoId() + " - ao projeto de id: "
				+ projetoAlunoAdd.projetoId());
		return projetoEditalService.registrarAluno(projetoAlunoAdd);
	}

	@Operation(summary = "Registrar Professor", description = "Este endpoint serve para registrar um Professor em um projeto.")
	@PostMapping("/registrar/professor")
	public ResponseEntity<Retorno> registrarProfessor(@RequestBody ProjetoProfessorAdd projetoProfessorAdd)
			throws CustomException {
		log.info("Tentativa de vincular o professor de id: " + projetoProfessorAdd.professorId()
				+ " - ao projeto de id: " + projetoProfessorAdd.projetoId());
		return projetoEditalService.registrarProfessor(projetoProfessorAdd);
	}

	@Operation(summary = "Retirar Aluno", description = "Este endpoint serve para retirar um Aluno de um projeto.")
	@DeleteMapping("/retirar/{projetoEditalId}")
	public ResponseEntity<Retorno> retirar(@PathVariable Long projetoEditalId) throws CustomException {
		log.info(" >>> Um Usuário está retirar um Aluno de um projeto.");
		return projetoEditalService.retirar(projetoEditalId);
	}
}