import { Component, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth-service.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  isSmallScreen = false;
  menuExpanded = false;
  username: string = '';  // <-- added username property

  constructor(private router: Router, private authService: AuthService) {}

  @HostListener('window:resize', [])
  onResize() {
    this.isSmallScreen = window.innerWidth < 768;

    if (!this.isSmallScreen) {
      this.menuExpanded = true; // Always show on large screens
    }
  }

  ngOnInit() {
    this.onResize(); // Trigger screen check on init
    this.loadUsername(); // Load username on init
  }

  loadUsername(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      const user = JSON.parse(userJson);
      this.username = user.username || user.name || ''; // Adjust according to your user object structure
    }
  }

  toggleMenu() {
    if (this.isSmallScreen) {
      this.menuExpanded = !this.menuExpanded;
    }
  }

  logout() {
    // Call your auth service to clear session/token
    this.authService.logout();
  
    // Navigate to login or home page after logout
    this.router.navigate(['/login']);
  }
}
