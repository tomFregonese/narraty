import { Component } from '@angular/core';
import { MainBlockComponent } from "../../components/main-block/main-block.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { LinkButtonComponent } from '../../components/link-button/link-button.component';
import { FormInputComponent } from "../../components/forms/form-input/form-input.component";
import { FormButtonComponent } from "../../components/forms/form-button/form-button.component";
import {AuthService} from '../../business/services/auth.service';
import {Router} from '@angular/router';
import {Login} from '../../business/models/login.model';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-sign-in',
    imports: [MainBlockComponent, ReactiveFormsModule, LinkButtonComponent, FormInputComponent, FormButtonComponent, CommonModule],
    templateUrl: './sign-in.page.html',
    styleUrl: './sign-in.page.scss'
})
export class LoginPage {

    protected errorMessage: string | null = null;

    constructor(private authService: AuthService,
                private router: Router) { }

    protected login = new FormGroup({
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', Validators.required),
    })

    protected onSubmit() {
        if (this.login.invalid) return; // TODO the user should be notified
        this.loginUser();
    }

    loginUser() {
        const credentials: Login = {
            email: this.login.value.email ?? '',
            password: this.login.value.password ?? ''
        };
    
        this.authService.login(credentials).subscribe({
            next: () => {
                this.errorMessage = null; 
                this.router.navigate(['/explore']);
            },
            error: (err) => {
                console.error('Login error:', err);
                this.errorMessage = err.error?.message || 'Une erreur est survenue lors de la connexion. Veuillez réessayer.';
            }
        });
    }


}
