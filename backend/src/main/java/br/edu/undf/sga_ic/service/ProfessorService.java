package br.edu.undf.sga_ic.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.ProfessorAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Professor;
import br.edu.undf.sga_ic.repository.ProfessorRepository;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfessorService {

	private final RetornoUtils retornoUtils;

	private final UsuarioService usuarioService;

	private final ProfessorRepository professorRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(ProfessorAdd professorAdd) throws CustomException {

		Professor professor = salvar(professorAdd);

		usuarioService.cadastrarUsuario(professor.getId(), professorAdd.cpf(), UsuarioRole.PROFESSOR);

		log.info("Professor registrado com sucesso.");
		return retornoUtils.retornoSucesso("Professor registrado com sucesso.");
	}

	public Professor salvar(ProfessorAdd professorAdd) {

		Professor professor = new Professor();

		professor.setNome(professorAdd.nome());
		professor.setEmail(professorAdd.email());
		professor.setCelular(professorAdd.celular());
		professor.setCurriculoLattes(professorAdd.curriculoLattes());

		return professorRepository.save(professor);
	}
}