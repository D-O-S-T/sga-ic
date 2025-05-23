import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { PerfilComponent } from '../../../shared/perfil/perfil.component';

@Component({
  selector: 'app-perfil-aluno',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, PerfilComponent],
  templateUrl: './perfil-coordenador.component.html',
  styleUrl: './perfil-coordenador.component.scss'
})
export class PerfilCoordenadorComponent {

  navItems: NavItem[] = [
    { label: 'Home', route: '/coordenador' },
    { label: 'Perfil', route: '/perfil-coordenador' },
    { label: 'Editais', route: '/listar-editais' },
    { label: 'Alunos', route: '/listar-alunos' },
    { label: 'Professores', route: '/listar-professores' },
    { label: 'Coordenadores', route: '/listar-coordenadores' },
  ];
}