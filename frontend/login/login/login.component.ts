

import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { jwtDecode } from 'jwt-decode';
 // <--- Import AuthService

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  message = '';
  isSuccess = false;
  user = { email: '', password: '' };
  // Inject AuthService here
  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) { }

  login() {
    this.authService.login(this.user.email, this.user.password).subscribe({
      next: (response) => {
        const token = response.token;
        this.authService.storeToken(token);
        localStorage.setItem('user', JSON.stringify(response.user));
        const decoded: any = jwtDecode(token);
        const role = decoded.role;

        switch (role) {
          case 'BUYER':
            this.router.navigate(['/dashboard-buyer']);
            break;
          case 'SELLER':
            this.router.navigate(['/dashboard-seller']);
            break;
          case 'ADMIN':
              this.router.navigate(['/dashboard-admin']);
              break;
          case 'MANAGER':
                this.router.navigate(['/dashboard-manager']);
                break;
        }
       
      
      },
      error: (errMsg) => {
        this.isSuccess = false;
        this.message = errMsg; // Show backend error message like "Invalid email or password"
      }
    });
  }
  
  goBackHome() {
    this.router.navigate(['/']);
  }
  
}
