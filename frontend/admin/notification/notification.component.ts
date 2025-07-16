import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent 
{
  notification = {
    user: { id: null },
    message: '',
    targetRole: '',
    active: true
  };
  errorMsg = '';
  successMsg = '';
  roles = ['BUYER', 'SELLER', 'MANAGER', 'ADMIN']; // use your enum roles here
 
  constructor(private http: HttpClient) { }
 
  sendNotification() {
    this.errorMsg = '';
    this.successMsg = '';
 
    this.http.post('http://localhost:8082/api/admin/config/notifications', this.notification, { responseType: 'text' })
      .subscribe({
        next: (response: string) => {
          this.successMsg = response; // response is plain text
        },
        error: (error) => {
          console.error('Error response from backend:', error);
          if (error.error) {
            this.errorMsg = error.error;
          } else {
            this.errorMsg = 'Failed to send notification.';
          }
        }
      });
  }

}
