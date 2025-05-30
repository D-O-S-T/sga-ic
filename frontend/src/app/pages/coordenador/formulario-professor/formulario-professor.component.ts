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
import { Router } from '@angular/router';

interface Professor {
  id: number;
  cpf: string;
  nome: string;
  email: string;
  descricao: string;
  curriculoLattes: string;
  celular: string;
}

@Component({
  selector: 'app-formulario-professor',
  standalone: true,
  imports: [FormsModule, MatFormFieldModule, MatButtonModule, MatCardModule, NgxMaskDirective],
  providers: [provideNgxMask()],
  templateUrl: './formulario-professor.component.html',
  styleUrl: './formulario-professor.component.scss',
})
export class FormularioProfessorComponent {

  @ViewChild('formProfessor') formProfessor!: NgForm;

  id?: number;

  cpf = '';
  nome = '';
  email = '';
  curriculoLattes = '';
  celular = '';

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = +idParam;
      this.carregarAluno(this.id);
    }
  }

  private carregarAluno(id: number) {
    this.http.get<Professor>(`http://localhost:8080/sga-ic/api/professor/${id}`, { withCredentials: true })
      .subscribe({
        next: (professor) => this.zone.run(() => {
          // 1. Atualiza o modelo
          this.cpf = this.formatarCPF(professor.cpf);
          this.nome = professor.nome;
          this.email = professor.email;
          this.curriculoLattes = professor.curriculoLattes;
          this.celular = professor.celular;

          // 2. Empurra os valores para os controles do NgForm
          this.formProfessor.form.patchValue({
            cpf: this.cpf,
            nome: this.nome,
            email: this.email,
            curriculoLattes: this.curriculoLattes,
            celular: this.celular,
          });

          // 3. (Opcional) Mantém o formulário “pristine”
          this.formProfessor.form.markAsPristine();
        }),
        error: err => console.error('Falha ao carregar aluno', err)
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
    private zone: NgZone,
    private router: Router
  ) { }

  cadastrarProfessor(): void {
    const professorData = {
      cpf: this.formatarCPF(this.cpf),
      nome: this.nome,
      email: this.email,
      curriculoLattes: this.curriculoLattes,
      celular: this.celular,
    };

    const req$ = this.id
      ? this.http.put(`http://localhost:8080/sga-ic/api/professor/editar/${this.id}`, professorData, { withCredentials: true })
      : this.http.post('http://localhost:8080/sga-ic/api/professor/registrar', professorData, { withCredentials: true });

    req$.subscribe({
      next: () => {
        alert(`Professor ${this.id ? 'atualizado' : 'salvo'} com sucesso!`);
        if (!this.id) this.resetForm(); // em edição você pode redirecionar, se quiser
        this.router.navigate(['/listar-professores']);
      },
      error: (err) => {
        console.error('Erro ao salvar professor', err);
        alert('Erro ao salvar professor. Verifique os dados.');
      },
    });
  }

  resetForm() {
    this.cpf = '';
    this.nome = '';
    this.email = '';
    this.curriculoLattes = '';
    this.celular = '';
  }
}
