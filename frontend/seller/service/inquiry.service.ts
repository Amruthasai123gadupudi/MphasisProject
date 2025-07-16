import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Inquiry } from '../model/inquiry';

@Injectable({ providedIn: 'root' })
export class InquiryService {
  private base = 'http://localhost:8082/api/inquiries';

  constructor(private http: HttpClient) {}

  // Existing methods (getBySeller etc.) omitted for brevity

  respondAsSeller(
    inquiryId: number,
    sellerId: number,
    message: string,
    scheduleDate?: string
  ): Observable<Inquiry> {
    let params = new HttpParams()
      .set('inquiryId', inquiryId)
      .set('sellerId', sellerId)
      .set('message', message);

    if (scheduleDate) {
      params = params.set('scheduleDate', scheduleDate);
    }

    return this.http.put<Inquiry>(`${this.base}/respond/seller`, null, { params });
  }

  
  getInquiriesBySeller(sellerId: number): Observable<Inquiry[]> {
    return this.http.get<Inquiry[]>(`${this.base}/seller/${sellerId}`);
  }
}
