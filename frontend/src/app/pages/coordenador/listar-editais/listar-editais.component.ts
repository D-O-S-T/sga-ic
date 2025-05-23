import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

interface Edital {
    id: number;
  titulo: string;
  instituicao: string;
  descricao: string;
  qtdBolsistas: number;
  qtdAlunos: number;
  qtdProfessores: number;
  qtdProjetos: number;
  dtInicio: string;
  dtFim: string;
}


@Component({
  selector: 'app-edital-list',
  templateUrl: './listar-editais.component.html',
  styleUrls: ['./listar-editais.component.scss'],
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule, MatIcon],
})
export class EditalListComponent implements OnInit {

  public editais: Edital[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef, private router: Router) { }

  ngOnInit(): void {
    this.getEditais();
  }

  getEditais(): void {
    this.http.get<Edital[]>('http://localhost:8080/sga-ic/api/edital').subscribe({
      next: (data) => {
        this.editais = [...data];
        this.cdr.detectChanges();
        console.log('Editais carregados:', this.editais);
      },
      error: (error) => {
        console.error('Erro ao carregar editais:', error);
      }
    });
  }

  deletarEdital(id: number): void {
    if (confirm('Tem certeza que deseja excluir este edital?')) {
      this.http.delete(`http://localhost:8080/sga-ic/api/edital/deletar/${id}`).subscribe({
        next: () => {
          this.editais = this.editais.filter(e => e.id !== id);
        },
        error: (error) => {
          console.error('Erro ao excluir edital:', error);
        }
      });
    }
  }

  editarEdital(id: number): void {
    this.router.navigate([`/form-edital/${id}`]);
  }
}