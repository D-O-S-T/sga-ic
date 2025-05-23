package br.edu.undf.sga_ic.service;

import br.edu.undf.sga_ic.dto.res.*;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.*;
import br.edu.undf.sga_ic.repository.RelatorioRepository;
import br.edu.undf.sga_ic.repository.ResRelatorioRepository;
import br.edu.undf.sga_ic.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final DateUtils dateUtils;
    private final EmptyUtils emptyUtils;
    private final EditalUtils editalUtils;
    private final UsuarioUtils usuarioUtils;
    private final RetornoUtils retornoUtils;
    private final ProjetoUtils projetoUtils;
    private final RelatorioUtils relatorioUtils;

    private final ArquivoService arquivoService;

    private final RelatorioRepository relatorioRepository;
    private final ResRelatorioRepository resRelatorioRepository;

    public ResponseEntity<Retorno> registrar(String titulo, String descricao, LocalDate dataAbertura,
                                             LocalDate dataEncerramento, Long editalId, MultipartFile[] arquivos, HttpServletRequest request)
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

    public List<RelatorioRes> findByprojeto(Long projetoId) throws CustomException {

        Projeto projeto = projetoUtils.findById(projetoId);

        Edital edital = projeto.getEdital();

        List<Relatorio> relatorios = relatorioRepository.findByEditalIdOrderByDataRegistroDesc(edital.getId());

        emptyUtils.validaListaVazia(relatorios, "Não foram encontrados Relatorios registradas para esse projeto.");

        log.info(">>> Retornando lista de relatorios com sucesso para o projeto com ID: {}", projeto.getId());
        return mapRelatoriosToDTO(relatorios);
    }

    public RelatorioResBig findById(Long relatorioId) throws CustomException {

        Relatorio relatorio = relatorioUtils.findById(relatorioId);

        CoordenadorShort coordenadorDTO = CoordenadorShort.builder().id(relatorio.getCoordenador().getId())
                .nome(relatorio.getCoordenador().getNome()).build();

        List<ArquivoRes> arquivosAtividade = arquivoService.findArquivosByRelatorio(relatorioId);

        List<ResRelatorioResBig> respostasDTO = resRelatorioRepository.findByRelatorioId(relatorioId).stream()
                .map(res -> {

                    ProfessorShort professorDTO = ProfessorShort.builder()
                            .id(res.getProfessor().getId())
                            .nome(res.getProfessor().getNome())
                            .build();

                    List<ArquivoRes> arquivosResposta = arquivoService.findArquivosByRespostaRelatorio(res.getId());

                    return ResRelatorioResBig.builder()
                            .id(res.getId())
                            .descricao(res.getDescricao())
                            .professor(professorDTO)
                            .arquivosResposta(arquivosResposta)
                            .build();

                }).collect(Collectors.toList());

        return RelatorioResBig.builder()
                .id(relatorio.getId())
                .titulo(relatorio.getTitulo())
                .descricao(relatorio.getDescricao())
                .dataRegistro(dateUtils.formatarDataHora(relatorio.getDataRegistro()))
                .dataAbertura(dateUtils.formatarData(relatorio.getDataAbertura()))
                .dataEncerramento(dateUtils.formatarData(relatorio.getDataEncerramento()))
                .coordenador(coordenadorDTO)
                .arquivosRelatorio(arquivosAtividade)
                .repostas(respostasDTO)
                .build();
    }

    private List<RelatorioRes> mapRelatoriosToDTO(List<Relatorio> relatorios) {
        return relatorios.stream()
                .map(relatorio -> RelatorioRes.builder()
                        .id(relatorio.getId())
                        .titulo(relatorio.getTitulo())
                        .descricao(relatorio.getDescricao())
                        .dataRegistro(dateUtils.formatarDataHora(relatorio.getDataRegistro()))
                        .dataAbertura(dateUtils.formatarData(relatorio.getDataAbertura()))
                        .dataEncerramento(dateUtils.formatarData(relatorio.getDataEncerramento()))
                        .build())
                .toList();
    }
}