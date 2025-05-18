package br.edu.undf.sga_ic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Atividade;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

	List<Atividade> findByProjetoIdOrderByDataRegistroDesc(Long projetoId);
}