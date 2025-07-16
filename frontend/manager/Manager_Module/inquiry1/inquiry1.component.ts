import { Component, OnInit } from '@angular/core';
import { InquiryService } from '../services/inquiry.service';
 
@Component({
  selector: 'app-inquiry1',
  templateUrl: './inquiry1.component.html',
  styleUrls: ['./inquiry1.component.css']
})
export class Inquiry1Component implements OnInit {
  inquiries: any[] = [];
  managerResponses: any[] = [];
  selectedInquiry: any = null;
 
  responseText = '';
  scheduleDateInput = '';
  errorMessage = '';
  showManagerResponses: boolean = false;
 
  private readonly managerId = 3; // 🔒 Hardcoded manager ID
 
  constructor(private inquiryService: InquiryService) {}
 
  ngOnInit(): void {}
 
  loadInquiries(): void {
    this.inquiryService.getInquiriesByManager(this.managerId).subscribe({
      next: (data) => {
        this.inquiries = data;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch inquiries.';
        console.error(err);
      }
    });
  }
 
  toggleManagerResponses(): void {
    this.showManagerResponses = !this.showManagerResponses;
 
    if (this.showManagerResponses) {
      this.inquiryService.getInquiriesByManager(this.managerId).subscribe({
        next: (data) => {
          this.managerResponses = data.filter(inq => !!inq.responseMessage);
          this.errorMessage = '';
        },
        error: (err) => {
          this.errorMessage = `Failed to load manager responses: ${err.message}`;
          this.showManagerResponses = false;
        }
      });
    }
  }
 
  select(inquiry: any): void {
    this.selectedInquiry = inquiry;
    this.responseText = '';
    this.scheduleDateInput = '';
  }
 
  respondToInquiry(inquiryId: number, message: string, scheduleDate?: string): void {
    this.inquiryService.respondAsManager(inquiryId, this.managerId, message, scheduleDate).subscribe({
      next: () => {
        this.loadInquiries();
        this.selectedInquiry = null;
      },
      error: (err) => {
        console.error('Failed to send response', err);
      }
    });
  }
}
 