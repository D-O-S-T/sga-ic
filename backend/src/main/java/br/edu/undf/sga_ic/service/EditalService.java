package br.edu.undf.sga_ic.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.edu.undf.sga_ic.dto.req.EditalAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.repository.EditalRepository;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditalService {

	private final RetornoUtils retornoUtils;

	private final EditalRepository editalRepository;

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
}