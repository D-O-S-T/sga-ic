import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';



@Component({
  selector: 'app-aluno',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent],
  templateUrl: './aluno.component.html',
  styleUrl: './aluno.component.scss'
})
export class AlunoComponent {

  navItems: NavItem[] = [
    { label: 'Dashboard', route: '/listar-alunos' },
    { label: 'Usuários', route: '/usuarios' },
  ];
  
}
