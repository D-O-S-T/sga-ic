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

import br.edu.undf.sga_ic.dto.req.CoordenadorAdd;
import br.edu.undf.sga_ic.dto.res.CoordenadorRes;
import br.edu.undf.sga_ic.dto.res.CoordenadorResShort;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.CoordenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/coordenador")
@Tag(name = "CoordenadorController", description = "Endpoints relacionados a requisições de Coordenador")
public class CoordenadorController {

	private final CoordenadorService coordenadorService;

	@Operation(summary = "Registrar Coordenador", description = "Este endpoint serve para registrar um novo Coordenador.")
	@PostMapping("/registrar")
	public ResponseEntity<Retorno> registrar(@RequestBody @Valid CoordenadorAdd coordenadorAdd) throws CustomException {
		log.info(" >>> Um Usuário está tentando registrar um novo Coordenador.");
		return coordenadorService.registrar(coordenadorAdd);
	}

	@Operation(summary = "Find All Coordenador", description = "Este endpoint serve para retornar todos os Coordenadores registrados.")
	@GetMapping
	public List<CoordenadorResShort> findAll() throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar todos os Coordenadores da Aplicação.");
		return coordenadorService.findAll();
	}

	@Operation(summary = "Coordenador by Id", description = "Este endpoint serve para retornar um Coordenador pelo Id.")
	@GetMapping("/{coordenadorId}")
	public CoordenadorRes findById(@PathVariable Long coordenadorId) throws CustomException {
		log.info(" >>> Um Coordenador está tentando retornar um Coordenador pelo id.");
		return coordenadorService.findById(coordenadorId);
	}

	@Operation(summary = "Deletar Coordenador", description = "Este endpoint serve para deletar um Coordenador.")
	@DeleteMapping("/deletar/{coordenadorId}")
	public ResponseEntity<Retorno> deletar(@PathVariable Long coordenadorId) throws CustomException {
		log.info(" >>> Um Usuário está tentando deletar um Coordenador.");
		return coordenadorService.deletar(coordenadorId);
	}
}
