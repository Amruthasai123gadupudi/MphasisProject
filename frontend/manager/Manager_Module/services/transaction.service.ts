// src/app/services/transaction.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseUrl = 'http://localhost:8082/api/transactions';

  constructor(private http: HttpClient) {}

  getBySellerId(sellerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/seller/${sellerId}`);
  }

  getByBuyerId(buyerId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/buyer/${buyerId}`);
  }

  getByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/status/${status}`);
  }

  searchByName(name: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/search?name=${name}`);
  }

  getAllTransactions(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`);
  }
  

}
