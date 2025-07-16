import { Component, OnInit } from '@angular/core';
import { ReportService } from '../services/report.service';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  userRoleReport: { role: string, count: number }[] = [];
  propertyApprovalReport: { approved: boolean, count: number }[] = [];
  transactionStatusReport: { status: string, count: number }[] = [];
  platformPerformanceReport: any = {};
  salesPerformanceReport: any = {};
  activeSection = 'userRole';  // default section

  errorMessage = '';

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {
    this.reportService.getUserRoleReport().subscribe({
      next: data => this.userRoleReport = data,
      error: () => this.errorMessage = 'Failed to load User Role report'
    });

    this.reportService.getPropertyApprovalReport().subscribe({
      next: data => this.propertyApprovalReport = data,
      error: () => this.errorMessage = 'Failed to load Property Approval report'
    });

    this.reportService.getTransactionStatusReport().subscribe({
      next: data => this.transactionStatusReport = data,
      error: () => this.errorMessage = 'Failed to load Transaction Status report'
    });

    this.reportService.getPlatformPerformanceReport().subscribe({
      next: data => this.platformPerformanceReport = data,
      error: () => this.errorMessage = 'Failed to load Platform Performance report'
    });

    this.reportService.getSalesPerformanceReport().subscribe({
      next: data => this.salesPerformanceReport = data,
      error: () => this.errorMessage = 'Failed to load Sales Performance report'
    });
  }

}
