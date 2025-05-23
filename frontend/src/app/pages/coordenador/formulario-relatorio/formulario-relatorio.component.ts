import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core'; // <- Importa Input
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-relatorio-atividade',
  standalone: true,
  imports: [FormsModule, MatCardModule, MatButtonModule, MatFormFieldModule],
  templateUrl: './formulario-relatorio.component.html',
  styleUrl: './formulario-relatorio.component.scss'
})
export class FormularioRelatorioComponent {
  
  @Input() editalId!: number; // <- Adicionado Input

  titulo = '';
  descricao = '';
  dataAbertura = '';
  dataEncerramento = '';
  selectedFile: File | null = null;

  constructor(private http: HttpClient) {}

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
    formData.append('editalId', this.editalId.toString());
    formData.append('arquivo', this.selectedFile, this.selectedFile.name);

    this.http.post('http://localhost:8080/sga-ic/api/relatorio/registrar', formData, { withCredentials: true }).subscribe({
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
    this.selectedFile = null;
  }
}
