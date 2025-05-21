import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

interface Coordenador {
  id: number;
  nome: string;
  cpf: string;
  fotoPerfil: string | null;
}

@Component({
  selector: 'app-coordenadores',
  templateUrl: './listar-coordenadores.component.html',
  styleUrls: ['./listar-coordenadores.component.scss'],
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterModule, MatIcon],
  providers: [HttpClientModule]
})
export class CoordenadorListComponent implements OnInit {

  public coordenadores: Coordenador[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}

  ngOnInit(): void {
    console.log('ngOnInit do CoordenadorListComponent chamado');
    this.getCoordenadores();
  }

   getCoordenadores(): void {
    this.http.get<Coordenador[]>('http://localhost:8080/sga-ic/api/coordenador').subscribe({
      next: (data) => {
        this.coordenadores = [...data];
        this.cdr.detectChanges();
        console.log('Coordenadores carregados:', this.coordenadores);
      },
      error: (error) => {
        console.error('Erro ao carregar coordenadores:', error);
      }
    });
  }

  deletarCoordenador(id: number): void {
    if (confirm('Tem certeza que deseja excluir este coordenador?')) {
      this.http.delete(`http://localhost:8080/sga-ic/api/coordenador/deletar/${id}`).subscribe({
        next: () => {
          this.coordenadores = this.coordenadores.filter(e => e.id !== id);
            this.getCoordenadores();
        },
        error: (error) => {
          console.error('Erro ao excluir Coordenador:', error);
        }
      });
    }
  }

  editarCoordenador(id: number): void {
    this.router.navigate([`/form-coordenador/${id}`]);
  }
}
