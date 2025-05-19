package br.edu.undf.sga_ic.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Projeto;
import br.edu.undf.sga_ic.model.ProjetoEdital;

@Repository
public interface ProjetoEditalRepository extends JpaRepository<ProjetoEdital, Long> {

	ProjetoEdital findByProfessorIdAndEditalId(Long professorId, Long editalId);

	boolean existsByAlunoIdAndProjetoId(Long alunoId, Long projetoId);

	boolean existsByAlunoIdAndEditalId(Long alunoId, Long editalId);

	boolean existsByProfessorIdAndProjetoId(Long professorId, Long projetoId);

	boolean existsByProfessorIdAndEditalId(Long professorId, Long editalId);

	BigDecimal countByProjetoIdAndProfessorIsNull(Long projetoId);

	BigDecimal countByProjetoIdAndAlunoIsNull(Long projetoId);

	BigDecimal countByProjetoIdAndProfessorIsNullAndIsBolsistaTrue(Long projetoId);

	@Query("SELECT pe.projeto FROM ProjetoEdital pe WHERE pe.aluno.id = :alunoId")
	List<Projeto> findProjetosByAlunoId(@Param("alunoId") Long alunoId);

	@Query("SELECT pe.projeto FROM ProjetoEdital pe WHERE pe.professor.id = :professorId")
	List<Projeto> findProjetosByProfessorId(@Param("professorId") Long professorId);
}