package br.edu.undf.sga_ic.service;

import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.undf.sga_ic.dto.req.Login;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.infra.security.JwtService;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.utils.CustomExceptionUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final PasswordEncoder passwordEncoder;

	private final JwtService jwtService;

	private final RetornoUtils retornoUtils;
	private final UsuarioUtils usuarioUtils;
	private final CustomExceptionUtils customExceptionUtils;

	public ResponseEntity<UsuarioRole> login(Login login) throws CustomException {

		Usuario usuario = usuarioUtils.findByCpf(login.cpf());

		if (!passwordEncoder.matches(login.senha(), usuario.getSenhaHash())) {
			throw customExceptionUtils.errorAndBadRequest("Usuário ou Senha incorreto.");
		}

		String token = jwtService.generateTokenFromUserDetails(usuario.getId(), usuario.getUsuarioRole());

		log.info(" >>> Login realizado com sucesso.");
		return retornoUtils.retornoSucessoLoginComToken(usuario.getUsuarioRole(), token);
	}

	public ResponseEntity<Retorno> logout() {

		ResponseCookie cookie = jwtService.getCleanJwtCookie();
		return retornoUtils.retornoSucessoLogoutComToken("Logout realizado com sucesso.", cookie.toString());
	}
}