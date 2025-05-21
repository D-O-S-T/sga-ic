import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Edital {
  id: number;
  titulo: string;
  instituicao: string;
  dtInicio: string;
  dtFim: string;
}

@Component({
  selector: 'app-edital-list',
  templateUrl: './listar-editais.component.html',
  styleUrls: ['./listar-editais.component.scss'],
  standalone: true,
  imports: [CommonModule, HttpClientModule],
})
export class EditalListComponent implements OnInit {

  public editais: Edital[] = [];

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

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
}
