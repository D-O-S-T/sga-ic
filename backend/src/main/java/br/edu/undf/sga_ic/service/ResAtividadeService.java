package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.ResAtividade;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.ResAtividadeRepository;
import br.edu.undf.sga_ic.utils.AtividadeUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResAtividadeService {

	private final RetornoUtils retornoUtils;
	private final UsuarioUtils usuariolUtils;
	private final AtividadeUtils atividadeUtils;

	private final ArquivoService arquivoService;

	private final ResAtividadeRepository resAtividadeRepository;

	public ResponseEntity<Retorno> registrar(String descricao, Long atividadeId, MultipartFile[] arquivos,
			HttpServletRequest request) throws CustomException, IOException {

		Usuario usuario = usuariolUtils.findByToken(request);

		Aluno aluno = usuario.getAluno();

		Atividade atividade = atividadeUtils.findById(atividadeId);

		ResAtividade resAtividade = new ResAtividade();

		resAtividade.setAluno(aluno);
		resAtividade.setAtividade(atividade);
		resAtividade.setDescricao(descricao);

		resAtividadeRepository.save(resAtividade);

		if (arquivos != null && Arrays.stream(arquivos).anyMatch(a -> !a.isEmpty())) {
			arquivoService.salvar(arquivos, null, resAtividade, null, null);
		}

		log.info("Resposta registrada com sucesso.");
		return retornoUtils.retornoSucesso("Resposta registrada com sucesso.");
	}
}