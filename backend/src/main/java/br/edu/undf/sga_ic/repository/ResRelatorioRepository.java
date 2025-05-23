package br.edu.undf.sga_ic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.undf.sga_ic.model.ResRelatorio;

import java.util.List;

@Repository
public interface ResRelatorioRepository extends JpaRepository<ResRelatorio, Long> {

    List<ResRelatorio> findByRelatorioId(Long relatorioId);

    @Query("select count(r) from ResRelatorio r where r.projeto.id = :projetoId")
    long countByProjetoId(@Param("projetoId") Long projetoId);
}