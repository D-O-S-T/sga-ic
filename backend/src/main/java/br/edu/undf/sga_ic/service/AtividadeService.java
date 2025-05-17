package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.AtividadeStatus;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.AtividadeRepository;
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
		atividade.setAtividadeStatus(atividadeStatus);

		atividadeRepository.save(atividade);

		arquivoService.salvar(arquivos, atividade, null);

		log.info("Atividade registrada com sucesso.");
		return retornoUtils.retornoSucesso("Atividade registrada com sucesso.");
	}
}