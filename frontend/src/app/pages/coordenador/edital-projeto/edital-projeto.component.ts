import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { Router } from '@angular/router';

interface Professor {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil?: string;
}

interface Bolsista {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil?: string;
}

interface naoBolsista {
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
  imports: [CommonModule,
    HttpClientModule,
    HeaderComponent,
    SidebarComponent],
  templateUrl: './edital-projeto.component.html',
  styleUrl: './edital-projeto.component.scss'
})
export class EditalProjetoComponent {

  professores: Professor[] = [];
  bolsistas: Bolsista[] = [];
  naoBolsistas: naoBolsista[] = [];
  carregando = true;
  erro = '';
  navItems: NavItem[] = [
    { label: 'Home', route: '/coordenador' },
    { label: 'Editais', route: '/listar-editais' },
    { label: 'Alunos', route: '/listar-alunos' },
    { label: 'Professores', route: '/listar-professores' },
    { label: 'Coordenadores', route: '/listar-coordenadores' },
  ];

  metricas: MetricasProjeto | null = null;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  ngOnInit(): void {
    const projetoId = Number(this.route.snapshot.paramMap.get('id'));
    if (!projetoId) {
      this.erro = 'Projeto inválido.';
      this.carregando = false;
      return;
    }

    this.http.get<any>(`http://localhost:8080/sga-ic/api/projeto/participantes/${projetoId}`, {
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
    this.http.get<MetricasProjeto>(`http://localhost:8080/sga-ic/api/projeto/metricas/${projetoId}`, {
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
}
