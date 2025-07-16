import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inquiry } from '../models/inquiry.model';

@Injectable({
  providedIn: 'root'
})
export class InquiryService {
  private baseUrl = 'http://localhost:8082/api/inquiries';

  constructor(private http: HttpClient) {}

  getBuyerInquiries(buyerId: number): Observable<Inquiry[]> {
    return this.http.get<Inquiry[]>(`${this.baseUrl}/buyer/${buyerId}`);
  }

  sendInquiry(data: {
    propertyId: number;
    buyerId: number;
    message: string;
    targetRole: 'SELLER' | 'MANAGER';
  }): Observable<Inquiry> {
    const params = new HttpParams()
      .set('propertyId', data.propertyId)
      .set('buyerId', data.buyerId)
      .set('message', data.message)
      .set('targetRole', data.targetRole);
      
    return this.http.post<Inquiry>(`${this.baseUrl}/send`, null, { params });
  }
}
