
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { ProjetoService } from '../../../services/projeto.service';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-projetos-list',
  templateUrl: './listar-projetos.component.html',
  styleUrls: ['./listar-projetos.component.scss'],
  imports: [CommonModule],
  standalone: true
})
export class ProjetosListComponent implements OnChanges {

  @Input() editalId!: number;

  projetos: any[] = [];
  carregando = false;
  erro: string | null = null;

  constructor(private cdr: ChangeDetectorRef, private projetoService: ProjetoService) { }

  ngOnChanges(changes: SimpleChanges): void {
  if (changes['editalId']) {
    console.log('editalId mudou para', this.editalId);
    if (this.editalId) {
      this.carregarProjetos();
    }
  }
}


  carregarProjetos(): void {
  this.carregando = true;
  this.erro = null;
  this.projetos = [];

  this.projetoService.getProjetosPorEdital(this.editalId).subscribe({
    next: (dados) => {
      this.projetos = dados;
      this.carregando = false;
        this.cdr.detectChanges(); 
    },
    error: (err) => {
      this.erro = 'Erro ao carregar projetos.';
      this.carregando = false;
      console.error(err);
       this.cdr.detectChanges();
    }
  });
}




}
