package br.edu.undf.sga_ic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "res_relatorio")
public class ResRelatorio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
	private Projeto projeto;

	@ManyToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "relatorio_id", referencedColumnName = "id", nullable = false)
	private Relatorio relatorio;
}