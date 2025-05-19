package br.edu.undf.sga_ic.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.ResAtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/res-atividade")
@Tag(name = "ResAtividadeController", description = "Endpoints relacionados a requisições de Respostas de Atividade")
public class ResAtividadeController {

	private final ResAtividadeService resAtividadeService;

	@Operation(summary = "Registrar Resposta", description = "Este endpoint serve para registrar uma nova Resposta.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestParam String descricao, @RequestParam Long atividadeId,
			@RequestParam MultipartFile[] arquivos, HttpServletRequest request) throws CustomException, IOException {
		log.info(" >>> Um Aluno está tentando registrar uma nova Resposta.");
		return resAtividadeService.registrar(descricao, atividadeId, arquivos, request);
	}
}