import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { HeaderComponent } from '../../../shared/header/header.component';
import { CommonModule } from '@angular/common';
import { ResAtividadeComponent } from '../res-atividade/res-atividade.component';

@Component({
  selector: 'app-atividade-respostas',
  standalone: true,
  imports: [CommonModule, HeaderComponent, SidebarComponent, ResAtividadeComponent],
  templateUrl: './atividade-respostas.component.html',
  styleUrl: './atividade-respostas.component.scss'
})
export class AtividadeRespostasComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-aluno' },
    { label: 'Projetos', route: '/aluno' },
  ];

  mostrarModal = false;

  abrirModalResposta(): void {
    this.mostrarModal = true;
  }

  aoSalvarResposta(): void {
    this.mostrarModal = false;
    this.carregarAtividade(); // função que recarrega a atividade
  }

  atividade: any;
  carregando = true;
  erro = '';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.carregarAtividade();
  }

  carregarAtividade(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.http.get(`http://localhost:8080/sga-ic/api/atividade/${id}`, { withCredentials: true })
        .subscribe({
          next: data => {
            this.atividade = data;
            this.carregando = false;
            this.cdr.detectChanges();
          },
          error: err => {
            this.erro = 'Erro ao carregar atividade';
            console.error(err);
            this.carregando = false;
          }
        });
    }
  }
}