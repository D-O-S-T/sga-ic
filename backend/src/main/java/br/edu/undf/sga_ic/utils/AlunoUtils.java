package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlunoUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final AlunoRepository alunoRepository;

	public Aluno findById(Long id) throws CustomException {
		return alunoRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Aluno informado não encontrado"));
	}
}