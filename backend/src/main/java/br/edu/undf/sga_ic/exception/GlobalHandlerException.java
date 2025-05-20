package br.edu.undf.sga_ic.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHandlerException {

	private final RetornoUtils retornoUtils;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Retorno> handleException(Exception ex) {
		log.error("Erro interno no servidor: " + ex);
		return retornoUtils.retornoErroInternoNoServidor("Erro inesperado durante o preocessamento.");
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Retorno> handleRuntimeException(RuntimeException ex) {
		log.error("Erro interno no servidor: " + ex);
		return retornoUtils.retornoErroInternoNoServidor("Erro inesperado durante o preocessamento.");
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Retorno> handleCustomException(CustomException ex) {
		log.error("Erro interno no servidor: " + ex);
		return retornoUtils.retornoCustomizado(ex.getMessage(), ex.getSeverityStatus(), ex.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Retorno> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

		String message = ex.getBindingResult().getFieldErrors().stream().findFirst()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("Dados inválidos");

		return retornoUtils.retornoBadRequest(message);
	}
}