import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatFormField } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';

@Component({
  selector: 'app-formulario-atividade',
  standalone: true,
  imports: [FormsModule, MatFormField, MatLabel, MatCardModule, MatButtonModule, MatFormFieldModule],
  templateUrl: './formulario-atividade.component.html',
  styleUrl: './formulario-atividade.component.scss'
})
export class FormularioAtividadeComponent implements OnInit {

  titulo = '';
  descricao = '';
  dataAbertura = '';
  dataEncerramento = '';
  projetoId!: number;
  selectedFile: File | null = null;

  constructor(private http: HttpClient, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id = params.get('projetoId');
      if (id) {
        this.projetoId = +id; // Converte string para número
      }
    });
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
    }
  }

  uploadFile() {
    if (!this.selectedFile) {
      alert('Por favor, selecione um arquivo.');
      return;
    }

    const formData = new FormData();
    formData.append('titulo', this.titulo);
    formData.append('descricao', this.descricao);
    formData.append('dataAbertura', this.dataAbertura);
    formData.append('dataEncerramento', this.dataEncerramento);
    formData.append('projetoId', this.projetoId.toString());
    formData.append('arquivos', this.selectedFile, this.selectedFile.name);

    this.http.post('http://localhost:8080/sga-ic/api/atividade/registrar', formData, { withCredentials: true }).subscribe({
      next: (res) => {
        console.log('Dados enviados com sucesso', res);
        alert('Upload realizado com sucesso!');
        this.resetForm();
      },
      error: (err) => {
        console.error('Erro ao enviar dados', err);
        alert('Erro ao enviar dados. Tente novamente.');
      },
    });
  }

  resetForm() {
    this.titulo = '';
    this.descricao = '';
    this.dataAbertura = '';
    this.dataEncerramento = '';
    this.projetoId = 0;
    this.selectedFile = null;
  }
}
