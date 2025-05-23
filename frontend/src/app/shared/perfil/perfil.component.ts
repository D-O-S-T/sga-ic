import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

interface Usuario {
  id: number;
  nome: string;
  cpf: string;
  email: string;
  celular: string;
  curriculoLattes?: string;
  fotoPerfil?: string;
}

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss']
})
export class PerfilComponent implements OnInit {
  usuario: Usuario | null = null;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.http.get<Usuario>('http://localhost:8080/sga-ic/api/usuario/perfil-cheio', {
      withCredentials: true
    }).subscribe({
      next: (dados) => {
        this.usuario = dados;
        this.cdr.detectChanges(); // <- Agora está dentro da função `next`
      },
      error: (err) => console.error('Erro ao buscar perfil:', err)
    });
  }

  getFoto(): string {
    return this.usuario?.fotoPerfil || '../../../assets/images/profile_default.png';
  }
}