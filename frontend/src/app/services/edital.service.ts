import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Edital {
  titulo: string;
  descricao: string;
  dtInicio: string;
  dtFim: string;
  instituicao: string;
  qtdAlunos: number;
  qtdProjetos: number;
  qtdBolsistas: number;
  qtdProfessores: number;
}

@Injectable({
  providedIn: 'root'
})
export class EditalService {
  private API_URL = 'http://localhost:8080/sga-ic/api/edital/registrar'; // URL para criar edital
  private baseUrl = 'http://localhost:8080/sga-ic/api'; // Base para outros endpoints

  constructor(private http: HttpClient) { }

  criarEdital(edital: Edital): Observable<any> {
    return this.http.post(this.API_URL, edital);
  }

  buscarEditalPorId(id: string): Observable<Edital> {
    return this.http.get<Edital>(`${this.baseUrl}/edital/${id}`);
  }

  atualizarEdital(id: string, edital: Edital): Observable<Edital> {
    return this.http.put<Edital>(`${this.baseUrl}/edital/${id}`, edital);
  }

  obterProjetosPorEdital(idEdital: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/projeto/edital/${idEdital}`);
  }
}
