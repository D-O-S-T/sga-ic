package br.edu.undf.sga_ic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Arquivo;

@Repository
public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

	List<Arquivo> findByAtividadeId(Long atividadeId);

	List<Arquivo> findByResAtividadeId(Long resAtividadeId);
}