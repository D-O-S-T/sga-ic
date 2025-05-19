package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.ProjetoEdital;
import br.edu.undf.sga_ic.model.Relatorio;
import br.edu.undf.sga_ic.model.ResRelatorio;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.ProjetoEditalRepository;
import br.edu.undf.sga_ic.repository.ResRelatorioRepository;
import br.edu.undf.sga_ic.utils.RelatorioUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResRelatorioService {

	private final RetornoUtils retornoUtils;
	private final UsuarioUtils usuariolUtils;
	private final RelatorioUtils relatorioUtils;

	private final ArquivoService arquivoService;

	private final ResRelatorioRepository resRelatorioRepository;
	private final ProjetoEditalRepository projetoEditalRepository;

	public ResponseEntity<Retorno> registrar(String descricao, Long relatorioId, MultipartFile[] arquivos,
			HttpServletRequest request) throws CustomException, IOException {

		Usuario usuario = usuariolUtils.findByToken(request);

		Professor professor = usuario.getProfessor();

		Relatorio relatorio = relatorioUtils.findById(relatorioId);

		ProjetoEdital projetoEdital = projetoEditalRepository.findByProfessorIdAndEditalId(professor.getId(),
				relatorio.getEdital().getId());

		Projeto projeto = projetoEdital.getProjeto();

		ResRelatorio resRelatorio = new ResRelatorio();

		resRelatorio.setProjeto(projeto);
		resRelatorio.setProfessor(professor);
		resRelatorio.setRelatorio(relatorio);
		resRelatorio.setDescricao(descricao);

		resRelatorioRepository.save(resRelatorio);

		if (arquivos != null && Arrays.stream(arquivos).anyMatch(a -> !a.isEmpty())) {
			arquivoService.salvar(arquivos, null, null, null, resRelatorio);
		}

		log.info("Resposta registrada com sucesso.");
		return retornoUtils.retornoSucesso("Resposta registrada com sucesso.");
	}
}