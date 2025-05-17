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
@Table(name = "projeto_edital")
public class ProjetoEdital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = true)
	private Boolean isBolsista;

	@ManyToOne
	@JoinColumn(name = "aluno_id", referencedColumnName = "id", nullable = true)
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = true)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "edital_id", referencedColumnName = "id", nullable = false)
	private Edital edital;

	@ManyToOne
	@JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
	private Projeto projeto;

	@ManyToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = true)
	private Professor professor;
}