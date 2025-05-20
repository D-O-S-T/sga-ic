package br.edu.undf.sga_ic.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.SeverityStatus;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetornoUtils {

	public ResponseEntity<Retorno> retornoBadRequest(String message) {
		Retorno retorno = mapearRetorno(message, SeverityStatus.ERROR);
		log.info(" >>> Mensagem de Requisição Ruim: {}", message);
		return ResponseEntity.badRequest().body(retorno);
	}

	public ResponseEntity<Retorno> retornoSucesso(String message) {
		Retorno retorno = mapearRetorno(message, SeverityStatus.SUCCESS);
		log.info(" >>> Mensagem de Sucesso: {}", message);
		return ResponseEntity.ok().body(retorno);
	}

	public ResponseEntity<Retorno> retornoErroInternoNoServidor(String message) {
		Retorno retorno = mapearRetorno(message, SeverityStatus.ERROR);
		log.info(" >>> Mensagem de Erro Interno no Servidor: {}", message);
		return ResponseEntity.internalServerError().body(retorno);
	}

	public ResponseEntity<Retorno> retornoCustomizado(String message, SeverityStatus severityStatus,
			HttpStatus httpStatus) {
		Retorno retorno = mapearRetorno(message, severityStatus);
		log.info(" >>> Mensagem Customizada: {}", message);
		return ResponseEntity.status(httpStatus).body(retorno);
	}

	public ResponseEntity<UsuarioRole> retornoSucessoLoginComToken(UsuarioRole usuarioRole, String cookie) {
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie).body(usuarioRole);
	}

	public ResponseEntity<Retorno> retornoSucessoLogoutComToken(String message, String cookie) {
		Retorno retorno = mapearRetorno(message, SeverityStatus.SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie).body(retorno);
	}

	private Retorno mapearRetorno(String message, SeverityStatus severityStatus) {
		return Retorno.builder().message(message).severityStatus(severityStatus).build();
	}
}