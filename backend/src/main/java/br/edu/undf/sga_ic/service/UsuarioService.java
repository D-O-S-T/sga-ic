package br.edu.undf.sga_ic.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void cadastrarUsuario(Long id, String cpf, UsuarioRole usuarioRole) throws CustomException {

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