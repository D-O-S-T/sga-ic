package br.edu.undf.sga_ic.service;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.ProjetoAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.repository.ProjetoRepository;
import br.edu.undf.sga_ic.utils.CustomExceptionUtils;
import br.edu.undf.sga_ic.utils.EditalUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjetoService {

	private final EditalUtils editalUtils;
	private final RetornoUtils retornoUtils;
	private final CustomExceptionUtils customExceptionUtils;

	private final ProjetoRepository projetoRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(ProjetoAdd projetoAdd) throws CustomException {

		Edital edital = editalUtils.findById(projetoAdd.editalId());

		validaMaxProjetosByEdital(edital);

		Projeto projeto = new Projeto();

		projeto.setEdital(edital);
		projeto.setTitulo(projetoAdd.titulo());
		projeto.setDescricao(projetoAdd.descricao());

		projetoRepository.save(projeto);

		log.info("Projeto registrado com sucesso.");
		return retornoUtils.retornoSucesso("Projeto registrado com sucesso.");
	}

	public void validaMaxProjetosByEdital(Edital edital) throws CustomException {

		BigDecimal projetosCadastrados = projetoRepository.countByEditalId(edital.getId());
		BigDecimal limiteProjetos = edital.getQtdProjetos();

		if (projetosCadastrados.compareTo(limiteProjetos) >= 0) {
			String mensagemErro = String.format(
					"Não é possível cadastrar mais projetos neste edital. Limite: %s. Já cadastrados: %s.",
					limiteProjetos, projetosCadastrados);

			log.error(mensagemErro);
			throw customExceptionUtils.errorAndBadRequest(mensagemErro);
		}
	}
}