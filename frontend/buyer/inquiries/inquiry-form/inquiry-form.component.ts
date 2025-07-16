import { Component, Input, OnInit } from '@angular/core';
import { InquiryService } from 'src/app/buyer/services/inquiry.service';
import { User } from 'src/app/buyer/models/user';

@Component({
  selector: 'app-inquiry-form',
  templateUrl: './inquiry-form.component.html',
  styleUrls: ['./inquiry-form.component.css']
})
export class InquiryFormComponent implements OnInit {
  @Input() propertyId!: number;

  message: string = '';
  targetRole: string = 'SELLER';
  buyerId!: number;

  constructor(private inquiryService: InquiryService) {}

  ngOnInit(): void {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      const user = JSON.parse(userJson) as User;
      this.buyerId = user.id;
    } else {
      console.warn('⚠️ No user found in localStorage.');
    }
  }

  submitInquiry(): void {
    if (!this.message || !this.targetRole) return;

    this.inquiryService.sendInquiry({
      propertyId: this.propertyId,
      buyerId: this.buyerId,
      message: this.message,
      targetRole: this.targetRole as 'SELLER' | 'MANAGER'
    }).subscribe({
      next: () => {
        alert('✅ Inquiry sent!');
        this.message = '';
        this.targetRole = 'SELLER';
      },
      error: (err) => console.error('❌ Error sending inquiry', err)
    });
  }
}
