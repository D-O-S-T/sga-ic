package br.edu.undf.sga_ic.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.ProjetoEdital;

@Repository
public interface ProjetoEditalRepository extends JpaRepository<ProjetoEdital, Long> {

	boolean existsByAlunoIdAndProjetoId(Long alunoId, Long projetoId);

	boolean existsByProfessorIdAndProjetoId(Long professorId, Long projetoId);

	BigDecimal countByProjetoIdAndProfessorIsNull(Long projetoId);

	BigDecimal countByProjetoIdAndAlunoIsNull(Long projetoId);

	BigDecimal countByProjetoIdAndProfessorIsNullAndIsBolsistaTrue(Long projetoId);
}