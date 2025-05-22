import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

export interface Projeto {
  id: number;
  titulo: string;
  descricao: string;
}

@Component({
  selector: 'app-projetos-professor',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './projetos-professor.component.html',
  styleUrl: './projetos-professor.component.scss'
})
export class ProjetosProfessorComponent  implements OnInit {
  projetos: Projeto[] = [];
  carregando = true;
  erro = '';

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef, private router: Router) { }

  ngOnInit(): void {
    this.http
      .get<Projeto[]>('http://localhost:8080/sga-ic/api/projeto/professor', { withCredentials: true })
      .subscribe({
        next: dados => {
          this.projetos = dados;
          this.carregando = false;
          this.cdr.detectChanges();
        },
        error: err => {
          console.error(err);
          this.erro = 'Erro ao carregar os projetos';
          this.carregando = false;
        }
      });
  }

  abrirFormularioAtividade(projetoId: number) {
  this.router.navigate(['/formulario-atividade', projetoId]);
}
}
