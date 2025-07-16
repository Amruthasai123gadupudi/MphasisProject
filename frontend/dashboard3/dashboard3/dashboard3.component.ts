import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard3',
  templateUrl: './dashboard3.component.html',
  styleUrls: ['./dashboard3.component.css']
})
export class Dashboard3Component {
  backPressCount = 0;
  constructor(private router: Router,private authService:AuthService) {}
  ngOnInit(): void {
    
    window.onpopstate = () => {
      this.backPressCount++;
      if (this.backPressCount >1) {
        this.authService.logout(); // Clear session or tokens
        this.router.navigate(['/']); // Redirect to home or login
        this.backPressCount = 0;
      }
    };
  }
  ngOnDestroy(): void {
    // Clean up listener when component destroyed
    window.onpopstate = null;
  }
  logout() {
    // Call your auth service to clear session/token
    this.authService.logout();
  
    // Navigate to login or home page after logout
    this.router.navigate(['/login']);
  }
  
}
