import { Component } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  user = {
    name: '',
    email: '',
    password: '',
    role: ''
  };

  message: string = '';
  isSuccess: boolean = false;

  constructor(private http: HttpClient,private router: Router) {}

  register(): void {
    if (!this.user.name || !this.user.email || !this.user.password || !this.user.role) {
      this.message = 'Please fill in all fields';
      this.isSuccess = false;
      return;
    }

    this.http.post<any>('http://localhost:8082/api/auth/register', this.user)
      .subscribe(
        (response) => {
          this.message = 'Registration successful';
          this.isSuccess = true;
        },
        (error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.message = 'You are already registered with this email.';
          } else {
            this.message = 'Registration failed. Please try again.';
          }
          this.isSuccess = false;
          console.error('Registration failed:', error);
        }
      );
  }
  
  goBackHome() {
    this.router.navigate(['/']);
  }
}
