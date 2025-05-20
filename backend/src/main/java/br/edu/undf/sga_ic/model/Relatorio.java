package br.edu.undf.sga_ic.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDate dataAbertura;

    @Column(nullable = false)
    private LocalDate dataEncerramento;

    @ManyToOne
    @JoinColumn(name = "coordenador_id", referencedColumnName = "id", nullable = false)
    private Coordenador coordenador;

    @ManyToOne
    @JoinColumn(name = "edital_id", referencedColumnName = "id", nullable = false)
    private Edital edital;
}