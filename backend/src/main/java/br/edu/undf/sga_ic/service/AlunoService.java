package br.edu.undf.sga_ic.service;

import br.edu.undf.sga_ic.dto.req.AlunoAdd;
import br.edu.undf.sga_ic.dto.res.AlunoRes;
import br.edu.undf.sga_ic.dto.res.AlunoResShort;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.AlunoRepository;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import br.edu.undf.sga_ic.utils.EmptyUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Base64;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlunoService {

	private final EmptyUtils emptyUtils;
	private final RetornoUtils retornoUtils;
	private final UsuarioUtils usuarioUtils;

	private final UsuarioService usuarioService;

	private final AlunoRepository alunoRepository;
	private final UsuarioRepository usuarioRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(AlunoAdd alunoAdd) throws CustomException {

		Aluno aluno = salvar(alunoAdd, null);

		usuarioService.cadastrarUsuario(aluno.getId(), alunoAdd.cpf(), UsuarioRole.ALUNO);

		log.info(" >>> Aluno registrado com sucesso.");
		return retornoUtils.retornoSucesso("Aluno registrado com sucesso.");
	}

	public List<AlunoResShort> findAll() throws CustomException {

		List<Usuario> usuarios = usuarioRepository.findByAlunoIsNotNull();

		emptyUtils.validaListaVazia(usuarios, "Não foram encontrados Alunos cadastrados.");

		log.info(" >>> Retornando lista de Alunos com sucesso.");
		return mapAlunoToDTO(usuarios);
	}

	public AlunoRes findById(Long alunoId) throws CustomException {

		Usuario usuario = usuarioUtils.findByAlunoId(alunoId);

		Aluno aluno = usuario.getAluno();

		AlunoRes alunoDTO = AlunoRes.builder().id(aluno.getId()).nome(aluno.getNome()).cpf(usuario.getCpf())
				.email(aluno.getEmail()).celular(aluno.getCelular()).curriculoLattes(aluno.getCurriculoLattes())
				.fotoPerfil(aluno.getFotoPerfil() != null
						? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(aluno.getFotoPerfil())
						: null)
				.build();

		log.info(" >>> Retornando Aluno pelo id com sucesso.");
		return alunoDTO;
	}

	public ResponseEntity<Retorno> deletar(@PathVariable Long alunoId) throws CustomException {

		Usuario usuario = usuarioUtils.findByAlunoId(alunoId);

		Aluno aluno = usuario.getAluno();

		usuarioRepository.delete(usuario);
		alunoRepository.delete(aluno);

		log.info(" >>> Aluno deletado com sucesso.");
		return retornoUtils.retornoSucesso("Aluno deletado com sucesso.");
	}

	public ResponseEntity<Retorno> editar(Long alunoId, AlunoAdd alunoAdd) throws CustomException {

		Usuario usuario = usuarioUtils.findByAlunoId(alunoId);

		usuario.setCpf(alunoAdd.cpf());
		usuarioRepository.save(usuario);

		salvar(alunoAdd, usuario);

		log.info(" >>> Aluno editado com sucesso.");
		return retornoUtils.retornoSucesso("Aluno editado com sucesso.");
	}

	private List<AlunoResShort> mapAlunoToDTO(List<Usuario> usuarios) {
		return usuarios.stream().map(usuario -> AlunoResShort.builder().id(usuario.getAluno().getId())
				.nome(usuario.getAluno().getNome()).cpf(usuario.getCpf())
				.fotoPerfil(usuario.getAluno().getFotoPerfil() != null ? "data:image/jpeg;base64,"
						+ Base64.getEncoder().encodeToString(usuario.getAluno().getFotoPerfil()) : null)
				.build()).toList();
	}

	public Aluno salvar(AlunoAdd alunoAdd, Usuario usuario) {

		Aluno aluno;

		if (usuario == null) {
			aluno = new Aluno();
		} else {
			aluno = usuario.getAluno();
		}

		aluno.setNome(alunoAdd.nome());
		aluno.setEmail(alunoAdd.email());
		aluno.setCelular(alunoAdd.celular());
		aluno.setCurriculoLattes(alunoAdd.curriculoLattes());

		return alunoRepository.save(aluno);
	}
}