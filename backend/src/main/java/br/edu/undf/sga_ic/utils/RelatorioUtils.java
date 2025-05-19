package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Relatorio;
import br.edu.undf.sga_ic.repository.RelatorioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RelatorioUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final RelatorioRepository relatorioRepository;

	public Relatorio findById(Long id) throws CustomException {
		return relatorioRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Relatório informado não encontrada."));
	}
}