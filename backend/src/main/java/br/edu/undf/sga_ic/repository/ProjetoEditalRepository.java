package br.edu.undf.sga_ic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.ProjetoEdital;

@Repository
public interface ProjetoEditalRepository extends JpaRepository<ProjetoEdital, Long> {

}