import { ChangeDetectionStrategy, Component, Inject, Optional } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; 
import { EditalService } from '../../../services/edital.service';
import { Edital } from '../../../services/edital.service';

@Component({
  selector: 'app-formulario-edital',
  templateUrl: './formulario-edital.component.html',
  styleUrls: ['./formulario-edital.component.scss'],
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush, 
  imports: [
    MatCardModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    FormsModule,MatDatepickerModule,
    MatNativeDateModule
  ],
})
export class FormularioEditalComponent {
  edital = {
    titulo: '',
    descricao: '',
    dtInicio: '',
    dtFim: '',
    instituicao: '',
    qtdAlunos: 0,
    qtdProjetos: 0,
    qtdBolsistas: 0,
    qtdProfessores: 0
  };

   constructor(
      private editalService: EditalService,
    @Optional() @Inject(MatDialogRef) public dialogRef: MatDialogRef<FormularioEditalComponent>
  ) { }
  salvar() {
      this.editalService.criarEdital(this.edital).subscribe({
     next: (response: Edital) => {
  console.log('Edital salvo com sucesso!', response);
  this.dialogRef.close(response); // Ou this.edital se preferir
}
 });

  } 

  fechar() {
    this.dialogRef.close();
  }
}

