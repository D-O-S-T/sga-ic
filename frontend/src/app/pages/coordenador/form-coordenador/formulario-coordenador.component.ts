import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import { ChangeDetectorRef, NgZone } from '@angular/core';
import { ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

interface Coordenador {
  id: number;
  cpf: string;
  nome: string;
  email: string;
  descricao: string;
  celular: string;
}

@Component({
  selector: 'app-formulario-coordenador',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatButtonModule, MatCardModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './formulario-coordenador.component.html',
  styleUrl: './formulario-coordenador.component.scss',
})
export class FormularioCoordenadorComponent {

  @ViewChild('formCoordenador') formCoordenador!: NgForm;

  id?: number;

  cpf = '';
  nome = '';
  email = '';
  celular = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.carregarCoordenador(this.id);
    }
  }

  private carregarCoordenador(id: number) {
    this.http.get<Coordenador>(`http://localhost:8080/sga-ic/api/coordenador/${id}`, { withCredentials: true })
      .subscribe({
        next: (coordenador) => this.zone.run(() => {
          // 1. Atualiza o modelo
          this.cpf = this.formatarCPF(coordenador.cpf);
          this.nome = coordenador.nome;
          this.email = coordenador.email;
          this.celular = coordenador.celular;

          // 2. Empurra os valores para os controles do NgForm
          this.formCoordenador.form.patchValue({
            cpf: this.cpf,
            nome: this.nome,
            email: this.email,
            celular: this.celular,
          });

          // 3. (Opcional) Mantém o formulário “pristine”
          this.formCoordenador.form.markAsPristine();
        }),
        error: err => console.error('Falha ao carregar coordenador', err)
      });
  }

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

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private cd: ChangeDetectorRef,
    private zone: NgZone
  ) { }

  cadastrarCoordenador(): void {
    const coordenadorData = {
      cpf: this.formatarCPF(this.cpf),
      nome: this.nome,
      email: this.email,
      celular: this.celular,
    };

    const req$ = this.id
      ? this.http.put(`http://localhost:8080/sga-ic/api/coordenador/editar/${this.id}`, coordenadorData, { withCredentials: true })
      : this.http.post('http://localhost:8080/sga-ic/api/coordenador/registrar', coordenadorData, { withCredentials: true });

    req$.subscribe({
      next: () => {
        alert(`Coordenador ${this.id ? 'atualizado' : 'salvo'} com sucesso!`);
        if (!this.id) this.resetForm(); // em edição você pode redirecionar, se quiser
      },
      error: (err) => {
        console.error('Erro ao salvar coordenador', err);
        alert('Erro ao salvar coordenador. Verifique os dados.');
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