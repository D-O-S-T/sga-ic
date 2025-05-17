package br.edu.undf.sga_ic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.undf.sga_ic.dto.req.ProjetoAlunoAdd;
import br.edu.undf.sga_ic.dto.req.ProjetoProfessorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.ProjetoEditalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/projeto-edital")
@Tag(name = "ProjetoEditalController", description = "Endpoints relacionados a requisições de relacionamento entre Projeto e Edital")
public class ProjetoEditalController {

	private final ProjetoEditalService projetoEditalService;

	@PostMapping("/registrar/aluno")
	public ResponseEntity<Retorno> registrarAluno(@RequestBody ProjetoAlunoAdd projetoAlunoAdd) throws CustomException {
		log.info("Tentativa de vincular o aluno de id: " + projetoAlunoAdd.alunoId() + " - ao projeto de id: "
				+ projetoAlunoAdd.projetoId());
		return projetoEditalService.registrarAluno(projetoAlunoAdd);
	}

	@PostMapping("/registrar/professor")
	public ResponseEntity<Retorno> registrarProfessor(@RequestBody ProjetoProfessorAdd projetoProfessorAdd)
			throws CustomException {
		log.info("Tentativa de vincular o professor de id: " + projetoProfessorAdd.professorId()
				+ " - ao projeto de id: " + projetoProfessorAdd.projetoId());
		return projetoEditalService.registrarProfessor(projetoProfessorAdd);
	}
}