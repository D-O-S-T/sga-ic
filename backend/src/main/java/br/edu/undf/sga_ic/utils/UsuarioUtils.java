package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.infra.security.JwtService;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final JwtService jwtService;

	private final UsuarioRepository usuarioRepository;

	public Usuario findById(Long id) throws CustomException {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Usuário informado não encontrado"));
	}

	public Usuario findByAlunoId(Long alunoId) throws CustomException {
		return usuarioRepository.findByAlunoId(alunoId)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Usuário - Aluno informado não encontrado"));
	}

	public Usuario findByProfessorId(Long professorId) throws CustomException {
		return usuarioRepository.findByProfessorId(professorId).orElseThrow(
				() -> customExceptionUtils.errorAndNotFound("Usuário - Professor informado não encontrado"));
	}

	public Usuario findByCoordenadorId(Long coordenadorId) throws CustomException {
		return usuarioRepository.findByCoordenadorId(coordenadorId).orElseThrow(
				() -> customExceptionUtils.errorAndNotFound("Usuário - Coordenador informado não encontrado"));
	}

	public Usuario findByCpf(String cpf) throws CustomException {
		return usuarioRepository.findByCpf(cpf)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Usuário informado não encontrado"));
	}

	public Usuario findByToken(HttpServletRequest httpServletRequest) throws CustomException {

		String token = jwtService.getJwtFromCookies(httpServletRequest);

		if (token == null || token.isEmpty()) {
			throw customExceptionUtils.errorAndUnauthorized("Token jwt não encontrado na requisição.");
		}

		if (!jwtService.validateJwtToken(token)) {
			throw customExceptionUtils.errorAndUnauthorized("Token jwt não é valido.");
		}

		Long id = jwtService.getUsuarioIdFromJwtToken(token);
		Usuario usuario = findById(id);

		return usuario;
	}
}