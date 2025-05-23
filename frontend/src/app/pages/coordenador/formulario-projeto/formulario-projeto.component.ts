import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { ProjetoService, Projeto } from '../../../services/projeto.service';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formulario-projeto',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './formulario-projeto.component.html',
  styleUrl: './formulario-projeto.component.scss'
})
export class FormularioProjetoComponent implements OnChanges {
 editalId!: number;
  @Output() projetoCriado = new EventEmitter<void>();
  @Output() cancelar = new EventEmitter<void>();

  novoProjeto: Projeto = {
    titulo: '',
    descricao: '',
    editalId: 0
  };

  carregando = false;
  erro = '';

    constructor(private route: ActivatedRoute, private projetoService: ProjetoService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.editalId = +params['editalId'] || 0;
      this.novoProjeto.editalId = this.editalId;
    });
  }

  ngOnChanges() {
    if (this.editalId) {
      this.novoProjeto.editalId = this.editalId;
    }
  }

  salvarProjeto() {
    this.carregando = true;
    this.erro = '';

    this.projetoService.criarProjeto(this.novoProjeto).subscribe({
      next: (projeto: Projeto) => {
        this.carregando = false;
        this.projetoCriado.emit();
      },
      error: (err: any) => {
        this.carregando = false;
        this.erro = 'Erro ao salvar projeto. Tente novamente.';
        console.error(err);
      }
    });
  }

  cancelarForm() {
    this.cancelar.emit();
  }
}
