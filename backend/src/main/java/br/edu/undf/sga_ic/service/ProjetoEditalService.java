package br.edu.undf.sga_ic.service;

import br.edu.undf.sga_ic.dto.req.ProjetoAlunoAdd;
import br.edu.undf.sga_ic.dto.req.ProjetoProfessorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.*;
import br.edu.undf.sga_ic.repository.ProjetoEditalRepository;
import br.edu.undf.sga_ic.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjetoEditalService {

    private final AlunoUtils alunoUtils;
    private final RetornoUtils retornoUtils;
    private final ProjetoUtils projetoUtils;
    private final ProfessorUtils professorUtils;
    private final CustomExceptionUtils customExceptionUtils;

    private final ProjetoEditalRepository projetoEditalRepository;
    private final UsuarioUtils usuarioUtils;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public ResponseEntity<Retorno> registrarAluno(@RequestBody ProjetoAlunoAdd projetoAlunoAdd) throws CustomException {

        Aluno aluno = alunoUtils.findById(projetoAlunoAdd.alunoId());

        Usuario usuario = usuarioUtils.findByAlunoId(projetoAlunoAdd.alunoId());

        Projeto projeto = projetoUtils.findById(projetoAlunoAdd.projetoId());

        Edital edital = projeto.getEdital();

        validaAlunoJaRegistradoEdital(aluno, edital);

        validaAlunoJaRegistrado(aluno, projeto);

        validaQtdTotalAlunoPermitido(edital, projeto);

        validaQtdBolsistaPermitido(edital, projeto, projetoAlunoAdd.isBolsista());

        validaQtdNaoBolsistaPermitido(edital, projeto, projetoAlunoAdd.isBolsista());

        ProjetoEdital projetoEdital = new ProjetoEdital();

        projetoEdital.setAluno(aluno);
        projetoEdital.setEdital(edital);
        projetoEdital.setProfessor(null);
        projetoEdital.setProjeto(projeto);
        projetoEdital.setUsuario(usuario);
        projetoEdital.setIsBolsista(projetoAlunoAdd.isBolsista());

        projetoEditalRepository.save(projetoEdital);

        String msg = String.format("Atribuindo aluno '%s' ao projeto '%s' com sucesso!", aluno.getNome(),
                projeto.getTitulo());

        log.info(msg);
        return retornoUtils.retornoSucesso(msg);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public ResponseEntity<Retorno> registrarProfessor(ProjetoProfessorAdd projetoProfessorAdd) throws CustomException {

        Professor professor = professorUtils.findById(projetoProfessorAdd.professorId());

        Usuario usuario = usuarioUtils.findByProfessorId(projetoProfessorAdd.professorId());

        Projeto projeto = projetoUtils.findById(projetoProfessorAdd.projetoId());

        Edital edital = projeto.getEdital();

        validaProfessorJaRegistradoEdital(professor, edital);

        validaProfessorJaRegistrado(professor, projeto);

        validaQtdTotalProfessorPermitido(edital, projeto);

        ProjetoEdital projetoEdital = new ProjetoEdital();

        projetoEdital.setUsuario(usuario);
        projetoEdital.setProfessor(professor);
        projetoEdital.setEdital(edital);
        projetoEdital.setProjeto(projeto);
        projetoEdital.setIsBolsista(null);
        projetoEdital.setAluno(null);

        projetoEditalRepository.save(projetoEdital);

        String msg = String.format("Atribuindo professor '%s' ao projeto '%s' com sucesso!", professor.getNome(),
                projeto.getTitulo());

        log.info(msg);
        return retornoUtils.retornoSucesso(msg);
    }

    public void validaQtdNaoBolsistaPermitido(Edital edital, Projeto projeto, Boolean isBolsista)
            throws CustomException {

        if (!Boolean.TRUE.equals(isBolsista)) {
            BigDecimal totalCadastrados = projetoEditalRepository.countByProjetoIdAndProfessorIsNull(projeto.getId());
            BigDecimal bolsistas = projetoEditalRepository
                    .countByProjetoIdAndProfessorIsNullAndIsBolsistaTrue(projeto.getId());
            BigDecimal naoBolsistas = totalCadastrados.subtract(bolsistas);
            BigDecimal limiteNaoBolsista = edital.getQtdAlunos().subtract(edital.getQtdBolsistas());

            if (naoBolsistas.compareTo(limiteNaoBolsista) >= 0) {
                String msg = String.format(
                        "O edital '%s' permite até %s alunos não bolsistas por projeto. O projeto '%s' já possui %s.",
                        edital.getTitulo(), limiteNaoBolsista, projeto.getTitulo(), naoBolsistas);

                log.info(msg);
                throw customExceptionUtils.errorAndBadRequest(msg);
            }
        }
    }

    public void validaQtdBolsistaPermitido(Edital edital, Projeto projeto, Boolean isBolsista) throws CustomException {

        if (Boolean.TRUE.equals(isBolsista)) {
            BigDecimal bolsistas = projetoEditalRepository
                    .countByProjetoIdAndProfessorIsNullAndIsBolsistaTrue(projeto.getId());

            if (bolsistas.compareTo(edital.getQtdBolsistas()) >= 0) {
                String msg = String.format(
                        "O edital '%s' permite até %s bolsistas por projeto. O projeto '%s' já possui %s.",
                        edital.getTitulo(), edital.getQtdBolsistas(), projeto.getTitulo(), bolsistas);

                log.info(msg);
                throw customExceptionUtils.errorAndBadRequest(msg);
            }
        }
    }

    public void validaQtdTotalAlunoPermitido(Edital edital, Projeto projeto) throws CustomException {

        BigDecimal totalCadastrados = projetoEditalRepository.countByProjetoIdAndProfessorIsNull(projeto.getId());

        if (totalCadastrados.compareTo(edital.getQtdAlunos()) >= 0) {
            String msg = String.format("O edital '%s' permite até %s alunos por projeto. O projeto '%s' já possui %s.",
                    edital.getTitulo(), edital.getQtdAlunos(), projeto.getTitulo(), totalCadastrados);

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }

    public void validaAlunoJaRegistrado(Aluno aluno, Projeto projeto) throws CustomException {

        if (projetoEditalRepository.existsByAlunoIdAndProjetoId(aluno.getId(), projeto.getId())) {
            String msg = String.format("O aluno '%s' já está registrado no projeto '%s'.", aluno.getNome(),
                    projeto.getTitulo());

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }

    public void validaAlunoJaRegistradoEdital(Aluno aluno, Edital edital) throws CustomException {

        if (projetoEditalRepository.existsByAlunoIdAndEditalId(aluno.getId(), edital.getId())) {
            String msg = String.format("O aluno '%s' já está registrado em um projeto do edital '%s'.", aluno.getNome(),
                    edital.getTitulo());

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }

    public void validaQtdTotalProfessorPermitido(Edital edital, Projeto projeto) throws CustomException {

        BigDecimal totalCadastrados = projetoEditalRepository.countByProjetoIdAndAlunoIsNull(projeto.getId());

        if (totalCadastrados.compareTo(edital.getQtdProfessores()) >= 0) {
            String msg = String.format(
                    "O edital '%s' permite até %s professores por projeto. O projeto '%s' já possui %s.",
                    edital.getTitulo(), edital.getQtdProfessores(), projeto.getTitulo(), totalCadastrados);

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }

    public void validaProfessorJaRegistrado(Professor professor, Projeto projeto) throws CustomException {

        if (projetoEditalRepository.existsByProfessorIdAndProjetoId(professor.getId(), projeto.getId())) {
            String msg = String.format("O professor '%s' já está registrado no projeto '%s'.", professor.getNome(),
                    projeto.getTitulo());

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }

    public void validaProfessorJaRegistradoEdital(Professor professor, Edital edital) throws CustomException {

        if (projetoEditalRepository.existsByProfessorIdAndEditalId(professor.getId(), edital.getId())) {
            String msg = String.format("O professor '%s' já está registrado em um projeto do edital '%s'.",
                    professor.getNome(), edital.getTitulo());

            log.info(msg);
            throw customExceptionUtils.errorAndBadRequest(msg);
        }
    }
}