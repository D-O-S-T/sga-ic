import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface Projeto {
  id: number;
  titulo: string;
  descricao: string;
}

@Component({
  selector: 'app-projetos-aluno',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './projetos-aluno.component.html',
  styleUrls: ['./projetos-aluno.component.scss']
})
export class ProjetosAlunoComponent implements OnInit {
  projetos: Projeto[] = [];
  carregando = true;
  erro = '';

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.http
      .get<Projeto[]>('http://localhost:8080/sga-ic/api/projeto/aluno', { withCredentials: true })
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
}