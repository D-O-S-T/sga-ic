import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { AlunoComponent } from './pages/aluno/aluno.component';
import { CoordenadorComponent } from './pages/coordenador/coordenador.component';
import { NgModule } from '@angular/core';
import{FormularioEditalComponent} from './pages/coordenador/formulario-edital/formulario-edital.component';

import{ProjetosComponent} from './pages/coordenador/projetos/projetos.component';



export const routes: Routes = [

    { path: 'home', component: HomeComponent },
    { path: 'aluno', component: AlunoComponent },
    { path: 'professor', component: ProfessorComponent },
    { path: 'coordenador', component: CoordenadorComponent },
    { path: 'navbar', component: NavbarComponent },
    {path: 'projetos', component: ProjetosComponent},
    {path: '', component: FormularioEditalComponent}
 
   
    
];
