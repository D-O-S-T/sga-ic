package br.edu.undf.sga_ic.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "projeto_edital")
public class ProjetoEdital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Boolean isBolsista;

	@ManyToOne
	@JoinColumn(name = "aluno_id", referencedColumnName = "id")
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "edital_id", referencedColumnName = "id", nullable = false)
	private Edital edital;

	@ManyToOne
	@JoinColumn(name = "projeto_id", referencedColumnName = "id", nullable = false)
	private Projeto projeto;

	@ManyToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id")
	private Professor professor;
}