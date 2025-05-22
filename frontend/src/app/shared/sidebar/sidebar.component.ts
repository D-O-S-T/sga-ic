import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface NavItem {
  label: string;
  icon?: string;          // classe de ícone opcional (ex.: lucide / font-awesome)
  route: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule, HttpClientModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
})
export class SidebarComponent {
  @Input({ required: true }) items: NavItem[] = [];
  @Input() width = 220;

  collapsed = false;
  toggle() {
    this.collapsed = !this.collapsed;
  }

  constructor(private http: HttpClient, private router: Router) { }

  logout() {
    this.http.post('http://localhost:8080/sga-ic/api/auth/logout', {}, { withCredentials: true })
      .subscribe({
        next: () => this.router.navigateByUrl('/home'),
        error: () => this.router.navigateByUrl('/home') // garante fallback mesmo se falhar
      });
  }
}