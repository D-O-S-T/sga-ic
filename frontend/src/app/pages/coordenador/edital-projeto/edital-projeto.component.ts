import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { FormsModule } from '@angular/forms';
import { forkJoin } from 'rxjs';
import { MatIcon } from '@angular/material/icon';

interface Pessoa {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil?: string;
}

interface MetricasProjeto {
  qtdProfessores: number;
  qtdBolsistas: number;
  qtdNaoBolsistas: number;
  qtdAtividades: number;
  qtdResAtividades: number;
  qtdRelatorios: number;
  qtdResRelatorios: number;
}

@Component({
  selector: 'app-edital-projeto',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    HeaderComponent,
    SidebarComponent,
    MatSelectModule,
    MatButtonModule,
    MatCheckboxModule,
    FormsModule,
    MatIcon
  ],
  templateUrl: './edital-projeto.component.html',
  styleUrl: './edital-projeto.component.scss'
})
export class EditalProjetoComponent implements OnInit {
  projetoId!: number;

  professores: Pessoa[] = [];
  bolsistas: Pessoa[] = [];
  naoBolsistas: Pessoa[] = [];

  professoresDisponiveis: Pessoa[] = [];
  alunosDisponiveis: Pessoa[] = [];

  professorSelecionado?: Pessoa;
  alunoSelecionado?: Pessoa;
  isBolsista = false;

  metricas: MetricasProjeto | null = null;

  carregando = true;
  erro = '';

  navItems: NavItem[] = [
    { label: 'Home', route: '/coordenador' },
    { label: 'Editais', route: '/listar-editais' },
    { label: 'Alunos', route: '/listar-alunos' },
    { label: 'Professores', route: '/listar-professores' },
    { label: 'Coordenadores', route: '/listar-coordenadores' },
  ];

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.projetoId = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.projetoId) {
      this.erro = 'Projeto inválido.';
      this.carregando = false;
      return;
    }

    this.carregarParticipantes();
    this.carregarMetricas();
    this.carregarCombos();
  }

  carregarParticipantes() {
    this.http.get<any>(`http://localhost:8080/sga-ic/api/projeto/participantes/${this.projetoId}`, {
      withCredentials: true
    }).subscribe({
      next: (res) => {
        this.professores = res.professores || [];
        this.bolsistas = res.bolsistas || [];
        this.naoBolsistas = res.naoBolsistas || [];
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.erro = 'Erro ao carregar participantes.';
        console.error(err);
        this.carregando = false;
        this.cdr.detectChanges();
      }
    });
  }

  carregarMetricas() {
    this.http.get<MetricasProjeto>(`http://localhost:8080/sga-ic/api/projeto/metricas/${this.projetoId}`, {
      withCredentials: true
    }).subscribe({
      next: (dados) => {
        this.metricas = dados;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Erro ao buscar métricas:', err);
      }
    });
  }

  carregarCombos(): void {
    forkJoin({
      alunos: this.http.get<Pessoa[]>('http://localhost:8080/sga-ic/api/aluno', { withCredentials: true }),
      professores: this.http.get<Pessoa[]>('http://localhost:8080/sga-ic/api/professor', { withCredentials: true })
    }).subscribe(({ alunos, professores }) => {
      const idsAlunosProjeto = [...this.bolsistas, ...this.naoBolsistas].map(a => a.id);
      const idsProfProjeto = this.professores.map(p => p.id);

      this.alunosDisponiveis = alunos.filter(a => !idsAlunosProjeto.includes(a.id));
      this.professoresDisponiveis = professores.filter(p => !idsProfProjeto.includes(p.id));
      this.cdr.detectChanges();
    });
  }

  atribuirProfessor() {
    if (!this.professorSelecionado) return;
    const body = {
      projetoId: this.projetoId,
      professorId: this.professorSelecionado.id
    };
    this.http.post<any>('http://localhost:8080/sga-ic/api/projeto-edital/registrar/professor', body, {
      withCredentials: true
    }).subscribe(result => {
      this.professores.push({ ...this.professorSelecionado!, id: result.id });
      this.professoresDisponiveis = this.professoresDisponiveis.filter(p => p.id !== this.professorSelecionado!.id);
      this.professorSelecionado = undefined;
      this.metricas!.qtdProfessores++;
    });
  }

  atribuirAluno() {
    if (!this.alunoSelecionado) return;
    const body = {
      alunoId: this.alunoSelecionado.id,
      projetoId: this.projetoId,
      isBolsista: this.isBolsista
    };
    this.http.post<any>('http://localhost:8080/sga-ic/api/projeto-edital/registrar/aluno', body, {
      withCredentials: true
    }).subscribe(result => {
      const novo = { ...this.alunoSelecionado!, id: result.id };
      if (this.isBolsista) {
        this.bolsistas.push(novo);
        this.metricas!.qtdBolsistas++;
      } else {
        this.naoBolsistas.push(novo);
        this.metricas!.qtdNaoBolsistas++;
      }
      this.alunosDisponiveis = this.alunosDisponiveis.filter(a => a.id !== this.alunoSelecionado!.id);
      this.alunoSelecionado = undefined;
      this.isBolsista = false;
    });
  }

  desatribuir(relacaoId: number) {
    this.http.delete(`http://localhost:8080/sga-ic/api/projeto-edital/retirar/${relacaoId}`, {
      withCredentials: true
    }).subscribe(() => {
      this.professores = this.professores.filter(p => p.id !== relacaoId);
      this.bolsistas = this.bolsistas.filter(b => b.id !== relacaoId);
      this.naoBolsistas = this.naoBolsistas.filter(n => n.id !== relacaoId);
      this.recalcularMetricas();
      this.carregarCombos();
    });
  }

  recalcularMetricas() {
    if (!this.metricas) return;
    this.metricas.qtdProfessores = this.professores.length;
    this.metricas.qtdBolsistas = this.bolsistas.length;
    this.metricas.qtdNaoBolsistas = this.naoBolsistas.length;
  }
}
