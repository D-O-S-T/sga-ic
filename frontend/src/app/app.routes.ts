import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { NavbarComponent } from './shared/navbar/navbar.component';
import { ProfessorComponent } from './pages/professor/professor.component';
import { AlunoComponent } from './pages/aluno/aluno.component';
import { CoordenadorComponent } from './pages/coordenador/coordenador.component';
import { NgModule } from '@angular/core';
import{CadastrarEditalComponent} from './pages/coordenador/cadastrar-edital/cadastrar-edital.component';



export const routes: Routes = [

    { path: 'home', component: HomeComponent },
    { path: 'aluno', component: AlunoComponent },
    { path: 'professor', component: ProfessorComponent },
    { path: 'coordenador', component: CoordenadorComponent },
    { path: 'navbar', component: NavbarComponent },
    {path: '', component: CoordenadorComponent}
];
