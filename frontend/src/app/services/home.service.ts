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
    this.http.post<any>(this.API_URL, { cpf, senha }, { withCredentials: true }).subscribe({
      next: (res) => {
        if (res?.message && res?.severityStatus) {
          const tipo = res.severityStatus.toUpperCase();
          alert(`${tipo}: ${res.message}`);
          return;
        }

        switch (res) {
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
            console.error('Perfil não reconhecido:', res);
        }
      },
      error: (err) => {
        const erroRes = err?.error;
        if (erroRes?.message && erroRes?.severityStatus) {
          const tipo = erroRes.severityStatus.toUpperCase();
          alert(`${tipo}: ${erroRes.message}`);
        } else {
          console.error('Erro ao fazer login:', err);
          alert('Erro ao fazer login. Verifique suas credenciais.');
        }
      },
    });
  }
}