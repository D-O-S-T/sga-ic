import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { SidebarComponent, NavItem } from '../../../shared/sidebar/sidebar.component';
import { HeaderComponent } from '../../../shared/header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-atividade-respostas',
  standalone: true,
  imports: [CommonModule, HeaderComponent, SidebarComponent],
  templateUrl: './atividade-respostas.component.html',
  styleUrl: './atividade-respostas.component.scss'
})
export class AtividadeRespostasComponent implements OnInit {

  navItems: NavItem[] = [
    { label: 'Perfil', route: '/perfil' },
    { label: 'Projetos', route: '/aluno' },
  ];

  atividade: any;
  carregando = true;
  erro = '';

  constructor(
    private http: HttpClient,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
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