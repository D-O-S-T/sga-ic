package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjetoUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final ProjetoRepository projetoRepository;

	public Projeto findById(Long id) throws CustomException {
		return projetoRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Projeto informado não encontrado"));
	}
}