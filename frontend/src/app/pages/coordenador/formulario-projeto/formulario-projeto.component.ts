import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { ProjetoService, Projeto } from '../../../services/projeto.service';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { EditaisCoordenadorComponent, Edital } from '../editais-coordenador/editais-coordenador.component';
import { EditalService } from '../../../services/edital.service';
import { ProjetosListComponent } from '../listar-projetos/listar-projetos.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-formulario-projeto',
  standalone: true,
  imports: [FormsModule, HeaderComponent, SidebarComponent, EditaisCoordenadorComponent, ProjetosListComponent, CommonModule],
  templateUrl: './formulario-projeto.component.html',
  styleUrl: './formulario-projeto.component.scss'
})
export class FormularioProjetoComponent implements OnChanges {
  editalId!: number;
  @Output() projetoCriado = new EventEmitter<void>();
  @Output() cancelar = new EventEmitter<void>();

  novoProjeto: Projeto = {
    titulo: '',
    descricao: '',
    editalId: 0
  };

  navItems: NavItem[] = [
    { label: 'Dashboard', route: '/listar-alunos' },
    { label: 'Usuários', route: '/usuarios' },
    { label: 'Cadastro de Editais', route: '/form-edital' },
    { label: 'Listar todos Editais', route: '/listar-editais' },
  ];

  projetos: any[] = [];


  editalSelecionadoId!: number;
  projetoErro: string = '';
  carregandoProjetos = false;
  modalAberta = false;


  detalhesEdital: Edital | null = null;


  carregando = false;
  erro = '';

  constructor(private route: ActivatedRoute, private projetoService: ProjetoService, private editalService: EditalService, private router: Router, private http: HttpClient) { }


  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.editalId = +params['editalId'] || 0;
      this.novoProjeto.editalId = this.editalId;
    });
  }

  ngOnChanges() {
    if (this.editalId) {
      this.novoProjeto.editalId = this.editalId;
    }
  }

  salvarProjeto() {
    this.carregando = true;
    this.erro = '';

    this.projetoService.criarProjeto(this.novoProjeto).subscribe({
      next: (projeto: Projeto) => {
        this.carregando = false;
        this.projetoCriado.emit();
      },
      error: (err: any) => {
        this.carregando = false;
        this.erro = 'Erro ao salvar projeto. Tente novamente.';
        console.error(err);
      }
    });
  }

  cancelarForm() {
    this.cancelar.emit();
  }

  carregarDetalhesEdital() {
    if (!this.editalSelecionadoId) return;

    this.http.get<Edital>(`http://localhost:8080/sga-ic/api/edital/${this.editalSelecionadoId}`, { withCredentials: true })
      .subscribe({
        next: (detalhes) => this.detalhesEdital = detalhes,

        error: (err) => alert('Erro ao carregar detalhes do edital.')
      });
  }

}
