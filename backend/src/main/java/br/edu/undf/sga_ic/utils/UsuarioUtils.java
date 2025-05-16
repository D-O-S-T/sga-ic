package br.edu.undf.sga_ic.utils;

import org.springframework.stereotype.Component;

import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UsuarioUtils {

	private final CustomExceptionUtils customExceptionUtils;

	private final UsuarioRepository usuarioRepository;

	public Usuario findByCpf(String cpf) throws CustomException {
		return usuarioRepository.findByCpf(cpf)
				.orElseThrow(() -> customExceptionUtils.errorAndNotFound("Usuário informado não encontrado"));
	}
}