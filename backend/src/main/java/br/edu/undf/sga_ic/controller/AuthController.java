package br.edu.undf.sga_ic.controller;

import br.edu.undf.sga_ic.dto.req.Login;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Endpoints relacionados a requisições de Autentificação")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Login", description = "Este endpoint serve para login de um usuário.")
    @PostMapping("/login")
    public ResponseEntity<UsuarioRole> login(@RequestBody @Valid Login login)
            throws CustomException {
        log.info(" >>> Um Usuário está tentando login na aplicação.");
        return authService.login(login);
    }

    @Operation(summary = "Logout", description = "Este endpoint serve para logout de um usuário.")
    @PostMapping("/logout")
    public ResponseEntity<Retorno> logout() {
        log.info(" >>> Um Usuário está tentando logout na aplicação.");
        return authService.logout();
    }
}