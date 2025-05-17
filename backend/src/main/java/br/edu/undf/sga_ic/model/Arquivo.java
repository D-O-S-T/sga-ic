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
@Table(name = "arquivo")
public class Arquivo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nomeArquivo;

	@Column(nullable = false)
	private String tipoArquivo;

	@Column(columnDefinition = "BYTEA", nullable = false)
	private byte[] bytesArquivo;

	@ManyToOne
	@JoinColumn(name = "resposta_id", referencedColumnName = "id", nullable = true)
	private Resposta resposta;

	@ManyToOne
	@JoinColumn(name = "atividade_id", referencedColumnName = "id", nullable = true)
	private Atividade atividade;
}