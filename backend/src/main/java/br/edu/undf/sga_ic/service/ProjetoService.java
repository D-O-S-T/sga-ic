package br.edu.undf.sga_ic.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.ProjetoAdd;
import br.edu.undf.sga_ic.dto.res.ProjetoRes;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.ProjetoEditalRepository;
import br.edu.undf.sga_ic.repository.ProjetoRepository;
import br.edu.undf.sga_ic.utils.CustomExceptionUtils;
import br.edu.undf.sga_ic.utils.EditalUtils;
import br.edu.undf.sga_ic.utils.EmptyUtils;
import br.edu.undf.sga_ic.utils.ProjetoUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjetoService {

	private final EmptyUtils emptyUtils;
	private final EditalUtils editalUtils;
	private final UsuarioUtils usuarioUtils;
	private final RetornoUtils retornoUtils;
	private final ProjetoUtils projetoUtils;
	private final CustomExceptionUtils customExceptionUtils;

	private final ProjetoRepository projetoRepository;
	private final ProjetoEditalRepository projetoEditalRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(ProjetoAdd projetoAdd) throws CustomException {

		Edital edital = editalUtils.findById(projetoAdd.editalId());

		validaMaxProjetosByEdital(edital);

		Projeto projeto = new Projeto();

		projeto.setEdital(edital);
		projeto.setTitulo(projetoAdd.titulo());
		projeto.setDescricao(projetoAdd.descricao());

		projetoRepository.save(projeto);

		log.info(" >>> Projeto registrado com sucesso.");
		return retornoUtils.retornoSucesso("Projeto registrado com sucesso.");
	}

	public ProjetoRes findById(Long projetoId) throws CustomException {

		Projeto projeto = projetoUtils.findById(projetoId);

		ProjetoRes projetoDTO = ProjetoRes.builder().id(projeto.getId()).titulo(projeto.getTitulo())
				.descricao(projeto.getDescricao()).build();

		log.info(">>> Retornando projeto de Id: {} - com sucesso.", projeto.getId());
		return projetoDTO;
	}

	public List<ProjetoRes> findByAluno(HttpServletRequest request) throws CustomException {

		Usuario usuario = usuarioUtils.findByToken(request);

		Aluno aluno = usuario.getAluno();
		List<Projeto> projetos = projetoEditalRepository.findProjetosByAlunoId(aluno.getId());

		emptyUtils.validaListaVazia(projetos, "Não foram encontrados projetos cadastrados para esse Aluno.");

		log.info(">>> Retornando lista de projetos com sucesso para o aluno com ID: {}", aluno.getId());
		return mapProjetosToDTO(projetos);
	}

	public List<ProjetoRes> findByProfessor(HttpServletRequest request) throws CustomException {

		Usuario usuario = usuarioUtils.findByToken(request);

		Professor professor = usuario.getProfessor();
		List<Projeto> projetos = projetoEditalRepository.findProjetosByProfessorId(professor.getId());

		emptyUtils.validaListaVazia(projetos, "Não foram encontrados projetos cadastrados para esse Professor.");

		log.info(">>> Retornando lista de projetos com sucesso para o professor com ID: {}", professor.getId());
		return mapProjetosToDTO(projetos);
	}

	public List<ProjetoRes> findByEdital(Long editalId) throws CustomException {

		Edital edital = editalUtils.findById(editalId);

		List<Projeto> projetos = projetoRepository.findProjetoByEditalId(edital.getId());

		log.info(">>> Retornando lista de projetos com sucesso para o Edital de ID: {}", edital.getId());
		return mapProjetosToDTO(projetos);
	}

	private List<ProjetoRes> mapProjetosToDTO(List<Projeto> projetos) {
		return projetos.stream().map(projeto -> ProjetoRes.builder().id(projeto.getId()).titulo(projeto.getTitulo())
				.descricao(projeto.getDescricao()).build()).toList();
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