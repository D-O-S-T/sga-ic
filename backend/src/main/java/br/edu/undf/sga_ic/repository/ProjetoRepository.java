package br.edu.undf.sga_ic.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Projeto;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

	BigDecimal countByEditalId(Long editalId);

	List<Projeto> findProjetoByEditalId(Long editalId);
}