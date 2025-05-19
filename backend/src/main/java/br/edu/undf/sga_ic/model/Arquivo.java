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
	@JoinColumn(name = "atividade_id", referencedColumnName = "id", nullable = true)
	private Atividade atividade;

	@ManyToOne
	@JoinColumn(name = "res_atividade_id", referencedColumnName = "id", nullable = true)
	private ResAtividade resAtividade;

	@ManyToOne
	@JoinColumn(name = "relatorio_id", referencedColumnName = "id", nullable = true)
	private Relatorio relatorio;

	@ManyToOne
	@JoinColumn(name = "res_relatorio_id", referencedColumnName = "id", nullable = true)
	private ResRelatorio resRelatorio;
}