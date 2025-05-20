package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.res.AtividadeRes;
import br.edu.undf.sga_ic.dto.res.AtividadeResBig;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.AtividadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
                                             @RequestParam LocalDate dataAbertura, @RequestParam LocalDate dataEncerramento,
                                             @RequestParam Long projetoId, @RequestParam(required = false) MultipartFile[] arquivos, HttpServletRequest request)
            throws CustomException, IOException {
        log.info(" >>> Um Professor está tentando registrar uma nova Atividade.");
        return atividadeService.registrar(titulo, descricao, dataAbertura, dataEncerramento, projetoId, arquivos,
                request);
    }

    @Operation(summary = "Atividade by Projeto", description = "Este endpoint serve para retornar Atividades de um projeto.")
    @GetMapping("projeto/{projetoId}")
    public List<AtividadeRes> findByprojeto(@PathVariable Long projetoId) throws CustomException {
        log.info(" >>> Um usuário está tentando retornar as Atividades de um Projeto..");
        return atividadeService.findByprojeto(projetoId);
    }

    @Operation(summary = "Atividade by Id", description = "Este endpoint serve para retornar um Atividade pelo Id.")
    @GetMapping("/{atividadeId}")
    public AtividadeResBig findById(@PathVariable Long atividadeId) throws CustomException {
        log.info(" >>> Um usuário está tentando retornar um Atividade pelo id.");
        return atividadeService.findById(atividadeId);
    }
}