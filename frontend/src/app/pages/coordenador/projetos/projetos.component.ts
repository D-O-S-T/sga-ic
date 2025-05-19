import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';

interface Projeto {
  id: number;
  titulo: string;
  coordenador: string;
  status: string;
  dataInicio: string;
}

@Component({
  selector: 'app-projetos',
  standalone: true,
  templateUrl: './projetos.component.html',
  styleUrls: ['./projetos.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatTableModule,
    MatCardModule,
  ]
})
export class ProjetosComponent {
  filtroForm: FormGroup;
  projetos: Projeto[] = [];
  projetosFiltrados: Projeto[] = [];

  displayedColumns: string[] = ['titulo', 'coordenador', 'status', 'dataInicio'];

  constructor(private fb: FormBuilder) {
    this.filtroForm = this.fb.group({
      titulo: [''],
      status: ['']
    });

    this.projetos = [
      { id: 1, titulo: 'Sistema Acadêmico', coordenador: 'João Silva', status: 'Em andamento', dataInicio: '2024-01-15' },
      { id: 2, titulo: 'App de Saúde', coordenador: 'Maria Souza', status: 'Concluído', dataInicio: '2023-09-10' },
      { id: 3, titulo: 'Chatbot de Atendimento', coordenador: 'Carlos Mendes', status: 'Em andamento', dataInicio: '2024-03-05' }
    ];

    this.projetosFiltrados = [...this.projetos];
  }

  filtrarProjetos(): void {
    const { titulo, status } = this.filtroForm.value;

    this.projetosFiltrados = this.projetos.filter(p =>
      (!titulo || p.titulo.toLowerCase().includes(titulo.toLowerCase())) &&
      (!status || p.status === status)
    );
  }

  limparFiltros(): void {
    this.filtroForm.reset();
    this.projetosFiltrados = [...this.projetos];
  }
}
