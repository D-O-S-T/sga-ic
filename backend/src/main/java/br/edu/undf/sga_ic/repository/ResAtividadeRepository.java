package br.edu.undf.sga_ic.repository;

import br.edu.undf.sga_ic.model.ResAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResAtividadeRepository extends JpaRepository<ResAtividade, Long> {

	List<ResAtividade> findByAtividadeId(Long atividadeId);

	@Query("select count(r) from ResAtividade r where r.atividade.projeto.id = :projetoId")
	long countByProjetoId(@Param("projetoId") Long projetoId);
}