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

interface Atividade {
  id: number;
  titulo: string;
  descricao: string;
  dataRegistro: string;
  dataAbertura: string;
  dataEncerramento: string;
}

@Component({
  selector: 'app-projeto-atividades',
  standalone: true,
  imports: [CommonModule, HttpClientModule, HeaderComponent, SidebarComponent],
  templateUrl: './projeto-atividades.component.html',
  styleUrl: './projeto-atividades.component.scss'
})
export class ProjetoAtividadesComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil' },
    { label: 'Projetos', route: '/aluno' },
  ];

  projeto!: Projeto;
  atividades: Atividade[] = [];
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
    this.carregarAtividades(projetoId);
  }

  irParaRespostas(atividadeId: number): void {
    this.router.navigate(['/atividade', atividadeId, 'respostas']);
  }

  private carregarProjeto(id: number): void {
    this.http.get<Projeto>(`http://localhost:8080/sga-ic/api/projeto/${id}`, { withCredentials: true })
      .subscribe({
        next: (data) => this.projeto = data,
        error: () => this.erro = 'Erro ao carregar o projeto.'
      });
  }

  private carregarAtividades(id: number): void {
    this.http.get<Atividade[]>(`http://localhost:8080/sga-ic/api/atividade/projeto/${id}`, { withCredentials: true })
      .subscribe({
        next: (dados) => {
          this.atividades = dados;
          this.carregando = false;
          this.cdr.detectChanges();
        },
        error: () => {
          this.erro = 'Erro ao carregar as atividades.';
          this.carregando = false;
        }
      });
  }
}
