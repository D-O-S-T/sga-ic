package br.edu.undf.sga_ic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByCpf(String cpf);

	List<Usuario> findByAlunoIsNotNull();

	Optional<Usuario> findByAlunoId(Long alunoId);

	Optional<Usuario> findByProfessorId(Long professorId);

	Optional<Usuario> findByCoordenadorId(Long coordenadorId);
}