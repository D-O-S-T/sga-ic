
import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem } from '../../shared/sidebar/sidebar.component';
import { ProjetosProfessorComponent } from './projetos-professor/projetos-professor.component';



@Component({
  selector: 'app-professor',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, ProjetosProfessorComponent],
  templateUrl: './professor.component.html',
  styleUrl: './professor.component.scss'
})
export class ProfessorComponent {


  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-professor' },
    { label: 'Projetos', route: '/professor' },
  ];
}
