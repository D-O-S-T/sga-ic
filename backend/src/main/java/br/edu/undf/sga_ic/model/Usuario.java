package br.edu.undf.sga_ic.model;

import java.time.ZonedDateTime;

import br.edu.undf.sga_ic.enums.UsuarioRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Entity
@Table(name = "usuario")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String cpf;

	@Column(nullable = false)
	private String senhaHash;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UsuarioRole usuarioRole;

	@Column(nullable = false)
	private ZonedDateTime cadastradoEm;

	@Column(nullable = true, columnDefinition = "BYTEA")
	private byte[] fotoPerfil;

	@OneToOne
	@JoinColumn(name = "aluno_id", referencedColumnName = "id", nullable = true)
	private Aluno aluno;

	@OneToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = true)
	private Professor professor;

	@OneToOne
	@JoinColumn(name = "coordenador_id", referencedColumnName = "id", nullable = true)
	private Coordenador coordenador;

	@ManyToOne
	@JoinColumn(name = "cadastrado_por_id", referencedColumnName = "id", nullable = true)
	private Usuario cadastradoPor;
}
