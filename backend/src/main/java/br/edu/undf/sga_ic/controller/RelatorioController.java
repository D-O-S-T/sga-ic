package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.res.*;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.RelatorioService;
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
@RequestMapping("/relatorio")
@Tag(name = "RelatorioController", description = "Endpoints relacionados a requisições de Relatorio")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Operation(summary = "Registrar Relatorio", description = "Este endpoint serve para registrar um novo Relatorio.")
    @PostMapping("/registrar")
    public ResponseEntity<Retorno> registrar(@RequestParam String titulo, @RequestParam String descricao,
                                             @RequestParam LocalDate dataAbertura, @RequestParam LocalDate dataEncerramento,
                                             @RequestParam Long editalId, @RequestParam(required = false) MultipartFile[] arquivos, HttpServletRequest request)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando registrar um novo Relatorio.");
        return relatorioService.registrar(titulo, descricao, dataAbertura, dataEncerramento, editalId, arquivos,
                request);
    }

    @Operation(summary = "Relatorio by Projeto", description = "Este endpoint serve para retornar Relatorios de um projeto.")
    @GetMapping("projeto/{projetoId}")
    public List<RelatorioRes> findByprojeto(@PathVariable Long projetoId) throws CustomException {
        log.info(" >>> Um usuário está tentando retornar as Atividades de um Projeto..");
        return relatorioService.findByprojeto(projetoId);
    }

    @Operation(summary = "Relatorio by Id", description = "Este endpoint serve para retornar um Relatorio pelo Id.")
    @GetMapping("/{relatorioId}")
    public RelatorioResBig findById(@PathVariable Long relatorioId) throws CustomException {
        log.info(" >>> Um usuário está tentando retornar um Atividade pelo id.");
        return relatorioService.findById(relatorioId);
    }
}