import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface Usuario {
  id: number;
  nome: string;
  fotoPerfil: string;
}

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  usuario: Usuario | null = null; // <- IMPORTANTE

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef, private router: Router) { }

  ngOnInit() {
    console.log('Header inicializado');
    this.http
      .get<Usuario>('http://localhost:8080/sga-ic/api/usuario/perfil', { withCredentials: true })
      .subscribe({
        next: (u) => {
          this.usuario = u;
          this.cdr.detectChanges();
          console.log('Usuário carregado:', u); // DEBUG
        },
        error: (e) => console.error('Falhou', e),
      });
  }
}