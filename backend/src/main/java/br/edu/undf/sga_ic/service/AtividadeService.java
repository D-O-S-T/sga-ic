package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.AlunoShort;
import br.edu.undf.sga_ic.dto.res.ArquivoRes;
import br.edu.undf.sga_ic.dto.res.AtividadeRes;
import br.edu.undf.sga_ic.dto.res.AtividadeResBig;
import br.edu.undf.sga_ic.dto.res.ProfessorShort;
import br.edu.undf.sga_ic.dto.res.ResAtividadeResBig;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.AtividadeRepository;
import br.edu.undf.sga_ic.repository.ResAtividadeRepository;
import br.edu.undf.sga_ic.utils.AtividadeUtils;
import br.edu.undf.sga_ic.utils.DateUtils;
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
public class AtividadeService {

	private final DateUtils dateUtils;
	private final EmptyUtils emptyUtils;
	private final RetornoUtils retornoUtils;
	private final UsuarioUtils usuarioUtils;
	private final ProjetoUtils projetoUtils;
	private final AtividadeUtils atividadeUtils;

	private final ArquivoService arquivoService;

	private final AtividadeRepository atividadeRepository;
	private final ResAtividadeRepository resAtividadeRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(String titulo, String descricao, LocalDate dataAbertura,
			LocalDate dataEncerramento, Long projetoId, MultipartFile[] arquivos, HttpServletRequest request)
			throws CustomException, IOException {

		Usuario usuario = usuarioUtils.findByToken(request);

		Professor professor = usuario.getProfessor();

		Projeto projeto = projetoUtils.findById(projetoId);

		Atividade atividade = new Atividade();

		atividade.setTitulo(titulo);
		atividade.setProjeto(projeto);
		atividade.setProfessor(professor);
		atividade.setDescricao(descricao);
		atividade.setDataAbertura(dataAbertura);
		atividade.setDataEncerramento(dataEncerramento);
		atividade.setDataRegistro(LocalDateTime.now());

		atividadeRepository.save(atividade);

		if (arquivos != null && Arrays.stream(arquivos).anyMatch(a -> !a.isEmpty())) {
			arquivoService.salvar(arquivos, atividade, null, null, null);
		}

		log.info("Atividade registrada com sucesso.");
		return retornoUtils.retornoSucesso("Atividade registrada com sucesso.");
	}

	public List<AtividadeRes> findByprojeto(Long projetoId) throws CustomException {

		Projeto projeto = projetoUtils.findById(projetoId);

		List<Atividade> atividades = atividadeRepository.findByProjetoIdOrderByDataRegistroDesc(projeto.getId());

		emptyUtils.validaListaVazia(atividades, "Não foram encontradas Atividades registradas para esse projeto.");

		log.info(">>> Retornando lista de atividades com sucesso para o projeto com ID: {}", projeto.getId());
		return mapAtividadesToDTO(atividades);
	}

	public AtividadeResBig findById(@PathVariable Long atividadeId) throws CustomException {

		Atividade atividade = atividadeUtils.findById(atividadeId);

		ProfessorShort professorDTO = ProfessorShort.builder().id(atividade.getProfessor().getId())
				.nome(atividade.getProfessor().getNome()).build();

		List<ArquivoRes> arquivosAtividade = arquivoService.findArquivosByAtividade(atividadeId);

		List<ResAtividadeResBig> respostasDTO = resAtividadeRepository.findByAtividadeId(atividadeId).stream()
				.map(res -> {

					AlunoShort alunoDTO = AlunoShort.builder().id(res.getAluno().getId()).nome(res.getAluno().getNome())
							.build();

					List<ArquivoRes> arquivosResposta = arquivoService.findArquivosByResposta(res.getId());

					return ResAtividadeResBig.builder().id(res.getId()).descricao(res.getDescricao()).aluno(alunoDTO)
							.arquivosResposta(arquivosResposta).build();

				}).collect(Collectors.toList());

		return AtividadeResBig.builder()
				.id(atividade.getId())
				.titulo(atividade.getTitulo())
				.descricao(atividade.getDescricao())
				.dataRegistro(dateUtils.formatarDataHora(atividade.getDataRegistro()))
				.dataAbertura(dateUtils.formatarData(atividade.getDataAbertura()))
				.dataEncerramento(dateUtils.formatarData(atividade.getDataEncerramento()))
				.professor(professorDTO)
				.arquivosAtividade(arquivosAtividade)
				.repostas(respostasDTO)
				.build();
	}

	private List<AtividadeRes> mapAtividadesToDTO(List<Atividade> atividades) {
		return atividades.stream()
				.map(atividade -> AtividadeRes.builder()
						.id(atividade.getId())
						.titulo(atividade.getTitulo())
						.descricao(atividade.getDescricao())
						.dataRegistro(dateUtils.formatarDataHora(atividade.getDataRegistro()))
						.dataAbertura(dateUtils.formatarData(atividade.getDataAbertura()))
						.dataEncerramento(dateUtils.formatarData(atividade.getDataEncerramento()))
						.build())
				.toList();
	}
}