import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

interface Professor {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil: string | null;
}

@Component({
  selector: 'app-professores',
  templateUrl: './listar-professores.component.html',
  styleUrls: ['./listar-professores.component.scss'],
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule, MatIcon],
  providers: [HttpClientModule]
})
export class ProfessorListComponent implements OnInit {

  public professores: Professor[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}

  ngOnInit(): void {
    console.log('ngOnInit do ProfessorListComponent chamado');
    this.getProfessores();
  }

   getProfessores(): void {
    this.http.get<Professor[]>('http://localhost:8080/sga-ic/api/professor').subscribe({
      next: (data) => {
        this.professores = [...data];
        this.cdr.detectChanges();
        console.log('Professores carregados:', this.professores);
      },
      error: (error) => {
        console.error('Erro ao carregar professores:', error);
      }
    });
  }

  deletarProfessor(id: number): void {
    if (confirm('Tem certeza que deseja excluir este professor?')) {
      this.http.delete(`http://localhost:8080/sga-ic/api/professor/deletar/${id}`).subscribe({
        next: () => {
          this.professores = this.professores.filter(e => e.id !== id);
            this.getProfessores();
        },
        error: (error) => {
          console.error('Erro ao excluir professor:', error);
        }
      });
    }
  }

  editarProfessor(id: number): void {
    this.router.navigate([`/form-professor/${id}`]);
  }
}
