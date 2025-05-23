import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { Router } from '@angular/router';

interface Projeto {
  id: number;
  titulo: string;
  descricao: string;
}

interface Relatorio {
  id: number;
  titulo: string;
  descricao: string;
  dataRegistro: string;
  dataAbertura: string;
  dataEncerramento: string;
}

@Component({
  selector: 'app-projeto-relatorios',
  standalone: true,
  imports: [CommonModule, HttpClientModule, HeaderComponent, SidebarComponent],
  templateUrl: './projeto-relatorios.component.html',
  styleUrl: './projeto-relatorios.component.scss'
})
export class ProjetoRelatoriosComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-professor' },
    { label: 'Projetos', route: '/professor' },
  ];

  projeto!: Projeto;
  relatorios: Relatorio[] = [];
  carregando = true;
  erro = '';

  constructor(private route: ActivatedRoute, private http: HttpClient, private cdr: ChangeDetectorRef, private router: Router) { }

  ngOnInit(): void {
    const projetoId = Number(this.route.snapshot.paramMap.get('id'));
    if (!projetoId) {
      this.erro = 'Projeto inválido.';
      this.carregando = false;
      return;
    }

    this.carregarProjeto(projetoId);
    this.carregarRelatorios(projetoId);
  }

  irParaRespostas(relatorioId: number): void {
    this.router.navigate(['/relatorio', relatorioId, 'respostas']);
  }

  private carregarProjeto(id: number): void {
    this.http.get<Projeto>(`http://localhost:8080/sga-ic/api/projeto/${id}`, { withCredentials: true })
      .subscribe({
        next: (data) => this.projeto = data,
        error: () => this.erro = 'Erro ao carregar o projeto.'
      });
  }

  private carregarRelatorios(id: number): void {
    this.http.get<Relatorio[]>(`http://localhost:8080/sga-ic/api/relatorio/projeto/${id}`, { withCredentials: true })
      .subscribe({
        next: (dados) => {
          this.relatorios = dados;
          this.carregando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.erro = 'Erro ao carregar as relatorios.';
          this.carregando = false;
        }
      });
  }
}