import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private baseUrl = 'http://localhost:8082/api/admin/reports';

  constructor(private http: HttpClient) {}

  getUserRoleReport(): Observable<{ role: string, count: number }[]> {
    return this.http.get<{ role: string, count: number }[]>(`${this.baseUrl}/user-roles`);
  }

  getPropertyApprovalReport(): Observable<{ approved: boolean, count: number }[]> {
    return this.http.get<{ approved: boolean, count: number }[]>(`${this.baseUrl}/property-approval`);
  }

  getTransactionStatusReport(): Observable<{ status: string, count: number }[]> {
    return this.http.get<{ status: string, count: number }[]>(`${this.baseUrl}/transaction-status`);
  }

  getPlatformPerformanceReport(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/platform-performance`);
  }

  getSalesPerformanceReport(): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/sales-performance`);
  }
}
