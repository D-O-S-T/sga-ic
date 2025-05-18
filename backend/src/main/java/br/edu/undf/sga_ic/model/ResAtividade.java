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
@Table(name = "res_atividade")
public class ResAtividade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = true)
	private String descricao;

	@ManyToOne
	@JoinColumn(name = "aluno_id", referencedColumnName = "id", nullable = false)
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "atividade_id", referencedColumnName = "id", nullable = false)
	private Atividade atividade;
}