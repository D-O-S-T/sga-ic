import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

interface Aluno {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil: string | null;
}

@Component({
  selector: 'app-alunos',
  templateUrl: './listar-alunos.component.html',
  styleUrls: ['./listar-alunos.component.scss'],
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule, MatIcon],
  providers: [HttpClientModule]
})
export class AlunoListComponent implements OnInit {

  public alunos: Aluno[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}

  ngOnInit(): void {
    console.log('ngOnInit do AlunoListComponent chamado');
    this.getAlunos();
  }

   getAlunos(): void {
    this.http.get<Aluno[]>('http://localhost:8080/sga-ic/api/aluno').subscribe({
      next: (data) => {
        this.alunos = [...data];
        this.cdr.detectChanges();
        console.log('Editais carregados:', this.alunos);
      },
      error: (error) => {
        console.error('Erro ao carregar editais:', error);
      }
    });
  }

  deletarAluno(id: number): void {
    if (confirm('Tem certeza que deseja excluir este aluno?')) {
      this.http.delete(`http://localhost:8080/sga-ic/api/aluno/deletar/${id}`).subscribe({
        next: () => {
          this.alunos = this.alunos.filter(e => e.id !== id);
            this.getAlunos();
        },
        error: (error) => {
          console.error('Erro ao excluir aluno:', error);
        }
      });
    }
  }

  editarAluno(id: number): void {
    this.router.navigate([`/form-aluno/${id}`]);
  }


}
