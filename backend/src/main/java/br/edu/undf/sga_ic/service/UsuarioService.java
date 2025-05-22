package br.edu.undf.sga_ic.service;

import br.edu.undf.sga_ic.dto.res.Perfil;
import br.edu.undf.sga_ic.dto.res.PerfilCheio;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.model.Coordenador;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.AlunoRepository;
import br.edu.undf.sga_ic.repository.CoordenadorRepository;
import br.edu.undf.sga_ic.repository.ProfessorRepository;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioUtils usuarioUtils;
    private final RetornoUtils retornoUtils;

    private final AlunoRepository alunoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProfessorRepository professorRepository;
    private final CoordenadorRepository coordenadorRepository;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public ResponseEntity<Retorno> uploadFoto(MultipartFile foto, HttpServletRequest request)
            throws CustomException, IOException {

        Usuario usuario = usuarioUtils.findByToken(request);

        switch (usuario.getUsuarioRole()) {
            case ALUNO:

                Aluno aluno = usuario.getAluno();

                aluno.setFotoPerfil(foto.getBytes());

                alunoRepository.save(aluno);

                break;
            case PROFESSOR:

                Professor professor = usuario.getProfessor();

                professor.setFotoPerfil(foto.getBytes());

                professorRepository.save(professor);

                break;
            case COORDENADOR:

                Coordenador coordenador = usuario.getCoordenador();

                coordenador.setFotoPerfil(foto.getBytes());

                coordenadorRepository.save(coordenador);

                break;
        }

        log.info("Usuário alterou sua foto com sucesso.");
        return retornoUtils.retornoSucesso("Usuário alterou sua foto com sucesso.");
    }

    public Perfil getPerfil(HttpServletRequest request)
            throws CustomException {

        Usuario usuario = usuarioUtils.findByToken(request);

        Perfil perfil = switch (usuario.getUsuarioRole()) {
            case ALUNO -> {

                Aluno aluno = usuario.getAluno();

                yield Perfil.builder().id(aluno.getId()).nome(aluno.getNome()).fotoPerfil(aluno.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(aluno.getFotoPerfil())
                        : null).build();
            }
            case PROFESSOR -> {

                Professor professor = usuario.getProfessor();

                yield Perfil.builder().id(professor.getId()).nome(professor.getNome()).fotoPerfil(professor.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(professor.getFotoPerfil())
                        : null).build();
            }
            case COORDENADOR -> {

                Coordenador coordenador = usuario.getCoordenador();

                yield Perfil.builder().id(coordenador.getId()).nome(coordenador.getNome()).fotoPerfil(coordenador.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coordenador.getFotoPerfil())
                        : null).build();
            }
        };

        log.info("Retornando perfil de Uusário com sucesso.");
        return perfil;
    }

    public PerfilCheio getPerfilCheio(HttpServletRequest request)
            throws CustomException {

        Usuario usuario = usuarioUtils.findByToken(request);

        PerfilCheio perfilCheio = switch (usuario.getUsuarioRole()) {
            case ALUNO -> {

                Aluno aluno = usuario.getAluno();

                yield PerfilCheio.builder()
                        .id(aluno.getId())
                        .nome(aluno.getNome())
                        .cpf(usuario.getCpf())
                        .email(aluno.getEmail())
                        .celular(aluno.getCelular())
                        .curriculoLattes(aluno.getCurriculoLattes())
                        .fotoPerfil(aluno.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(aluno.getFotoPerfil())
                        : null)
                        .build();
            }
            case PROFESSOR -> {

                Professor professor = usuario.getProfessor();

                yield PerfilCheio.builder()
                        .id(professor.getId())
                        .nome(professor.getNome())
                        .cpf(usuario.getCpf())
                        .email(professor.getEmail())
                        .celular(professor.getCelular())
                        .curriculoLattes(professor.getCurriculoLattes())
                        .fotoPerfil(professor.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(professor.getFotoPerfil())
                        : null)
                        .build();
            }
            case COORDENADOR -> {

                Coordenador coordenador = usuario.getCoordenador();

                yield PerfilCheio.builder()
                        .id(coordenador.getId())
                        .nome(coordenador.getNome())
                        .cpf(usuario.getCpf())
                        .email(coordenador.getEmail())
                        .celular(coordenador.getCelular())
                        .curriculoLattes(null)
                        .fotoPerfil(coordenador.getFotoPerfil() != null
                        ? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coordenador.getFotoPerfil())
                        : null)
                        .build();
            }
        };

        log.info("Retornando perfil cheio de Uusário com sucesso.");
        return perfilCheio;
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void cadastrarUsuario(Long id, String cpf, UsuarioRole usuarioRole) {

        Usuario usuario = new Usuario();

        switch (usuarioRole) {
            case ALUNO:

                Aluno aluno = alunoRepository.findById(id).orElse(null);

                usuario.setCpf(cpf);
                usuario.setAluno(aluno);
                usuario.setUsuarioRole(UsuarioRole.ALUNO);
                usuario.setSenhaHash(passwordEncoder.encode("12345"));

                usuarioRepository.save(usuario);

                break;
            case PROFESSOR:

                Professor professor = professorRepository.findById(id).orElse(null);

                usuario.setCpf(cpf);
                usuario.setProfessor(professor);
                usuario.setUsuarioRole(UsuarioRole.PROFESSOR);
                usuario.setSenhaHash(passwordEncoder.encode("12345"));

                usuarioRepository.save(usuario);

                break;
            case COORDENADOR:

                Coordenador coordenador = coordenadorRepository.findById(id).orElse(null);

                usuario.setCpf(cpf);
                usuario.setCoordenador(coordenador);
                usuario.setUsuarioRole(UsuarioRole.COORDENADOR);
                usuario.setSenhaHash(passwordEncoder.encode("12345"));

                usuarioRepository.save(usuario);

                break;
        }
    }
}