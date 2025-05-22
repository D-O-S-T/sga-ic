
import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';



@Component({
  selector: 'app-professor',
  standalone: true,
  imports: [HeaderComponent, SidebarComponent],
  templateUrl: './professor.component.html',
  styleUrl: './professor.component.scss'
})
export class ProfessorComponent {

  
   navItems: NavItem[] = [
      { label: 'Dashboard', route: '/listar-alunos' },
      { label: 'Usuários', route: '/usuarios' },
    ];

}
