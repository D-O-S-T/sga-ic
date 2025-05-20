package br.edu.undf.sga_ic.exception;

import br.edu.undf.sga_ic.enums.SeverityStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class CustomException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    HttpStatus httpStatus;
    SeverityStatus severityStatus;

    public CustomException(String message, SeverityStatus severityStatus, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.severityStatus = severityStatus;
    }
}