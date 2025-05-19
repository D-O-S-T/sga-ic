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
import br.edu.undf.sga_ic.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "UsuarioController", description = "Endpoints relacionados a requisições de Usuario")
public class UsuarioController {

	private final UsuarioService usuarioService;

	@Operation(summary = "Registrar Aluno", description = "Este endpoint serve para registrar um novo ALuno.")
	@PostMapping("/upload-foto")
	public ResponseEntity<Retorno> uploadFoto(@RequestParam MultipartFile foto, HttpServletRequest request)
			throws CustomException, IOException {
		log.info(" >>> Um Usuário está tentando alterar sua foto de perfil na aplicação");
		return usuarioService.uploadFoto(foto, request);
	}
}