import { Component, OnInit } from '@angular/core';
import { Inquiry } from '../model/inquiry';
import { InquiryService } from '../service/inquiry.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-inquiry',
  templateUrl: './inquiry.component.html',
  styleUrls: ['./inquiry.component.css']
})
export class InquiryComponent implements OnInit {
  inquiries: Inquiry[] = [];
  sellerResponses: Inquiry[] = [];
  selectedInquiry?: Inquiry;
  responseText: string = '';
  scheduleDateInput: string = '';

  sellerId!: number; // Will be set dynamically

  errorMessage: string = '';
  showSellerResponses: boolean = false;
  showInquiries: boolean = false;

  constructor(private inqService: InquiryService) {}

  ngOnInit() {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      const user = JSON.parse(userJson);
      this.sellerId = user.id;  // dynamically set sellerId from localStorage
    } else {
      this.errorMessage = 'User not logged in or user data missing!';
      // Optionally handle redirect or disable features here
    }
  }

  toggleSellerResponses() {
    this.showSellerResponses = !this.showSellerResponses;
    if (this.showSellerResponses) {
      this.showInquiries = false;

      this.inqService.getInquiriesBySeller(this.sellerId).subscribe({
        next: data => {
          this.sellerResponses = data.filter(inq => !!inq.responseMessage);
          this.errorMessage = '';
        },
        error: err => {
          this.errorMessage = `Failed to load seller responses: ${err.message}`;
          this.showSellerResponses = false;
        }
      });
    }
  }

  toggleLoadInquiries() {
    this.showInquiries = !this.showInquiries;
    if (this.showInquiries) {
      this.showSellerResponses = false;

      this.inqService.getInquiriesBySeller(this.sellerId).subscribe({
        next: data => {
          this.inquiries = data;
          this.selectedInquiry = undefined;
          this.errorMessage = '';
        },
        error: err => {
          this.errorMessage = `Load failed: ${err.message}`;
          this.showInquiries = false;
        }
      });
    }
  }

  select(inq: Inquiry) {
    this.selectedInquiry = inq;
    this.responseText = inq.responseMessage?.split('\n')[0] ?? '';
    this.scheduleDateInput = inq.scheduleDate || '';
  }

  submitResponse() {
    if (!this.selectedInquiry) {
      this.errorMessage = 'Please select an inquiry.';
      return;
    }

    const dtoSchedule = this.scheduleDateInput
      ? formatDate(this.scheduleDateInput, "yyyy-MM-dd'T'HH:mm:ss", 'en-US')
      : undefined;

    this.inqService.respondAsSeller(
      this.selectedInquiry.id,
      this.sellerId, // Now dynamic sellerId used here
      this.responseText,
      dtoSchedule
    ).subscribe({
      next: updated => {
        alert('Responded successfully!');
        this.selectedInquiry = updated;
        this.toggleLoadInquiries();  // reload to refresh view
        this.toggleLoadInquiries();
      },
      error: err => this.errorMessage = `Respond failed: ${err.message}`
    });
  }
}
