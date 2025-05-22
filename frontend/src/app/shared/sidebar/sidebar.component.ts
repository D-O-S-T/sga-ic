import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

export interface NavItem {
  label: string;
  icon?: string;          // classe de ícone opcional (ex.: lucide / font-awesome)
  route: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  /** Itens exibidos no menu. */
  @Input({ required: true }) items: NavItem[] = [];

  /** Largura (px) quando expandido -- permite ajuste via atributo `[width]="220"` */
  @Input() width = 220;

  collapsed = false;
  toggle() {
    this.collapsed = !this.collapsed;
  }
}