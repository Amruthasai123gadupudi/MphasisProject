import { Component, OnInit } from '@angular/core';
import { Inquiry } from '../../models/inquiry.model';
import { InquiryService } from '../../services/inquiry.service';
import { AuthService } from 'src/app/services/auth.service';
import { User } from '../../models/user'; // adjust path if needed

@Component({
  selector: 'app-inquiry-list',
  templateUrl: './inquiry-list.component.html',
  styleUrls: ['./inquiry-list.component.css']
})
export class InquiryListComponent implements OnInit {
  inquiries: Inquiry[] = [];
  buyerId!: number;

  constructor(
    private inquiryService: InquiryService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      const user = JSON.parse(userJson) as User;
      this.buyerId = user.id;
      this.loadInquiries();
    } else {
      console.warn('⚠️ No logged-in user found. Cannot load inquiries.');
    }
  }

  loadInquiries(): void {
    this.inquiryService.getBuyerInquiries(this.buyerId).subscribe({
      next: (data) => this.inquiries = data,
      error: (err) => console.error('❌ Failed to fetch inquiries', err)
    });
  }

  reload(): void {
    this.loadInquiries();
  }
}
