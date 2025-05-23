import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { ProjetosAlunoComponent } from '../projetos-aluno/projetos-aluno.component';
import { PerfilComponent } from '../../../shared/perfil/perfil.component';

@Component({
  selector: 'app-perfil-aluno',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, PerfilComponent, ProjetosAlunoComponent],
  templateUrl: './perfil-aluno.component.html',
  styleUrl: './perfil-aluno.component.scss'
})
export class PerfilAlunoComponent {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-aluno' },
    { label: 'Projetos', route: '/aluno' },
  ];
}
