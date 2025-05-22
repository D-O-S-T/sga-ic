import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';
import { ProjetosAlunoComponent } from './projetos-aluno/projetos-aluno.component';


@Component({
  selector: 'app-aluno',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent, ProjetosAlunoComponent],
  templateUrl: './aluno.component.html',
  styleUrl: './aluno.component.scss'
})
export class AlunoComponent {

  navItems: NavItem[] = [
    { label: 'Projetos', route: '/aluno' },
    { label: 'Usuários', route: '/usuarios' },
  ];
  
}
