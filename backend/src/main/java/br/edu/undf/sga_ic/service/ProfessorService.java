package br.edu.undf.sga_ic.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.ProfessorAdd;
import br.edu.undf.sga_ic.dto.res.ProfessorRes;
import br.edu.undf.sga_ic.dto.res.ProfessorResShort;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.model.Usuario;
import br.edu.undf.sga_ic.repository.ProfessorRepository;
import br.edu.undf.sga_ic.repository.UsuarioRepository;
import br.edu.undf.sga_ic.utils.EmptyUtils;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import br.edu.undf.sga_ic.utils.UsuarioUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessorService {

	private final EmptyUtils emptyUtils;
	private final UsuarioUtils usuarioUtils;
	private final RetornoUtils retornoUtils;

	private final UsuarioService usuarioService;

	private final UsuarioRepository usuarioRepository;
	private final ProfessorRepository professorRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(ProfessorAdd professorAdd) throws CustomException {

		Professor professor = salvar(professorAdd, null);

		usuarioService.cadastrarUsuario(professor.getId(), professorAdd.cpf(), UsuarioRole.PROFESSOR);

		log.info("Professor registrado com sucesso.");
		return retornoUtils.retornoSucesso("Professor registrado com sucesso.");
	}

	public List<ProfessorResShort> findAll() throws CustomException {

		List<Usuario> usuarios = usuarioRepository.findByProfessorIsNotNull();

		emptyUtils.validaListaVazia(usuarios, "Não foram encontrados Professores cadastrados.");

		log.info("Retornando lista de Alunos com sucesso.");
		return mapProfessorToDTO(usuarios);
	}

	public ProfessorRes findById(Long professorId) throws CustomException {

		Usuario usuario = usuarioUtils.findByProfessorId(professorId);

		Professor professor = usuario.getProfessor();

		ProfessorRes professorDTO = ProfessorRes.builder().id(professor.getId()).nome(professor.getNome())
				.cpf(usuario.getCpf()).email(professor.getEmail()).celular(professor.getCelular())
				.curriculoLattes(professor.getCurriculoLattes())
				.fotoPerfil(professor.getFotoPerfil() != null
						? "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(professor.getFotoPerfil())
						: null)
				.build();

		log.info("Retornando Aluno pelo id com sucesso.");
		return professorDTO;
	}

	public ResponseEntity<Retorno> deletar(Long professorId) throws CustomException {

		Usuario usuario = usuarioUtils.findByProfessorId(professorId);

		Professor professor = usuario.getProfessor();

		usuarioRepository.delete(usuario);
		professorRepository.delete(professor);

		log.info(" >>> Professor deletado com sucesso.");
		return retornoUtils.retornoSucesso("Professor deletado com sucesso.");
	}

	public ResponseEntity<Retorno> editar(Long professorId, ProfessorAdd professorAdd) throws CustomException {

		Usuario usuario = usuarioUtils.findByProfessorId(professorId);

		usuario.setCpf(professorAdd.cpf());
		usuarioRepository.save(usuario);

		salvar(professorAdd, usuario);

		log.info(" >>> Professor editado com sucesso.");
		return retornoUtils.retornoSucesso("Professor editado com sucesso.");
	}

	private List<ProfessorResShort> mapProfessorToDTO(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> ProfessorResShort.builder().id(usuario.getProfessor().getId())
						.nome(usuario.getProfessor().getNome()).cpf(usuario.getCpf())
						.fotoPerfil(usuario.getProfessor().getFotoPerfil() != null
								? "data:image/jpeg;base64,"
										+ Base64.getEncoder().encodeToString(usuario.getProfessor().getFotoPerfil())
								: null)
						.build())
				.collect(Collectors.toList());
	}

	public Professor salvar(ProfessorAdd professorAdd, Usuario usuario) {

		Professor professor;

		if (usuario == null) {
			professor = new Professor();
		} else {
			professor = usuario.getProfessor();
		}

		professor.setNome(professorAdd.nome());
		professor.setEmail(professorAdd.email());
		professor.setCelular(professorAdd.celular());
		professor.setCurriculoLattes(professorAdd.curriculoLattes());

		return professorRepository.save(professor);
	}
}