package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProfessorUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final ProfessorRepository professorRepository;

	public Professor findById(Long id) throws CustomException {
		return professorRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Professor informado não encontrado"));
	}
}