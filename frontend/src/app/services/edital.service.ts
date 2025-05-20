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
   private API_URL = 'http://localhost:8080/sga-ic/api/edital/registrar'// Altere para sua URL real

  constructor(private http: HttpClient) { }

  criarEdital(edital: Edital): Observable<any> {
    return this.http.post(this.API_URL, edital);
  }
}
