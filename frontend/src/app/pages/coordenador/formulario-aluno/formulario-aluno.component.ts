import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-formulario-aluno',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatButtonModule, MatCardModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './formulario-aluno.component.html',
  styleUrl: './formulario-aluno.component.scss',
})
export class FormularioAlunoComponent {
 cpf: string = '';

onCpfChange(value: string) {
  this.cpf = this.formatarCPF(value);
}

formatarCPF(cpf: string): string {
  cpf = cpf.replace(/\D/g, ''); // Remove tudo que não é dígito
  if (cpf.length > 11) cpf = cpf.slice(0, 11); // Limita a 11 dígitos

  // Aplica a máscara: 000.000.000-00
  if (cpf.length > 9) {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  } else if (cpf.length > 6) {
    return cpf.replace(/(\d{3})(\d{3})(\d{1,3})/, '$1.$2.$3');
  } else if (cpf.length > 3) {
    return cpf.replace(/(\d{3})(\d{1,3})/, '$1.$2');
  } else {
    return cpf;
  }
}

  nome = '';
  email = '';
  descricao = '';
  curriculoLattes = '';
  
  celular: string = '';




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
          console.log('Aluno salvo com sucesso!', res);
          alert('Aluno salvo!');
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
