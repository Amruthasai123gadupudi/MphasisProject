import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard1',
  templateUrl: './dashboard1.component.html',
  styleUrls: ['./dashboard1.component.css']
})
export class Dashboard1Component {
  constructor(private router: Router,private authService:AuthService) {}
  
  // @HostListener('window:popstate', ['$event'])
  // onPopState(event: any) {
  //   // Immediately logout and redirect on first back press
  //   this.authService.logout();
  // }
  
  logout() {
    // Call your auth service to clear session/token
    this.authService.logout();
  
    // Navigate to login or home page after logout
    this.router.navigate(['/login']);
  }
}
