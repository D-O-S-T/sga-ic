import { Component, Inject, Optional } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';

@Component({
  selector: 'app-formulario-edital',
  templateUrl: './formulario-edital.component.html',
  styleUrls: ['./formulario-edital.component.scss'],
  standalone: true,
  // changeDetection: ChangeDetectionStrategy.OnPush, // opcional
  imports: [
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,
  ],
})
export class FormularioEditalComponent {
  edital = {
    titulo: '',
    descricao: '',
    dataInicio: '',
    dataFim: ''
  };

   constructor(
    @Optional() @Inject(MatDialogRef) public dialogRef: MatDialogRef<FormularioEditalComponent>
  ) { }
  salvar() {
    this.dialogRef.close(this.edital);
  }

  fechar() {
    this.dialogRef.close();
  }
}

