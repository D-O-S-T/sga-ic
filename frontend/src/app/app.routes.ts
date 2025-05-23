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
import { EditaisComponent } from './pages/coordenador/editais-coordenador/editais-coordenador.component';

import { AtividadeRespostasComponent } from './pages/aluno/atividade-respostas/atividade-respostas.component';
import { PerfilAlunoComponent } from './pages/aluno/perfil-aluno/perfil-aluno.component';
import { ProjetoAtividadesProfComponent } from './pages/professor/projeto-atividades-prof/projeto-atividades-prof.component';
import { AtividadeRespostasProfComponent } from './pages/professor/atividade-respostas-prof/atividade-respostas-prof.component';
import { PerfilProfessorComponent } from './pages/professor/perfil-professor/perfil-professor.component';

import { ProjetosListComponent } from './pages/coordenador/listar-projetos/listar-projetos.component';
import { FormularioProjetoComponent } from './pages/coordenador/formulario-projeto/formulario-projeto.component';
import { ProjetoRelatoriosComponent } from './pages/professor/projeto-relatorios/projeto-relatorios.component';
import { RelatorioRespostasComponent } from './pages/professor/relatorio-respostas/relatorio-respostas.component';

export const routes: Routes = [

    { path: 'navbar', component: NavbarComponent },
    { path: 'header', component: HeaderComponent },
    { path: 'sidebar', component: SidebarComponent },

    { path: 'home', component: HomeComponent },

    { path: 'aluno', component: AlunoComponent },
    { path: 'perfil-aluno', component: PerfilAlunoComponent },
    { path: 'listar-alunos', component: AlunoListComponent },
    { path: 'form-aluno', component: FormularioAlunoComponent },
    { path: 'form-aluno/:id', component: FormularioAlunoComponent },
    { path: 'projeto/:id/atividades', component: ProjetoAtividadesComponent },
    { path: 'atividade/:id/respostas', component: AtividadeRespostasComponent },

    { path: 'professor', component: ProfessorComponent },
    { path: 'perfil-professor', component: PerfilProfessorComponent },
    { path: 'listar-professores', component: ProfessorListComponent },
    { path: 'form-professor', component: FormularioProfessorComponent },
    { path: 'form-professor/:id', component: FormularioProfessorComponent },
    { path: 'projeto/:id/relatorios', component: ProjetoRelatoriosComponent },
    { path: 'relatorio/:id/respostas', component: RelatorioRespostasComponent },
    { path: 'projeto/:id/atividades-prof', component: ProjetoAtividadesProfComponent },
    { path: 'atividade/:id/respostas-prof', component: AtividadeRespostasProfComponent },
    { path: 'projetos-professor', component: ProjetosProfessorComponent },
    { path: 'formulario-atividade/:projetoId', component: FormularioAtividadeComponent },


    { path: 'coordenador', component: CoordenadorComponent },
    { path: 'listar-coordenadores', component: CoordenadorListComponent },
    { path: 'form-coordenador', component: FormularioCoordenadorComponent },
    { path: 'form-coordenador/:id', component: FormularioCoordenadorComponent },
    { path: 'editais', component: EditaisComponent },
    { path: 'listar-projetos', component: ProjetosListComponent },
    { path: 'form-projeto', component: FormularioProjetoComponent },




    { path: 'form-edital', component: FormularioEditalComponent },
    { path: 'form-edital/:id**', component: FormularioEditalComponent },
    { path: 'listar-editais', component: EditalListComponent },


    { path: 'form-atividade', component: FormularioAtividadeComponent },
    { path: 'form-relatorio', component: FormularioRelatorioComponent },






];
