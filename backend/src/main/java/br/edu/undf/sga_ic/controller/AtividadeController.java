package br.edu.undf.sga_ic.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/atividade")
@Tag(name = "AtividadeController", description = "Endpoints relacionados a requisições de Atividade")
public class AtividadeController {

	private final AtividadeService atividadeService;

	@Operation(summary = "Registrar Atividade", description = "Este endpoint serve para registrar uma nova Atividade.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestParam String titulo, @RequestParam String descricao,
			@RequestParam LocalDateTime dataAbertura, @RequestParam LocalDateTime dataEncerramento,
			@RequestParam Long projetoId, @RequestParam MultipartFile[] arquivos, HttpServletRequest request)
			throws CustomException, IOException {
		log.info(" >>> Um Professor está tentando registrar uma nova Atividade.");
		return atividadeService.registrar(titulo, descricao, dataAbertura, dataEncerramento, projetoId, arquivos,
				request);
	}
}