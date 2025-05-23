package br.edu.undf.sga_ic.repository;

import br.edu.undf.sga_ic.model.Relatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelatorioRepository extends JpaRepository<Relatorio, Long> {

    long countByEditalId(Long editalId);

    List<Relatorio> findByEditalIdOrderByDataRegistroDesc(Long editalId);
}