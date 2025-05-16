package br.edu.undf.sga_ic.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final PasswordEncoder passwordEncoder;

	private final AlunoRepository alunoRepository;
	private final UsuarioRepository usuarioRepository;
	private final ProfessorRepository professorRepository;
	private final CoordenadorRepository coordenadorRepository;

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