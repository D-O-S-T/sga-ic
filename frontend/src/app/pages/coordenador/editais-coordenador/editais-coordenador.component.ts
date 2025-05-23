import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

export interface Edital {
  id: number;
  titulo: string;
  instituicao: string;
  dtInicio: string;
  dtFim: string;
}

@Component({
  selector: 'app-editais-coordenador',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './editais-coordenador.component.html',
  styleUrls: ['./editais-coordenador.component.scss']
})
export class EditaisCoordenadorComponent implements OnInit {
  editais: Edital[] = [];
  carregando = true;
  erro = '';

  constructor(
    private http: HttpClient,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) { }

  irParaEdital(editalId: number): void {
    this.router.navigate(['/projetos', editalId, 'edital']);
  }

  ngOnInit(): void {
    this.http
      .get<Edital[]>('http://localhost:8080/sga-ic/api/edital', { withCredentials: true })
      .subscribe({
        next: dados => {
          this.editais = dados;
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