package br.edu.undf.sga_ic.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "edital")
public class Edital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String titulo;

	@Column(nullable = false)
	private String instituicao;

	@Column(nullable = false)
	private String descricao;

	@Column(nullable = false)
	private BigDecimal qtdBolsistas;

	@Column(nullable = false)
	private BigDecimal qtdAlunos;

	@Column(nullable = false)
	private BigDecimal qtdProfessores;

	@Column(nullable = false)
	private BigDecimal qtdProjetos;

	@Column(nullable = false)
	private LocalDate dtInicio;

	@Column(nullable = false)
	private LocalDate dtFim;
}