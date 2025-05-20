package br.edu.undf.sga_ic.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
@Table(name = "atividade")
public class Atividade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String titulo;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String descricao;

	@Column(nullable = false)
	private LocalDateTime dataRegistro;

	@Column(nullable = false)
	private LocalDate dataAbertura;

	@Column(nullable = false)
	private LocalDate dataEncerramento;

	@ManyToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
	private Professor professor;

	@ManyToOne
	@JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
	private Projeto projeto;
}