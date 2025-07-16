import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-dashboard4',
  templateUrl: './dashboard4.component.html',
  styleUrls: ['./dashboard4.component.css']
})
export class Dashboard4Component implements OnInit, OnDestroy{
  backPressCount = 0;
   constructor(private router: Router,private authService:AuthService) {}
  ngOnInit(): void {
    
    window.onpopstate = () => {
     
      
        this.authService.logout(); // Clear session or tokens
        this.router.navigate(['/']); // Redirect to home or login
       
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
