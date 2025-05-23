
import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../shared/sidebar/sidebar.component';
import { EditaisComponent, Edital } from './editais-coordenador/editais-coordenador.component';
import { EditalService } from '../../services/edital.service';
import { ProjetosListComponent } from './listar-projetos/listar-projetos.component';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-coordenador',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, EditaisComponent, ProjetosListComponent, CommonModule],
  templateUrl: './coordenador.component.html',
  styleUrl: './coordenador.component.scss'
})
export class CoordenadorComponent {

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

  constructor(private editalService: EditalService, private router: Router, private http: HttpClient) { }


  carregarProjetosPorEdital(idEdital: number) {
    this.carregandoProjetos = true;
    this.projetoErro = '';
    this.editalSelecionadoId = idEdital;
    this.editalService.obterProjetosPorEdital(idEdital).subscribe({
      next: (dados) => {
        this.projetos = dados;
        console.log('Projetos carregados:', this.projetos);
        this.modalAberta = true;
        this.carregandoProjetos = false;
      },
      error: (erro) => {
        console.error('Erro ao buscar projetos do edital', erro);
        this.projetoErro = 'Erro ao buscar projetos.';
        this.carregandoProjetos = false;
      }
    });
  }


  abrirModalComProjetos(editalId: number) {
    this.editalSelecionadoId = editalId;
    this.modalAberta = true;
  }


  abrirModal(editalId: number) {
    this.editalSelecionadoId = editalId;
    this.modalAberta = true;
    this.detalhesEdital = null;  // limpa detalhes antes de buscar
  }

 fecharModal() {
  this.modalAberta = false;
  this.detalhesEdital = null;  // reseta os detalhes do edital
}


  irParaFormProjeto() {
    this.router.navigate(['/form-projeto'], { queryParams: { editalId: this.editalSelecionadoId } });
  }


  carregarDetalhesEdital() {


   

    this.http.get<Edital>(`http://localhost:8080/sga-ic/api/edital/${this.editalSelecionadoId}`, { withCredentials: true })
      .subscribe({
        next: (detalhes) => this.detalhesEdital = detalhes,

        error: (err) => alert('Erro ao carregar detalhes do edital.')
      });
  }


}
