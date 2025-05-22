import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { AlunoComponent } from './pages/aluno/aluno.component';
import { CoordenadorComponent } from './pages/coordenador/coordenador.component';
import { NgModule } from '@angular/core';
import { FormularioEditalComponent } from './pages/coordenador/formulario-edital/formulario-edital.component';
import { FormularioAtividadeComponent } from './pages/professor/formulario-atividade/formulario-atividade.component';
import { FormularioRelatorioComponent } from './pages/coordenador/formulario-relatorio/formulario-relatorio.component';
import { FormularioAlunoComponent } from './pages/coordenador/formulario-aluno/formulario-aluno.component';
import { ProjetosComponent } from './pages/coordenador/projetos/projetos.component';
import { FormularioProfessorComponent } from './pages/coordenador/formulario-professor/formulario-professor.component';
import { FormularioCoordenadorComponent } from './pages/coordenador/form-coordenador/formulario-coordenador.component';
import { AlunoListComponent } from './pages/coordenador/listar-alunos/listar-alunos.component';
import { EditalListComponent } from './pages/coordenador/listar-editais/listar-editais.component';
import { ProfessorListComponent } from './pages/coordenador/listar-professores/listar-professores.component';
import { CoordenadorListComponent } from './pages/coordenador/listar-coordenadores/listar-coordenadores.component';
import { HeaderComponent } from './shared/header/header.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { ProjetosProfessorComponent } from './pages/professor/projetos-professor/projetos-professor.component';
import { ProjetoAtividadesComponent } from './pages/aluno/projeto-atividades/projeto-atividades.component';

export const routes: Routes = [

    { path: 'navbar', component: NavbarComponent },
    { path: 'header', component: HeaderComponent },
    { path: 'sidebar', component: SidebarComponent },

    { path: 'home', component: HomeComponent },

    { path: 'aluno', component: AlunoComponent },
    { path: 'listar-alunos', component: AlunoListComponent },
    { path: 'form-aluno', component: FormularioAlunoComponent },
    { path: 'form-aluno/:id', component: FormularioAlunoComponent },
    { path: 'projeto/:id/atividades', component: ProjetoAtividadesComponent },

    { path: 'professor', component: ProfessorComponent },
    { path: 'listar-professores', component: ProfessorListComponent },
    { path: 'form-professor', component: FormularioProfessorComponent },
    { path: 'form-professor/:id', component: FormularioProfessorComponent },
    { path: 'projetos-professor', component: ProjetosProfessorComponent },
    {  path: 'formulario-atividade/:projetoId',component: FormularioAtividadeComponent },


    { path: 'coordenador', component: CoordenadorComponent },
    { path: 'listar-coordenadores', component: CoordenadorListComponent },
    { path: 'form-coordenador', component: FormularioCoordenadorComponent },
    { path: 'form-coordenador/:id', component: FormularioCoordenadorComponent },


    { path: 'form-edital', component: FormularioEditalComponent },
    { path: 'form-edital/:id**', component: FormularioEditalComponent },
    { path: 'listar-editais', component: EditalListComponent },

    { path: 'projetos', component: ProjetosComponent },
    { path: 'form-atividade', component: FormularioAtividadeComponent },
    { path: 'form-relatorio', component: FormularioRelatorioComponent },


   

];
