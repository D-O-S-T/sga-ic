import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

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
  imports: [CommonModule, HttpClientModule],
})
export class AlunoListComponent implements OnInit {

  public alunos: Aluno[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    console.log('ngOnInit do AlunoListComponent chamado');
    this.getAlunos();
  }

  getAlunos(): void {
    this.http.get<Aluno[]>('http://localhost:8080/sga-ic/api/aluno').subscribe({
      next: (data) => {
        this.alunos = [...data];  // cria nova referência para garantir atualização da view
        this.cdr.detectChanges(); // força a atualização da view
        console.log('Alunos carregados:', this.alunos);
      },
      error: (error) => {
        console.error('Erro ao carregar alunos:', error);
      }
    });
  }

}
