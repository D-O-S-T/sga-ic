package br.edu.undf.sga_ic.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.enums.SeverityStatus;
import br.edu.undf.sga_ic.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomExceptionUtils {

	public CustomException successAndNoContent(String message) {
		log.info(" >>> CustomException utils disparada: {}", message);
		return new CustomException(message, SeverityStatus.SUCCESS, HttpStatus.NO_CONTENT);
	}

	public CustomException errorAndNotFound(String message) {
		log.info(" >>> CustomException utils disparada: {}", message);
		return new CustomException(message, SeverityStatus.ERROR, HttpStatus.NOT_FOUND);
	}

	public CustomException errorAndBadRequest(String message) {
		log.info(" >>> CustomException utils disparada: {}", message);
		return new CustomException(message, SeverityStatus.ERROR, HttpStatus.BAD_REQUEST);
	}

	public CustomException errorAndUnauthorized(String message) {
		log.info(" >>> CustomException utils disparada: {}", message);
		return new CustomException(message, SeverityStatus.ERROR, HttpStatus.UNAUTHORIZED);
	}
}