package br.edu.undf.sga_ic.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Coordenador;
import br.edu.undf.sga_ic.model.Edital;
import br.edu.undf.sga_ic.model.Relatorio;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.RelatorioRepository;
import br.edu.undf.sga_ic.utils.EditalUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatorioService {

	private final EditalUtils editalUtils;
	private final UsuarioUtils usuarioUtils;
	private final RetornoUtils retornoUtils;

	private final ArquivoService arquivoService;

	private final RelatorioRepository relatorioRepository;

	public ResponseEntity<Retorno> registrar(String titulo, String descricao, LocalDateTime dataAbertura,
			LocalDateTime dataEncerramento, Long editalId, MultipartFile[] arquivos, HttpServletRequest request)
			throws CustomException, IOException {

		Usuario usuario = usuarioUtils.findByToken(request);

		Coordenador coordenador = usuario.getCoordenador();

		Edital edital = editalUtils.findById(editalId);

		Relatorio relatorio = new Relatorio();

		relatorio.setEdital(edital);
		relatorio.setTitulo(titulo);
		relatorio.setDescricao(descricao);
		relatorio.setCoordenador(coordenador);
		relatorio.setDataAbertura(dataAbertura);
		relatorio.setDataEncerramento(dataEncerramento);
		relatorio.setDataRegistro(LocalDateTime.now());

		relatorioRepository.save(relatorio);

		if (arquivos != null && Arrays.stream(arquivos).anyMatch(a -> !a.isEmpty())) {
			arquivoService.salvar(arquivos, null, null, relatorio, null);
		}

		log.info("Relatorio registrado com sucesso.");
		return retornoUtils.retornoSucesso("Relatorio registrado com sucesso.");
	}
}