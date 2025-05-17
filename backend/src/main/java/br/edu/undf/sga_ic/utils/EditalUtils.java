package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.repository.EditalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EditalUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final EditalRepository editalRepository;

	public Edital findById(Long id) throws CustomException {
		return editalRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Edital informado não encontrado"));
	}
}
