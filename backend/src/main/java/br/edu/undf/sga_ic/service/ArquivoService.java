package br.edu.undf.sga_ic.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.model.Arquivo;
import br.edu.undf.sga_ic.model.Atividade;
import br.edu.undf.sga_ic.model.Resposta;
import br.edu.undf.sga_ic.repository.ArquivoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoService {

	private final ArquivoRepository arquivoRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void salvar(MultipartFile[] arquivos, Atividade atividade, Resposta resposta) throws IOException {

		Resposta rspt = resposta != null ? resposta : null;
		Atividade atvd = atividade != null ? atividade : null;

		for (MultipartFile arquivo : arquivos) {

			Arquivo novoArquivo = new Arquivo();

			novoArquivo.setResposta(rspt);
			novoArquivo.setAtividade(atvd);
			novoArquivo.setBytesArquivo(arquivo.getBytes());
			novoArquivo.setTipoArquivo(arquivo.getContentType());
			novoArquivo.setNomeArquivo(arquivo.getOriginalFilename());

			arquivoRepository.save(novoArquivo);
		}
	}
}