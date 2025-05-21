import { ChangeDetectionStrategy, Component, Inject, Optional, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core'; 
import { EditalService, Edital } from '../../../services/edital.service';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { MatIcon } from '@angular/material/icon';

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
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    RouterModule,
    MatIcon
  ],
})
export class FormularioEditalComponent implements OnInit {
  
  edital: Edital = {
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

  idEdital: string | null = null;

  constructor(
    private editalService: EditalService,
    private route: ActivatedRoute,
    private router: Router,
    @Optional() @Inject(MatDialogRef) public dialogRef: MatDialogRef<FormularioEditalComponent>
  ) {}

  ngOnInit(): void {
    this.idEdital = this.route.snapshot.paramMap.get('id');

    if (this.idEdital) {
      this.editalService.buscarEditalPorId(this.idEdital).subscribe({
        next: (dados: Edital) => {
          this.edital = dados;
        },
        error: () => {
          console.error('Erro ao carregar edital');
        }
      });
    }
  }

  salvar() {
    if (this.idEdital) {
      this.editalService.atualizarEdital(this.idEdital, this.edital).subscribe({
        next: (res) => {
          console.log('Edital atualizado com sucesso!', res);
          this.voltarParaLista();
        }
      });
    } else {
      this.editalService.criarEdital(this.edital).subscribe({
        next: (res) => {
          console.log('Edital criado com sucesso!', res);
          this.voltarParaLista();
        }
      });
    }
  }

  fechar() {
    if (this.dialogRef) {
      this.dialogRef.close();
    } else {
      this.router.navigate(['/listar-editais']);
    }
  }

  voltarParaLista() {
    if (this.dialogRef) {
      this.dialogRef.close();
    } else {
      this.router.navigate(['/listar-editais']);
    }
  }
}
