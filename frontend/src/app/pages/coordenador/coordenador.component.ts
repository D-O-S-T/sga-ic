
import {ChangeDetectionStrategy, Component} from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import {MatListModule} from '@angular/material/list';
import { MatDialog } from '@angular/material/dialog';
import {MatDialogRef} from '@angular/material/dialog';
import {FormularioEditalComponent} from './formulario-edital/formulario-edital.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-coordenador',
  standalone: true,
   imports: [MatCardModule, MatButtonModule,MatListModule, MatDividerModule, FormularioEditalComponent,  MatDatepickerModule,
  MatNativeDateModule ],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './coordenador.component.html',
  styleUrl: './coordenador.component.scss'
})
export class CoordenadorComponent {

   longText = `The Shiba Inu is the smallest of the six original and distinct spitz breeds of dog
  from Japan. A small, agile dog that copes very well with mountainous terrain, the Shiba Inu was
  originally bred for hunting.`;

   constructor(private dialog: MatDialog) {}

  abrirFormulario() {
    this.dialog.open(FormularioEditalComponent, {
      width: '90%' // pode ajustar o tamanho
    });
  }

}
