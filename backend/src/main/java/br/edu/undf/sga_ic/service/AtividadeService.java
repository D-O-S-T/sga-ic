package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.AtividadeRes;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.AtividadeStatus;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.AtividadeRepository;
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

	private final ArquivoService arquivoService;

	private final AtividadeRepository atividadeRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(String titulo, String descricao, LocalDateTime dataAbertura,
			LocalDateTime dataEncerramento, Long projetoId, MultipartFile[] arquivos, HttpServletRequest request)
			throws CustomException, IOException {

		Usuario usuario = usuarioUtils.findByToken(request);

		Professor professor = usuario.getProfessor();

		Projeto projeto = projetoUtils.findById(projetoId);

		AtividadeStatus atividadeStatus = dataAbertura.isAfter(LocalDateTime.now()) ? AtividadeStatus.AGUARDANDO
				: AtividadeStatus.ABERTA;

		Atividade atividade = new Atividade();

		atividade.setTitulo(titulo);
		atividade.setProjeto(projeto);
		atividade.setProfessor(professor);
		atividade.setDescricao(descricao);
		atividade.setDataAbertura(dataAbertura);
		atividade.setDataEncerramento(dataEncerramento);
		atividade.setDataRegistro(LocalDateTime.now());
		atividade.setAtividadeStatus(atividadeStatus);

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

	private List<AtividadeRes> mapAtividadesToDTO(List<Atividade> atividades) {
		return atividades.stream()
				.map(atividade -> AtividadeRes.builder().id(atividade.getId()).titulo(atividade.getTitulo())
						.descricao(atividade.getDescricao())
						.dataRegistro(dateUtils.formatarDataHora(atividade.getDataRegistro()))
						.dataAbertura(dateUtils.formatarDataHora(atividade.getDataAbertura()))
						.dataEncerramento(dateUtils.formatarDataHora(atividade.getDataEncerramento()))
						.atividadeStatus(atividade.getAtividadeStatus()).build())
				.toList();
	}
}