import { ChangeDetectorRef, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';

export interface Edital {
   id: number;
  titulo: string;
  instituicao: string;
  descricao: string;
  qtdBolsistas: number;
  qtdAlunos: number;
  qtdProfessores: number;
  qtdProjetos: number;
  dtInicio: string;
  dtFim: string;
}


@Component({
  selector: 'app-editais',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './editais-coordenador.component.html',
  styleUrls: ['./editais-coordenador.component.scss']
})
export class EditaisComponent implements OnInit {

    @Output() editalSelecionado = new EventEmitter<number>();

  editais: Edital[] = [];
  carregando = true;
  erro = '';

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.http.get<Edital[]>('http://localhost:8080/sga-ic/api/edital', { withCredentials: true }).subscribe({
      next: dados => {
           console.log('Dados recebidos da API:', dados);  
        this.editais = dados;
        this.carregando = false;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error(err);
        this.erro = 'Erro ao carregar os editais.';
        this.carregando = false;
      }
    });
  }

   selecionarEdital(id: number) {
    this.editalSelecionado.emit(id);
  }

  
  editalSelecionado1?: Edital;
  modalAberta = false;





  fecharModal() {
    this.modalAberta = false;
  }
}