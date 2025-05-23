import { Component } from '@angular/core';
import { HeaderComponent } from '../../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { PerfilComponent } from '../../../shared/perfil/perfil.component';

@Component({
  selector: 'app-perfil-professor',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, PerfilComponent],
  templateUrl: './perfil-professor.component.html',
  styleUrl: './perfil-professor.component.scss'
})
export class PerfilProfessorComponent {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-professor' },
    { label: 'Projetos', route: '/professor' },
  ];
}
