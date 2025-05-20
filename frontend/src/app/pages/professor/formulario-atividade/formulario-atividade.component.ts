import { ChangeDetectionStrategy, Component, Inject, Optional } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import { MatInput, MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule, MatDatepickerToggle } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; 
import { MatIconModule } from '@angular/material/icon';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';
import { MatCard } from '@angular/material/card';

@Component({
  selector: 'app-formulario-atividade',
  standalone: true,
  imports: [FormsModule,MatFormField,MatInput,MatLabel,MatDatepickerModule, MatNativeDateModule,MatDatepickerToggle,MatIconModule,MatButtonModule,MatCardModule,MatDialogModule],
  templateUrl: './formulario-atividade.component.html',
  styleUrl: './formulario-atividade.component.scss'
})
export class FormularioAtividadeComponent {

   titulo = '';
  descricao = '';
  dataAbertura = '';
  dataEncerramento = '';
  projetoId!: number;
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
    formData.append('projetoId', this.projetoId.toString());
    formData.append('arquivos', this.selectedFile, this.selectedFile.name);

    this.http.post('http://localhost:8080/sga-ic/api/atividade/registrar', formData,{ withCredentials: true }).subscribe({
      next: (res) => {
        console.log('Dados enviados com sucesso', res);
        alert('Upload realizado com sucesso!');
        
      },
      error: (err) => {
        console.error('Erro ao enviar dados', err);
        alert('Erro ao enviar dados. Tente novamente.');
      },
     
    });
  }

  

}
