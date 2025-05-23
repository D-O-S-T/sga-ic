import { Component, Input, Output, EventEmitter } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-res-relatorio',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './res-relatorio.component.html',
  styleUrls: ['./res-relatorio.component.scss']
})
export class ResRelatorioComponent {
  @Input() relatorioId!: number;
  @Output() fecharModal = new EventEmitter<void>();
  @Output() respostaSalva = new EventEmitter<void>();

  descricao = '';

  constructor(private http: HttpClient) { }

  selectedFiles: File[] = [];

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files) {
      this.selectedFiles = Array.from(input.files); // converte FileList em array
    }
  }

  uploadResposta(): void {
    if (!this.selectedFiles.length) {
      alert('Por favor, selecione pelo menos um arquivo.');
      return;
    }

    const formData = new FormData();
    formData.append('descricao', this.descricao);
    formData.append('relatorioId', this.relatorioId.toString());

    this.selectedFiles.forEach((file, index) => {
      formData.append('arquivos', file, file.name); // backend espera lista com mesmo nome
    });

    this.http.post('http://localhost:8080/sga-ic/api/res-relatorio/registrar', formData, {
      withCredentials: true
    }).subscribe({
      next: () => {
        alert('Resposta enviada com sucesso!');
        this.respostaSalva.emit();
      },
      error: err => {
        console.error('Erro ao enviar resposta', err);
        alert('Erro ao enviar resposta.');
      }
    });
  }
  
  fechar(): void {
    this.fecharModal.emit();
  }
}