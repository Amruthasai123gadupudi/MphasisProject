import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InquiryService {
  private baseUrl = 'http://localhost:8082/api/inquiries';

  constructor(private http: HttpClient) {}

  // 📨 Manager responds to an inquiry
  respondAsManager(
    inquiryId: number,
    managerId: number,
    message: string,
    scheduleDate?: string
  ): Observable<any> {
    let params = new HttpParams()
      .set('inquiryId', inquiryId)
      .set('managerId', managerId)
      .set('message', message);

    if (scheduleDate) {
      params = params.set('scheduleDate', scheduleDate);
    }

    return this.http.put(`${this.baseUrl}/respond/manager`, null, { params });
  }

  // 📥 Fetch inquiries for a specific manager
  getInquiriesByManager(managerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/manager/${managerId}`);
  }

  // 💬 (Optional) Add back these methods if you uncomment the controller versions:
  // getInquiriesBySeller(sellerId: number): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.baseUrl}/seller/${sellerId}`);
  // }

  // getInquiriesByBuyer(buyerId: number): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.baseUrl}/buyer/${buyerId}`);
  // }
}
