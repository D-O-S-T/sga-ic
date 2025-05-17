package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.repository.AtividadeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AtividadeUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final AtividadeRepository atividadeRepository;

	public Atividade findById(Long id) throws CustomException {
		return atividadeRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Atividade informada não encontrada."));
	}
}