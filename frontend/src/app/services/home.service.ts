import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root',
})
export class HomeService {
  private readonly API_URL = 'http://localhost:8080/sga-ic/api/auth/login';

  constructor(private http: HttpClient, private router: Router) {}

  login(cpf: string, senha: string): void {
    this.http.post<string>(this.API_URL, { cpf, senha }).subscribe({
      next: (perfil) => {
        switch (perfil) {
          case 'ALUNO':
            this.router.navigate(['/aluno']);
            break;
          case 'PROFESSOR':
            this.router.navigate(['/professor']);
            break;
          case 'COORDENADOR':
            this.router.navigate(['/coordenador']);
            break;
          default:
            console.error('Perfil não reconhecido:', perfil);
        }
      },
      error: (err) => {
        console.error('Erro ao fazer login:', err);
      },
    });
  }
}
