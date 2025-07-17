import { Component } from '@angular/core';
import { MainBlockComponent } from '../../components/main-block/main-block.component';
import {
    AbstractControl,
    FormControl,
    FormGroup,
    ReactiveFormsModule,
    ValidationErrors,
    ValidatorFn,
    Validators
} from '@angular/forms';
import { LinkButtonComponent } from "../../components/link-button/link-button.component";
import { FormInputComponent } from "../../components/forms/form-input/form-input.component";
import { FormButtonComponent } from '../../components/forms/form-button/form-button.component';
import {AuthService} from '../../business/services/auth.service';
import {Router} from '@angular/router';
import {Register} from '../../business/models/register.model';
import { CommonModule } from '@angular/common';

@Component({
    selector: 'app-register',
    imports: [MainBlockComponent, ReactiveFormsModule, LinkButtonComponent, FormInputComponent, FormButtonComponent,CommonModule],
    templateUrl: './register.page.html',
    styleUrl: './register.page.scss'
})
export class RegisterPage {

    protected errorMessage: string | null = null;
    

    constructor(private authService: AuthService,
                private router: Router) { }


    protected register = new FormGroup({
        username: new FormControl('', [Validators.required, Validators.minLength(3), Validators.maxLength(20)]),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(64), passwordComplexityValidator()]),
        confirmPassword: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(64)])
    }, { validators: this.confirmPasswordValidator()});

    private confirmPasswordValidator(): ValidatorFn {
        return (control: AbstractControl): ValidationErrors | null => {
            return control.value.password === control.value.confirmPassword ? null : { PasswordNoMatch: true };
        };
    }

    protected onSubmit() {
        if (this.register.invalid) return; // TODO the user should be notified
        this.registerUser();
    }

    registerUser() {
        const credentials: Register = {
            username: this.register.value.username ?? '',
            email: this.register.value.email ?? '',
            password: this.register.value.password ?? ''
        };
    
        this.authService.registerUser(credentials).subscribe({
            next: () => {
                this.errorMessage = null; 
                this.router.navigate(['/explore']);
            },
            error: (err) => {
                console.error('Registration error:', err);
                this.errorMessage = err.error?.message || 'Une erreur est survenue lors de l\'enregistrement. Veuillez réessayer.';
            }
        });
    }
    


      
}

function passwordComplexityValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const value: string = control.value || '';
  
      const hasUpperCase = /[A-Z]/.test(value);
      const hasLowerCase = /[a-z]/.test(value);
      const hasDigit = /\d/.test(value);
      const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(value);
      const hasMinLength = value.length >= 8;
  
      const isValid = hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar && hasMinLength;
  
      return isValid ? null : { passwordComplexity: true };
    };
  }