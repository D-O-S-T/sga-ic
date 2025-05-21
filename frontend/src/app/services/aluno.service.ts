import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Aluno {
  nome: string;
  email: string;
  matricula: string;
  curso: string;
  instituicao: string;
}

@Injectable({
  providedIn: 'root'
})
export class AlunoService {
  private API_URL = 'http://localhost:8080/sga-ic/api/aluno';

  constructor(private http: HttpClient) { }

  criarAluno(aluno: Aluno): Observable<any> {
    return this.http.post(`${this.API_URL}/registrar`, aluno);
  }

  buscarAlunoPorId(id: string): Observable<Aluno> {
    return this.http.get<Aluno>(`${this.API_URL}/${id}`);
  }

  atualizarAluno(id: string, aluno: Aluno): Observable<Aluno> {
    return this.http.put<Aluno>(`${this.API_URL}/${id}`, aluno);
  }
}
