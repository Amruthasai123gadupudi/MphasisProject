import { HttpClient } from '@angular/common/http';
import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { User } from '../models/user';

@Component({
  selector: 'app-buyer-dashboard',
  templateUrl: './buyer-dashboard.component.html',
  styleUrls: ['./buyer-dashboard.component.css'],
})
export class BuyerDashboardComponent implements OnInit{
  sidebarOpen = false;
  
user: User | null = null;
ngOnInit() {
  const userJson = localStorage.getItem('user');
 
  if (userJson) {
    this.user = JSON.parse(userJson) as User;
  } else {
    this.user = null; // or redirect to login if needed
  }
}

    constructor(private http: HttpClient,private authService:AuthService,private router:Router) {}
  toggleSidebar() {
    this.sidebarOpen = !this.sidebarOpen;
  }

  // Close sidebar on route click if on mobile
  closeSidebarOnMobile() {
    if (window.innerWidth < 768) {
      this.sidebarOpen = false;
    }
  }

  // Optional: close sidebar on window resize if desktop
  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    if (window.innerWidth >= 768 && this.sidebarOpen) {
      this.sidebarOpen = false;
    }
  }
  logout() {
    console.log(this.user);
    // Call your auth service to clear session/token
    this.authService.logout();
  
    // Navigate to login or home page after logout
    this.router.navigate(['/login']);
  }
}
