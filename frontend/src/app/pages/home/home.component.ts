import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { merge } from 'rxjs';
import { HomeService } from '../../services/home.service';
import { NgIf } from '@angular/common'; // Import necessário para *ngIf
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

@Component({
  selector: 'app-home',
  standalone: true,
  providers: [
    {
      provide: MAT_FORM_FIELD_DEFAULT_OPTIONS,
      useValue: { appearance: 'fill', density: 'compact' }
    },
    provideNgxMask() // Se estiver usando ngx-mask standalone
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatDividerModule,
    MatButtonModule,
    NgIf,
    NgxMaskDirective // Diretiva da máscara
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})


export class HomeComponent {

   cpf = new FormControl('', [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/)]);
  senha = new FormControl('', [Validators.required]);
  errorMessage = '';
   private passwordHidden = true; 

   
  hide(): boolean {
    return this.passwordHidden;
  }

  clickEvent(event: Event): void {
    event.preventDefault(); // previne efeitos indesejados do botão
    this.passwordHidden = !this.passwordHidden;
  }

  constructor(private homeService: HomeService) {}

  onCpfChange(value: string) {
    // Atualiza o FormControl com o valor formatado (sem disparar event change)
    this.cpf.setValue(value, { emitEvent: false });
  }

  login() {
    if (this.cpf.invalid || this.senha.invalid) {
      this.errorMessage = 'CPF e senha são obrigatórios e devem ser válidos';
      return;
    }

    const cpfFormatado = this.cpf.value!;
    const senha = this.senha.value!;

    console.log('Enviando para backend:', { cpf: cpfFormatado, senha });

    this.homeService.login(cpfFormatado, senha);
  }
}