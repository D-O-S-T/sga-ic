package br.edu.undf.sga_ic.utils;

import java.util.List;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmptyUtils {

	private final CustomExceptionUtils customExceptionUtils;

	public void validaListaVazia(List<?> lista, String mensagem) throws CustomException {
		if (lista.isEmpty()) {
			log.info(" >>> Lista vazia: " + mensagem);
			throw customExceptionUtils.successAndNoContent(mensagem);
		}
	}
}