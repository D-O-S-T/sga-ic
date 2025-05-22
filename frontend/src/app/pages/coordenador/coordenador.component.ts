
import { Component } from '@angular/core';
import { HeaderComponent } from '../../shared/header/header.component';
import { SidebarComponent, NavItem  } from '../../shared/sidebar/sidebar.component';


@Component({
  selector: 'app-coordenador',
  standalone: true,
   imports: [HeaderComponent, SidebarComponent],
  templateUrl: './coordenador.component.html',
  styleUrl: './coordenador.component.scss'
})
export class CoordenadorComponent {

   navItems: NavItem[] = [
      { label: 'Dashboard', route: '/listar-alunos' },
      { label: 'Usuários', route: '/usuarios' },
    ];

}
