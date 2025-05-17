package br.edu.undf.sga_ic.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.undf.sga_ic.dto.req.AlunoAdd;
import br.edu.undf.sga_ic.dto.res.Retorno;
import br.edu.undf.sga_ic.enums.UsuarioRole;
import br.edu.undf.sga_ic.exception.CustomException;
import br.edu.undf.sga_ic.model.Aluno;
import br.edu.undf.sga_ic.repository.AlunoRepository;
import br.edu.undf.sga_ic.utils.RetornoUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlunoService {

	private final RetornoUtils retornoUtils;

	private final UsuarioService usuarioService;

	private final AlunoRepository alunoRepository;

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public ResponseEntity<Retorno> registrar(AlunoAdd alunoAdd) throws CustomException {

		Aluno aluno = salvar(alunoAdd);

		usuarioService.cadastrarUsuario(aluno.getId(), alunoAdd.cpf(), UsuarioRole.ALUNO);

		log.info("Aluno registrado com sucesso.");
		return retornoUtils.retornoSucesso("Aluno registrado com sucesso.");
	}

	public Aluno salvar(AlunoAdd alunoAdd) {

		Aluno aluno = new Aluno();

		aluno.setNome(alunoAdd.nome());
		aluno.setEmail(alunoAdd.email());
		aluno.setCelular(alunoAdd.celular());
		aluno.setCurriculoLattes(alunoAdd.curriculoLattes());

		return alunoRepository.save(aluno);
	}
}