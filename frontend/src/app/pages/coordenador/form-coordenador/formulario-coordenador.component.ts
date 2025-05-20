import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-formulario-coordenador',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatButtonModule, MatCardModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './formulario-coordenador.component.html',
  styleUrl: './formulario-coordenador.component.scss',
})
export class FormularioCoordenadorComponent {
  cpf: string = '';
  nome: string = '';
  email: string = '';
  celular: string = '';

  constructor(private http: HttpClient) {}

  onCpfChange(value: string) {
    this.cpf = this.formatarCPF(value);
  }

  formatarCPF(cpf: string): string {
    cpf = cpf.replace(/\D/g, '');
    if (cpf.length > 11) cpf = cpf.slice(0, 11);

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

  cadastrarCoordenador() {
    const coordenadorData = {
      cpf: this.cpf,
      nome: this.nome,
      email: this.email,
      celular: this.celular,
    };

    this.http
      .post('http://localhost:8080/sga-ic/api/coordenador/registrar', coordenadorData, { withCredentials: true })
      .subscribe({
        next: (res) => {
          console.log('Coordenador cadastrado com sucesso!', res);
          alert('Coordenador cadastrado!');
          this.resetForm();
        },
        error: (err) => {
          console.error('Erro ao cadastrar coordenador', err);
          alert('Erro ao cadastrar coordenador. Verifique os dados.');
        },
      });
  }

  resetForm() {
    this.cpf = '';
    this.nome = '';
    this.email = '';
    this.celular = '';
  }
}
