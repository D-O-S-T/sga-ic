import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { HeaderComponent } from '../../../shared/header/header.component';
import { CommonModule } from '@angular/common';
import { ResRelatorioComponent } from '../res-relatorio/res-relatorio.component';

@Component({
  selector: 'app-relatorio-respostas',
  standalone: true,
  imports: [CommonModule, HeaderComponent, SidebarComponent, ResRelatorioComponent],
  templateUrl: './relatorio-respostas.component.html',
  styleUrl: './relatorio-respostas.component.scss'
})
export class RelatorioRespostasComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil-professor' },
    { label: 'Projetos', route: '/professor' },
  ];

  mostrarModal = false;

  abrirModalResposta(): void {
    this.mostrarModal = true;
  }

  aoSalvarResposta(): void {
    this.mostrarModal = false;
    this.carregarRelatorio(); // função que recarrega a atividade
  }

  relatorio: any;
  carregando = true;
  erro = '';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.carregarRelatorio();
  }

  carregarRelatorio(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.http.get(`http://localhost:8080/sga-ic/api/relatorio/${id}`, { withCredentials: true })
        .subscribe({
          next: data => {
            this.relatorio = data;
            this.carregando = false;
            this.cdr.detectChanges();
          },
          error: err => {
            this.erro = 'Erro ao carregar relatorio';
            console.error(err);
            this.carregando = false;
          }
        });
    }
  }
}