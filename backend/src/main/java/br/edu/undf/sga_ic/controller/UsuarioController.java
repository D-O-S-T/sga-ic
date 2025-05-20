package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.res.Perfil;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/usuario")
@Tag(name = "UsuarioController", description = "Endpoints relacionados a requisições de Usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Upload Foto", description = "Este endpoint serve para alterar foto de usuário logado.")
    @PostMapping("/upload-foto")
    public ResponseEntity<Retorno> uploadFoto(@RequestParam MultipartFile foto, HttpServletRequest request)
            throws CustomException, IOException {
        log.info(" >>> Um Usuário está tentando alterar sua foto de perfil na aplicação");
        return usuarioService.uploadFoto(foto, request);
    }

    @Operation(summary = "Retornar Perfil", description = "Este endpoint serve para retornar perfil de usuário logado.")
    @GetMapping("/perfil")
    public Perfil getPerfil(HttpServletRequest request) throws CustomException {
        log.info(" >>> Um Usuário está tentando retornar as suas informações de perfil");
        return usuarioService.getPerfil(request);
    }
}