import { Routes } from '@angular/router';
import { HomePage } from './pages/home/home.page';
import { RegisterPage } from './pages/register/register.page';
import { LoginPage } from './pages/sign-in/sign-in.page';
import { AventuresPage } from './pages/aventures/aventures.page';
import { ExplorePage } from './pages/explore/explore.page';
import { TalePage } from './pages/tale/tale.page';
import { EditorPage } from './pages/editor/editor.page';
import { TalesDashboardComponent } from './pages/tales-dashboard/tales-dashboard.component';

export const routes: Routes = [
    {
        path: '',
        pathMatch: 'full',
        redirectTo: 'explore',
    },
    {
        path: 'home',
        component: HomePage,
    },
    {
        path: 'register',
        component: RegisterPage,
    },
    {
        path: 'login',
        component: LoginPage,
    },
    {
        path: 'tale/:taleId',
        component: TalePage,
    },
    {
        path: 'aventures',
        component: AventuresPage,
    },
    {
        path: 'explore',
        component: ExplorePage,
    },
    {
        path: 'tales-dashboard',
        component: TalesDashboardComponent,
    },
    {
        path: 'aventures/:id',
        component: AventuresPage,
    },
    {
        path: 'editor/:taleId',
        component: EditorPage,
    }
];
