package br.edu.undf.sga_ic.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.CoordenadorAdd;
import br.edu.undf.sga_ic.dto.res.CoordenadorRes;
import br.edu.undf.sga_ic.dto.res.CoordenadorResShort;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Coordenador;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.CoordenadorRepository;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import br.edu.undf.sga_ic.utils.EmptyUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoordenadorService {

	private final EmptyUtils emptyUtils;
	private final UsuarioUtils usuarioUtils;
	private final RetornoUtils retornoUtils;

	private final UsuarioService usuarioService;

	private final UsuarioRepository usuarioRepository;
	private final CoordenadorRepository coordenadorRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(CoordenadorAdd coordenadorAdd) throws CustomException {

		Coordenador coordenador = salvar(coordenadorAdd, null);

		usuarioService.cadastrarUsuario(coordenador.getId(), coordenadorAdd.cpf(), UsuarioRole.COORDENADOR);

		log.info("Coordenador registrado com sucesso.");
		return retornoUtils.retornoSucesso("Coordenador registrado com sucesso.");
	}

	public List<CoordenadorResShort> findAll() throws CustomException {

		List<Usuario> usuarios = usuarioRepository.findByCoordenadorIsNotNull();

		emptyUtils.validaListaVazia(usuarios, "Não foram encontrados Professores cadastrados.");

		log.info("Retornando lista de Alunos com sucesso.");
		return mapCoordenadorToDTO(usuarios);
	}

	public CoordenadorRes findById(Long coordenadorId) throws CustomException {

		Usuario usuario = usuarioUtils.findByCoordenadorId(coordenadorId);

		Coordenador coordenador = usuario.getCoordenador();

		CoordenadorRes coordenadorDTO = CoordenadorRes.builder().id(coordenador.getId()).nome(coordenador.getNome())
				.cpf(usuario.getCpf()).email(coordenador.getEmail()).celular(coordenador.getCelular())
				.fotoPerfil(coordenador.getFotoPerfil() != null
						? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(coordenador.getFotoPerfil())
						: null)
				.build();

		log.info("Retornando Aluno pelo id com sucesso.");
		return coordenadorDTO;
	}

	public ResponseEntity<Retorno> deletar(Long coordenadorId) throws CustomException {

		Usuario usuario = usuarioUtils.findByCoordenadorId(coordenadorId);

		Coordenador coordenador = usuario.getCoordenador();

		usuarioRepository.delete(usuario);
		coordenadorRepository.delete(coordenador);

		log.info(" >>> Coordenador deletado com sucesso.");
		return retornoUtils.retornoSucesso("Coordenador deletado com sucesso.");
	}

	public ResponseEntity<Retorno> editar(Long coordenadorId, CoordenadorAdd coordenadorAdd) throws CustomException {

		Usuario usuario = usuarioUtils.findByCoordenadorId(coordenadorId);

		usuario.setCpf(coordenadorAdd.cpf());
		usuarioRepository.save(usuario);

		salvar(coordenadorAdd, usuario);

		log.info(" >>> Coordenador editado com sucesso.");
		return retornoUtils.retornoSucesso("Coordenador editado com sucesso.");
	}

	private List<CoordenadorResShort> mapCoordenadorToDTO(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> CoordenadorResShort.builder().id(usuario.getCoordenador().getId())
						.nome(usuario.getCoordenador().getNome()).cpf(usuario.getCpf())
						.fotoPerfil(usuario.getCoordenador().getFotoPerfil() != null
								? "data:image/jpeg;base64,"
										+ Base64.getEncoder().encodeToString(usuario.getCoordenador().getFotoPerfil())
								: null)
						.build())
				.collect(Collectors.toList());
	}

	public Coordenador salvar(CoordenadorAdd coordenadorAdd, Usuario usuario) {

		Coordenador coordenador;

		if (usuario == null) {
			coordenador = new Coordenador();
		} else {
			coordenador = usuario.getCoordenador();
		}

		coordenador.setNome(coordenadorAdd.nome());
		coordenador.setEmail(coordenadorAdd.email());
		coordenador.setCelular(coordenadorAdd.celular());

		return coordenadorRepository.save(coordenador);
	}
}