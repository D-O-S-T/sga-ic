
import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';
import { EditaisComponent } from './editais-coordenador/editais-coordenador.component';
import { EditalService } from '../../services/edital.service';


@Component({
  selector: 'app-coordenador',
  standalone: true,
   imports: [HeaderComponent, SidebarComponent,EditaisComponent],
  templateUrl: './coordenador.component.html',
  styleUrl: './coordenador.component.scss'
})
export class CoordenadorComponent {

   navItems: NavItem[] = [
      { label: 'Dashboard', route: '/listar-alunos' },
      { label: 'Usuários', route: '/usuarios' },
      {label: 'Cadastro de Editais', route: '/form-edital'},
      { label: 'Listar todos Editais', route: '/listar-editais' },
    ];

     projetos: any[] = [];

  constructor(private editalService: EditalService) {}

    
  carregarProjetosPorEdital(idEdital: number) {
    this.editalService.obterProjetosPorEdital(idEdital).subscribe({
      next: (dados) => {
        this.projetos = dados;
        console.log('Projetos carregados:', this.projetos);
      },
      error: (erro) => {
        console.error('Erro ao buscar projetos do edital', erro);
      }
    });
  }

}
