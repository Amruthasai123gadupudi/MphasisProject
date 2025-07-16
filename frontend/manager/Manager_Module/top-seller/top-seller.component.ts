import { Component } from '@angular/core';
import { AnalyticsService } from '../services/analytics.service';
 
 
@Component({
  selector: 'app-top-seller',
  templateUrl: './top-seller.component.html',
  styleUrls: ['./top-seller.component.css']
})
export class TopSellerComponent {
  showPerformance = false;
  sellers: any[] = [];
  errorMessage: string = '';
 
  constructor(private analyticsService: AnalyticsService) {}
 
  togglePerformance(): void {
    this.showPerformance = !this.showPerformance;
 
    if (this.showPerformance && this.sellers.length === 0) {
      this.analyticsService.getTopSellers().subscribe({
        next: (data: any[]) => {
          this.sellers = data;
        },
        error: () => {
          this.errorMessage = 'Failed to load performance data.';
        }
      });
    }
  }
}
 