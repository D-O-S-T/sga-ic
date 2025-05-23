import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Interface do projeto (exemplo simples)
export interface Projeto {
  id?: number;
  titulo: string;
  descricao: string;
  editalId: number;
}

@Injectable({
  providedIn: 'root',
})
export class ProjetoService {
  private baseUrl = 'http://localhost:8080/sga-ic/api/projeto';

  constructor(private http: HttpClient) {}

  getProjetosPorEdital(editalId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/edital/${editalId}`);
  }

   criarProjeto(projeto: Projeto): Observable<Projeto> {
    console.log('Dados enviados para o backend (criarProjeto):', projeto);
    return this.http.post<Projeto>(`${this.baseUrl}/registrar`, projeto);
    
  }
}
