package br.edu.undf.sga_ic.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.EditalAdd;
import br.edu.undf.sga_ic.dto.res.EditalRes;
import br.edu.undf.sga_ic.dto.res.EditalResShort;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.repository.EditalRepository;
import br.edu.undf.sga_ic.utils.EditalUtils;
import br.edu.undf.sga_ic.utils.EmptyUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditalService {

	private final EmptyUtils emptyUtils;
	private final EditalUtils editalUtils;
	private final RetornoUtils retornoUtils;

	private final EditalRepository editalRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(EditalAdd editalAdd) {

		Edital edital = new Edital();

		edital.setDtFim(editalAdd.dtFim());
		edital.setTitulo(editalAdd.titulo());
		edital.setDtInicio(editalAdd.dtInicio());
		edital.setDescricao(editalAdd.descricao());
		edital.setQtdAlunos(editalAdd.qtdAlunos());
		edital.setQtdProjetos(editalAdd.qtdProjetos());
		edital.setInstituicao(editalAdd.instituicao());
		edital.setQtdBolsistas(editalAdd.qtdBolsistas());
		edital.setQtdProfessores(editalAdd.qtdProfessores());

		editalRepository.save(edital);

		log.info("Edital registrado com sucesso.");
		return retornoUtils.retornoSucesso("Edital registrado com sucesso.");
	}

	public List<EditalResShort> findAll() throws CustomException {

		List<Edital> editais = editalRepository.findAll();

		emptyUtils.validaListaVazia(editais, "Não foram encontrados editais registrados.");

		log.info(">>> Retornando lista de editais com sucesso.");
		return mapEditaisToDTO(editais);
	}

	public EditalRes findById(Long editalId) throws CustomException {

		Edital edital = editalUtils.findById(editalId);

		EditalRes editalDTO = EditalRes.builder().id(edital.getId()).titulo(edital.getTitulo())
				.instituicao(edital.getInstituicao()).descricao(edital.getDescricao())
				.qtdBolsistas(edital.getQtdBolsistas()).qtdAlunos(edital.getQtdAlunos())
				.qtdProfessores(edital.getQtdProfessores()).qtdProjetos(edital.getQtdProjetos())
				.dtInicio(edital.getDtInicio()).dtFim(edital.getDtFim()).build();

		log.info(">>> Retornando edital pelo id com sucesso.");
		return editalDTO;
	}

	private List<EditalResShort> mapEditaisToDTO(List<Edital> editais) {
		return editais.stream()
				.map(edital -> EditalResShort.builder().id(edital.getId()).titulo(edital.getTitulo())
						.instituicao(edital.getInstituicao()).dtInicio(edital.getDtInicio()).dtFim(edital.getDtFim())
						.build())
				.toList();
	}
}