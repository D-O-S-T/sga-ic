package br.edu.undf.sga_ic.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.CoordenadorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Coordenador;
import br.edu.undf.sga_ic.repository.CoordenadorRepository;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordenadorService {

	private final RetornoUtils retornoUtils;

	private final UsuarioService usuarioService;

	private final CoordenadorRepository coordenadorRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(CoordenadorAdd coordenadorAdd) throws CustomException {

		Coordenador coordenador = salvar(coordenadorAdd);

		usuarioService.cadastrarUsuario(coordenador.getId(), coordenadorAdd.cpf(), UsuarioRole.COORDENADOR);

		log.info("Coordenador registrado com sucesso.");
		return retornoUtils.retornoSucesso("Coordenador registrado com sucesso.");
	}

	public Coordenador salvar(CoordenadorAdd coordenadorAdd) {

		Coordenador coordenador = new Coordenador();

		coordenador.setNome(coordenadorAdd.nome());
		coordenador.setEmail(coordenadorAdd.email());
		coordenador.setCelular(coordenadorAdd.celular());

		return coordenadorRepository.save(coordenador);
	}
}