import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';
import { EditaisCoordenadorComponent } from './editais-coordenador/editais-coordenador.component';


@Component({
  selector: 'app-coordenador',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, EditaisCoordenadorComponent],
  templateUrl: './coordenador.component.html',
  styleUrl: './coordenador.component.scss'
})
export class CoordenadorComponent {

  navItems: NavItem[] = [
    { label: 'Home', route: '/coordenador' },
    { label: 'Perfil', route: '/perfil-coordenador' },
    { label: 'Editais', route: '/listar-editais' },
    { label: 'Alunos', route: '/listar-alunos' },
    { label: 'Professores', route: '/listar-professores' },
    { label: 'Coordenadores', route: '/listar-coordenadores' },
  ];
}