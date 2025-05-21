import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { AlunoComponent } from './pages/aluno/aluno.component';
import { CoordenadorComponent } from './pages/coordenador/coordenador.component';
import { NgModule } from '@angular/core';
import{FormularioEditalComponent} from './pages/coordenador/formulario-edital/formulario-edital.component';
import { FormularioAtividadeComponent } from './pages/professor/formulario-atividade/formulario-atividade.component';
import { FormularioRelatorioComponent } from './pages/coordenador/formulario-relatorio/formulario-relatorio.component';
import { FormularioAlunoComponent } from './pages/coordenador/formulario-aluno/formulario-aluno.component';
import{ProjetosComponent} from './pages/coordenador/projetos/projetos.component';
import{ FormularioProfessorComponent } from './pages/coordenador/formulario-professor/formulario-professor.component';
import { FormularioCoordenadorComponent } from './pages/coordenador/form-coordenador/formulario-coordenador.component';
import{AlunoListComponent} from './pages/aluno/listar-alunos/listar-alunos.component';
import {EditalListComponent} from './pages/coordenador/listar-editais/listar-editais.component';

export const routes: Routes = [

    { path: 'home', component: HomeComponent },
    { path: 'aluno', component: AlunoComponent },
    { path: 'professor', component: ProfessorComponent },
    { path: 'coordenador', component: CoordenadorComponent },
    { path: 'navbar', component: NavbarComponent },
    {path: 'projetos', component: ProjetosComponent},
    {path: '', component: FormularioEditalComponent},
    {path: 'form-atividade', component: FormularioAtividadeComponent},
    {path: 'form-relatorio', component: FormularioRelatorioComponent},
    {path: 'form-aluno', component: FormularioAlunoComponent},
    {path: 'form-professor', component: FormularioProfessorComponent},
    {path: 'form-coordenador', component: FormularioCoordenadorComponent},
    {path: 'listar-alunos', component: AlunoListComponent},
    {path: 'listar-editais', component: EditalListComponent},
   
 
   
    
];
