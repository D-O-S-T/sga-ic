package br.edu.undf.sga_ic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.ResAtividade;

@Repository
public interface ResAtividadeRepository extends JpaRepository<ResAtividade, Long> {

	List<ResAtividade> findByAtividadeId(Long atividadeId);
}