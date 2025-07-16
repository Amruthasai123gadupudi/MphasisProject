import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserNotification } from '../model/user-notification';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private baseUrl = 'http://localhost:8082/api/admin/config/notifications';
 
  constructor(private http: HttpClient) {}
 
  getNotificationsByUser(userId: number): Observable<UserNotification[]> {
    return this.http.get<UserNotification[]>(`${this.baseUrl}/user/${userId}`);
  }
}
