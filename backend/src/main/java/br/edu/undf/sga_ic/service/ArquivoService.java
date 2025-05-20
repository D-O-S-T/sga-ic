package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.ArquivoRes;
import br.edu.undf.sga_ic.model.Arquivo;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.Relatorio;
import br.edu.undf.sga_ic.model.ResAtividade;
import br.edu.undf.sga_ic.model.ResRelatorio;
import br.edu.undf.sga_ic.repository.ArquivoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoService {

	private final ArquivoRepository arquivoRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void salvar(MultipartFile[] arquivos, Atividade atvd, ResAtividade resatvd, Relatorio rltr,
			ResRelatorio resrltr) throws IOException {

		Atividade atividade = atvd != null ? atvd : null;
		ResAtividade resAtividade = resatvd != null ? resatvd : null;

		Relatorio relatorio = rltr != null ? rltr : null;
		ResRelatorio resRelatorio = resrltr != null ? resrltr : null;

		for (MultipartFile arquivo : arquivos) {

			Arquivo novoArquivo = new Arquivo();

			novoArquivo.setAtividade(atividade);
			novoArquivo.setResAtividade(resAtividade);
			novoArquivo.setRelatorio(relatorio);
			novoArquivo.setResRelatorio(resRelatorio);
			novoArquivo.setBytesArquivo(arquivo.getBytes());
			novoArquivo.setTipoArquivo(arquivo.getContentType());
			novoArquivo.setNomeArquivo(arquivo.getOriginalFilename());

			arquivoRepository.save(novoArquivo);
		}
	}

	public List<ArquivoRes> findArquivosByAtividade(Long atividadeId) {

		List<Arquivo> arquivos = arquivoRepository.findByAtividadeId(atividadeId);

		return mapArquivoToDTO(arquivos);
	}

	public List<ArquivoRes> findArquivosByResposta(Long resAtividadeId) {
		List<Arquivo> arquivos = arquivoRepository.findByResAtividadeId(resAtividadeId);
		return mapArquivoToDTO(arquivos);
	}

	private List<ArquivoRes> mapArquivoToDTO(List<Arquivo> arquivos) {
		return arquivos.stream()
				.map(arquivo -> ArquivoRes.builder().id(arquivo.getId()).nomeArquivo(arquivo.getNomeArquivo())
						.arquivo("data:" + arquivo.getTipoArquivo() + ";base64,"
								+ Base64.getEncoder().encodeToString(arquivo.getBytesArquivo()))
						.build())
				.collect(Collectors.toList());
	}
}