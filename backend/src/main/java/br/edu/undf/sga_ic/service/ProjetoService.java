package br.edu.undf.sga_ic.service;

import br.edu.undf.sga_ic.dto.req.ProjetoAdd;
import br.edu.undf.sga_ic.dto.res.*;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.*;
import br.edu.undf.sga_ic.repository.*;
import br.edu.undf.sga_ic.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

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
    private final AtividadeRepository atividadeRepository;
    private final RelatorioRepository relatorioRepository;
    private final ResAtividadeRepository resAtividadeRepository;
    private final ResRelatorioRepository resRelatorioRepository;
    private final ProjetoEditalRepository projetoEditalRepository;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
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

    public ResponseEntity<Retorno> deletar(@PathVariable Long projetoId) throws CustomException {

        Projeto projeto = projetoUtils.findById(projetoId);

        projetoRepository.delete(projeto);

        log.info(" >>> Projeto deletado com sucesso.");
        return retornoUtils.retornoSucesso("Projeto deletado com sucesso.");
    }

    public ResponseEntity<Retorno> editar(Long projetoId, ProjetoAdd projetoAdd) throws CustomException {

        Projeto projeto = projetoUtils.findById(projetoId);

        Edital edital = editalUtils.findById(projetoAdd.editalId());

        validaMaxProjetosByEdital(edital);

        projeto.setEdital(edital);
        projeto.setTitulo(projetoAdd.titulo());
        projeto.setDescricao(projetoAdd.descricao());

        projetoRepository.save(projeto);

        log.info(" >>> Projeto editado com sucesso.");
        return retornoUtils.retornoSucesso("Projeto editado com sucesso.");
    }

    public Participantes findParticipantes(Long projetoId) throws CustomException {

        Projeto projeto = projetoUtils.findById(projetoId);

        List<ProjetoEdital> relProfessores = projetoEditalRepository.findByProjetoIdAndAlunoIsNull(projetoId);

        List<ProjetoEdital> relBolsistas = projetoEditalRepository.findByProjetoIdAndProfessorIsNullAndIsBolsistaIsTrue(projetoId);

        List<ProjetoEdital> relNaoBolsistas = projetoEditalRepository.findByProjetoIdAndProfessorIsNullAndIsBolsistaIsFalse(projetoId);

        log.info("total professor={}", relProfessores.size());
        log.info("total bolsista={}", relBolsistas.size());
        log.info("total naoBolsista={}", relNaoBolsistas.size());

        List<ProfessorResShort> professores =
                relProfessores.stream()
                        .map(rel -> ProfessorResShort.builder()
                                .id(rel.getId())
                                .nome(rel.getProfessor().getNome())
                                .cpf(rel.getUsuario().getCpf())            // << aqui!
                                .fotoPerfil(rel.getProfessor().getFotoPerfil() != null
                                        ? "data:image/jpeg;base64," +
                                        Base64.getEncoder().encodeToString(rel.getProfessor().getFotoPerfil())
                                        : null)
                                .build())
                        .toList();

        List<AlunoResShort> bolsistas =
                relBolsistas.stream()
                        .map(rel -> AlunoResShort.builder()
                                .id(rel.getId())
                                .nome(rel.getAluno().getNome())
                                .cpf(rel.getUsuario().getCpf())            // idem
                                .fotoPerfil(rel.getAluno().getFotoPerfil() != null
                                        ? "data:image/jpeg;base64," +
                                        Base64.getEncoder().encodeToString(rel.getAluno().getFotoPerfil())
                                        : null)
                                .build())
                        .toList();

        List<AlunoResShort> naoBolsistas =
                relNaoBolsistas.stream()
                        .map(rel -> AlunoResShort.builder()
                                .id(rel.getId())
                                .nome(rel.getAluno().getNome())
                                .cpf(rel.getUsuario().getCpf())            // idem
                                .fotoPerfil(rel.getAluno().getFotoPerfil() != null
                                        ? "data:image/jpeg;base64," +
                                        Base64.getEncoder().encodeToString(rel.getAluno().getFotoPerfil())
                                        : null)
                                .build())
                        .toList();

        Participantes participantes = Participantes.builder().professores(professores).bolsistas(bolsistas).naoBolsistas(naoBolsistas).build();

        log.info(" >>> Retornando participantes com sucesso.");
        return participantes;
    }

    public Metricas findMetricas(Long projetoId) throws CustomException {

        Projeto projeto = projetoUtils.findById(projetoId);

        long qtdProfessores = projetoEditalRepository.countByProjetoIdAndProfessorIsNotNull(projetoId);
        long qtdBolsistas = projetoEditalRepository.countByProjetoIdAndAlunoIsNotNullAndIsBolsistaTrue(projetoId);
        long qtdNaoBolsitas = projetoEditalRepository.countByProjetoIdAndAlunoIsNotNullAndIsBolsistaFalse(projetoId);

        long qtdAtividades = atividadeRepository.countByProjetoId(projetoId);
        long qtdResAtividades = resAtividadeRepository.countByProjetoId(projetoId);

        long qtdRelatorios = relatorioRepository.countByEditalId(projeto.getEdital().getId());
        long qtdResRelatotios = resRelatorioRepository.countByProjetoId(projetoId);

        return Metricas.builder()
                .qtdProfessores(qtdProfessores)
                .qtdBolsistas(qtdBolsistas)
                .qtdNaoBolsistas(qtdNaoBolsitas)
                .qtdAtividades(qtdAtividades)
                .qtdResAtividades(qtdResAtividades)
                .qtdRelatorios(qtdRelatorios)
                .qtdResRelatorios(qtdResRelatotios)
                .build();
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