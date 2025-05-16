package br.edu.undf.sga_ic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}