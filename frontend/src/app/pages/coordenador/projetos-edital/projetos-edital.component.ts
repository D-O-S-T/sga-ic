import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { FormularioRelatorioComponent } from '../formulario-relatorio/formulario-relatorio.component'; 


export interface Edital {
  id: number;
  titulo: string;
  instituicao: string;
  descricao: string;
  qtdBolsistas: number;
  qtdAlunos: number;
  qtdProfessores: number;
  qtdProjetos: number;
  dtInicio: string;
  dtFim: string;
}

interface Projeto {
  id: number;
  titulo: string;
  descricao: string;
}

@Component({
  selector: 'app-projetos-edital',
  standalone: true,
  imports: [CommonModule, HttpClientModule, HeaderComponent, SidebarComponent],
  templateUrl: './projetos-edital.component.html',
  styleUrl: './projetos-edital.component.scss'
})
export class ProjetosEditalComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Home', route: '/coordenador' },
    { label: 'Editais', route: '/listar-editais' },
    { label: 'Alunos', route: '/listar-alunos' },
    { label: 'Professores', route: '/listar-professores' },
    { label: 'Coordenadores', route: '/listar-coordenadores' },
  ];

  edital!: Edital;
  projetos: Projeto[] = [];
  carregando = true;
  erro = '';

  constructor(private route: ActivatedRoute, private http: HttpClient, private cdr: ChangeDetectorRef, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    const editalId = Number(this.route.snapshot.paramMap.get('id'));
    if (!editalId) {
      this.erro = 'Edital inválido.';
      this.carregando = false;
      return;
    }

    this.carregarEdital(editalId);
    this.carregarProjetos(editalId);
  }

  irParaProjeto(projetoId: number): void {
    this.router.navigate(['/edital', projetoId, 'projeto']);
  }

  
  abrirModalRelatorio(): void {
    const dialogRef = this.dialog.open(FormularioRelatorioComponent, {
      width: '600px',
    });

    // Atribuir o valor do @Input após abrir o modal
    dialogRef.componentInstance.editalId = this.edital.id;
  }

  private carregarEdital(id: number): void {
    this.http.get<Edital>(`http://localhost:8080/sga-ic/api/edital/${id}`, { withCredentials: true })
      .subscribe({
        next: (data) => this.edital = data,
        error: () => this.erro = 'Erro ao carregar o projeto.'
      });
  }

  private carregarProjetos(id: number): void {
    this.http.get<Projeto[]>(`http://localhost:8080/sga-ic/api/projeto/edital/${id}`, { withCredentials: true })
      .subscribe({
        next: (dados) => {
          this.projetos = dados;
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