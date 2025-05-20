import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-formulario-aluno',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatButtonModule, MatCardModule],
  templateUrl: './formulario-aluno.component.html',
  styleUrl: './formulario-aluno.component.scss',
})
export class FormularioAlunoComponent {
  cpf = '';
  nome = '';
  email = '';
  descricao = '';
  curriculoLattes = '';
  celular = '';

  constructor(private http: HttpClient) {}

  cadastrarAluno() {
    const alunoData = {
      cpf: this.cpf,
      nome: this.nome,
      email: this.email,
      descricao: this.descricao,
      curriculoLattes: this.curriculoLattes,
      celular: this.celular,
    };

    this.http
      .post('http://localhost:8080/sga-ic/api/aluno/registrar', alunoData, { withCredentials: true })
      .subscribe({
        next: (res) => {
          console.log('Aluno cadastrado com sucesso!', res);
          alert('Aluno cadastrado!');
          this.resetForm();
        },
        error: (err) => {
          console.error('Erro ao cadastrar aluno', err);
          alert('Erro ao cadastrar aluno. Verifique os dados.');
        },
      });
  }

  resetForm() {
    this.cpf = '';
    this.nome = '';
    this.email = '';
    this.descricao = '';
    this.curriculoLattes = '';
    this.celular = '';
  }
}
