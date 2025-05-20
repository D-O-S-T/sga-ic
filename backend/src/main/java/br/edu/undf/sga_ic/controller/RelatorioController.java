package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

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
}