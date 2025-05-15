package br.edu.undf.sga_ic.exception;

import org.springframework.http.HttpStatus;

import br.edu.undf.sga_ic.enums.SeverityStatus;

public class CustomException extends Exception {
	private static final long serialVersionUID = 1L;

	HttpStatus httpStatus;
	SeverityStatus severityStatus;

	public CustomException(String message, SeverityStatus severityStatus, HttpStatus httpStatus) {
		super(message);
		this.httpStatus = httpStatus;
		this.severityStatus = severityStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public SeverityStatus getSeverityStatus() {
		return severityStatus;
	}
}