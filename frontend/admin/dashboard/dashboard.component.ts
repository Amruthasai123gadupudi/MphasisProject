import { Component, OnInit } from '@angular/core';
import { ReportService } from '../services/report.service';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  
  userRoleReport: any[] = [];
  platformPerformance: any;
  username: string = ''; // 👈 added

  constructor(
    private reportService: ReportService,
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadUsername(); // 👈 load from localStorage
    this.loadUserRoleReport();
    this.loadPlatformPerformance();
  }

  loadUsername(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      const user = JSON.parse(userJson);
      this.username = user.username || user.name || ''; // Adjust based on your backend user model
    }
  }

  loadUserRoleReport() {
    this.reportService.getUserRoleReport().subscribe((data: any[]) => {
      this.userRoleReport = data;
    });
  }

  loadPlatformPerformance() {
    this.reportService.getPlatformPerformanceReport().subscribe((data: any) => {
      this.platformPerformance = data;
    });
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
