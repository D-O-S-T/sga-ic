package br.edu.undf.sga_ic.model;

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
@Table(name = "relatorio")
public class Relatorio {

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
	private LocalDateTime dataAbertura;

	@Column(nullable = false)
	private LocalDateTime dataEncerramento;

	@ManyToOne
	@JoinColumn(name = "coordenador_id", referencedColumnName = "id", nullable = false)
	private Coordenador coordenador;

	@ManyToOne
	@JoinColumn(name = "edital_id", referencedColumnName = "id", nullable = false)
	private Edital edital;
}